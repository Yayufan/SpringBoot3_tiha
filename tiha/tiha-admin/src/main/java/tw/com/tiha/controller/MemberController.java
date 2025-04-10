package tw.com.tiha.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RedissonClient;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wf.captcha.SpecCaptcha;

import cn.dev33.satoken.annotation.SaCheckRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import tw.com.tiha.mapper.MemberMapper;
import tw.com.tiha.pojo.DTO.AddTagToMemberDTO;
import tw.com.tiha.pojo.DTO.ForgetPwdDTO;
import tw.com.tiha.pojo.DTO.InsertMemberDTO;
import tw.com.tiha.pojo.DTO.MemberLoginInfo;
import tw.com.tiha.pojo.DTO.ProviderRegisterDTO;
import tw.com.tiha.pojo.DTO.UpdateMemberDTO;
import tw.com.tiha.pojo.VO.MemberTagVO;
import tw.com.tiha.pojo.VO.MemberVO;
import tw.com.tiha.pojo.entity.Member;
import tw.com.tiha.saToken.StpKit;
import tw.com.tiha.service.MemberService;
import tw.com.tiha.utils.R;

/**
 * <p>
 * 會員表 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-09-12
 */

@Tag(name = "會員表API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

	@Qualifier("businessRedissonClient")
	private final RedissonClient redissonClient;
	private final MemberService memberService;

	private final MemberMapper memberMapper;

	@GetMapping("/addTestMember")
	@Operation(summary = "批量生成測試Member")
	public R<Void> addTestMember() {
		for (int i = 1; i <= 200000; i++) {
			Member member = new Member();
			member.setName("測試" + i);
			memberMapper.insert(member);
		}

		return R.ok();
	}

	@GetMapping("/captcha")
	@Operation(summary = "獲取驗證碼")
	public R<HashMap<Object, Object>> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 50, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = "Captcha:" + UUID.randomUUID().toString();
		// 明確調用String類型的Bucket,存入String類型的Value 進redis並設置過期時間為10分鐘
		redissonClient.<String>getBucket(key).set(verCode, 10, TimeUnit.MINUTES);

		// 将key和base64返回给前端
		HashMap<Object, Object> hashMap = new HashMap<>();
		hashMap.put("key", key);
		hashMap.put("image", specCaptcha.toBase64());

		return R.ok(hashMap);
	}

	@GetMapping("{id}")
	@Operation(summary = "查詢單一會員")
	public R<Member> getMember(@PathVariable("id") Long memberId) {
		Member member = memberService.getMember(memberId);
		return R.ok(member);
	}

	@GetMapping
	@Operation(summary = "查詢所有會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER), })
	@SaCheckRole("super-admin")
	public R<List<Member>> getAllMember() {

		List<Member> memberList = memberService.getAllMember();
		return R.ok(memberList);
	}

	@GetMapping("pagination")
	@Operation(summary = "查詢所有會員(分頁)")
	@SaCheckRole("super-admin")
	public R<IPage<Member>> getAllMember(@RequestParam Integer page, @RequestParam Integer size) {
		Page<Member> pageInfo = new Page<>(page, size);
		IPage<Member> memberList = memberService.getAllMember(pageInfo);
		return R.ok(memberList);
	}

	@GetMapping("pagination-by-status")
	@SaCheckRole("super-admin")
	@Operation(summary = "根據會員狀態/查詢文字,查詢符合的資料(分頁)")
	public R<IPage<Member>> getAllMemberByQuery(@RequestParam Integer page, @RequestParam Integer size,
			@RequestParam(required = false) String status, @RequestParam(required = false) String queryText) {
		Page<Member> pageInfo = new Page<>(page, size);

		IPage<Member> memberList;

		memberList = memberService.getAllMemberByStatus(pageInfo, status, queryText);

		return R.ok(memberList);
	}

	@GetMapping("count")
	@Operation(summary = "查詢會員總數")
	public R<Long> getMemberCount() {
		Long memberCount = memberService.getMemberCount();
		return R.ok(memberCount);
	}

	@GetMapping("count-by-status")
	@Operation(summary = "根據審核狀態,查詢相符的會員總數")
	public R<Long> getMemberCountByStatus(String status) {
		Long memberCount = memberService.getMemberCount(status);
		return R.ok(memberCount);
	}

	@Operation(summary = "新增會員 - 一般註冊")
	@PostMapping
	public R<Long> saveMember(@Validated @RequestBody InsertMemberDTO insertMemberDTO) {
		// 透過key 獲取redis中的驗證碼
		String redisCode = redissonClient.<String>getBucket(insertMemberDTO.getVerificationKey()).get();
		String userVerificationCode = insertMemberDTO.getVerificationCode();

		// 判斷驗證碼是否正確,如果不正確就直接返回前端,不做後續的業務處理
		if (userVerificationCode == null || !redisCode.equals(userVerificationCode.trim().toLowerCase())) {
			return R.fail("驗證碼不正確");
		}
		Long memberId = memberService.insertMember(insertMemberDTO);

		return R.ok(memberId);

	}

	@Operation(summary = "更新會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckRole("super-admin")
	@PutMapping
	public R<Void> updateMember(@Validated @RequestBody UpdateMemberDTO updateMemberDTO) {
		memberService.updateMember(updateMemberDTO);
		return R.ok();

	}

	@Operation(summary = "批量更新會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckRole("super-admin")
	@PutMapping("batch")
	public R<Void> batchUpdateMember(@Validated @RequestBody @NotEmpty List<UpdateMemberDTO> updateMemberDTOList) {
		System.out.println("觸發批量更新");
		memberService.updateMember(updateMemberDTOList);
		return R.ok();

	}

	@Operation(summary = "刪除會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckRole("super-admin")
	@DeleteMapping("{id}")
	public R<Void> deleteMember(@PathVariable("id") Long memberId) {
		memberService.deleteMember(memberId);
		return R.ok();
	}

	@Operation(summary = "批量刪除會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckRole("super-admin")
	@DeleteMapping()
	public R<Void> batchDeleteMember(@Valid @NotNull @RequestBody List<Long> memberIdList) {
		memberService.deleteMember(memberIdList);

		return R.ok();
	}

	@Operation(summary = "會員登入")
	@PostMapping("login")
	public R<MemberVO> login(@Validate @RequestBody MemberLoginInfo memberLoginInfo) {
		MemberVO memberVO = memberService.login(memberLoginInfo);
		return R.ok(memberVO);
	}

	@Operation(summary = "會員註冊-信箱密碼")
	@PostMapping("register")
	public R<Void> register(@Validate @RequestBody InsertMemberDTO insertMemberDTO) {
		memberService.insertMember(insertMemberDTO);
		return R.ok();
	}

	@Operation(summary = "會員註冊更新-第三方社群")
	@PostMapping("provider-register")
	public R<Void> providerRegister(@Validate @RequestBody ProviderRegisterDTO providerRegisterDTO) {
		memberService.updateProviderMember(providerRegisterDTO);
		return R.ok();
	}

	@Operation(summary = "會員登出")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@PostMapping("logout")
	public R<Void> logout() {
		memberService.logout();
		return R.ok();
	}

	@Operation(summary = "找回密碼")
	@PostMapping("forget-password")
	public R<Void> forgetPassword(@Validated @RequestBody ForgetPwdDTO forgetPwdDTO) throws MessagingException {
		Member member = memberService.forgetPassword(forgetPwdDTO.getEmail());
		if (member != null) {
			return R.ok("請前往信箱查看");
		}
		return R.fail(401, "沒有此信箱");
	}

	@Operation(summary = "確認登入狀態")
	@Parameters({
			@Parameter(name = "Authorization-member", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER), })
	@GetMapping("check-login")
	public R<Boolean> checkLogin() {
		boolean loginStatus = StpKit.MEMBER.isLogin();
		return R.ok(loginStatus);
	}

	@Operation(summary = "下載同意書excel列表")
	@SaCheckRole("super-admin")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER), })
	@GetMapping("/download-excel")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		memberService.downloadExcel(response);
	}

	@Operation(summary = "根據會員ID 查詢會員資料及他持有的標籤")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckRole("super-admin")
	@GetMapping("tag/{id}")
	public R<MemberTagVO> getMemberTagVOByMember(@PathVariable("id") Long memberId) {
		MemberTagVO memberTagVOByMember = memberService.getMemberTagVOByMember(memberId);
		return R.ok(memberTagVOByMember);

	}

	@Operation(summary = "查詢所有會員資料及他持有的標籤(分頁)")
	@SaCheckRole("super-admin")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@GetMapping("tag/pagination")
	public R<IPage<MemberTagVO>> getAllMemberTagVO(@RequestParam Integer page, @RequestParam Integer size) {
		Page<Member> pageInfo = new Page<>(page, size);
		
		IPage<MemberTagVO> memberTagVOPage = memberService.getAllMemberTagVO(pageInfo);
		return R.ok(memberTagVOPage);
	}

	@Operation(summary = "根據條件 查詢會員資料及他持有的標籤(分頁)")
	@SaCheckRole("super-admin")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@GetMapping("tag/pagination-by-query")
	public R<IPage<MemberTagVO>> getAllMemberTagVOByQuery(@RequestParam Integer page, @RequestParam Integer size,
			@RequestParam(required = false) String queryText, @RequestParam(required = false) String status,
			@RequestParam(required = false) List<Long> tags) {
		
		Page<Member> pageInfo = new Page<>(page, size);

		IPage<MemberTagVO> memberList;

		memberList = memberService.getAllMemberTagVOByQuery(pageInfo,queryText, status );

		return R.ok(memberList);
	}

	@Operation(summary = "為會員新增/更新/刪除 複數標籤")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckRole("super-admin")
	@PutMapping("tag")
	public R<Void> assignTagToMember(@Validated @RequestBody AddTagToMemberDTO addTagToMemberDTO) {
		memberService.assignTagToMember(addTagToMemberDTO.getTargetTagIdList(), addTagToMemberDTO.getMemberId());
		return R.ok();

	}

}
