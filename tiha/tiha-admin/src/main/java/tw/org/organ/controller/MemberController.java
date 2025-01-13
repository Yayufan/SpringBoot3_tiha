package tw.org.organ.controller;

import java.util.List;

import org.simpleframework.xml.core.Validate;
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

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.ForgetPwdDTO;
import tw.org.organ.pojo.DTO.InsertMemberDTO;
import tw.org.organ.pojo.DTO.MemberLoginInfo;
import tw.org.organ.pojo.DTO.ProviderRegisterDTO;
import tw.org.organ.pojo.DTO.UpdateMemberDTO;
import tw.org.organ.pojo.VO.MemberVO;
import tw.org.organ.pojo.entity.Member;
import tw.org.organ.saToken.StpKit;
import tw.org.organ.service.MemberService;
import tw.org.organ.utils.R;

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

	private final MemberService memberService;

	@GetMapping("{id}")
	@Operation(summary = "查詢單一會員")
	public R<Member> getMember(@PathVariable("id") Long memberId) {
		Member member = memberService.getMember(memberId);
		return R.ok(member);
	}

	@GetMapping
	@Operation(summary = "查詢所有會員")
	public R<List<Member>> getAllMember() {

		List<Member> memberList = memberService.getAllMember();
		return R.ok(memberList);
	}

	@GetMapping("pagination")
	@Operation(summary = "查詢所有會員(分頁)")
	public R<IPage<Member>> getAllMember(@RequestParam Integer page, @RequestParam Integer size) {
		Page<Member> pageInfo = new Page<>(page, size);
		IPage<Member> memberList = memberService.getAllMember(pageInfo);

		return R.ok(memberList);
	}

	@GetMapping("pagination-by-status")
	@Operation(summary = "根據會員狀態,查詢符合的所有會員(分頁)")
	public R<IPage<Member>> getAllMemberByStatus(@RequestParam Integer page, @RequestParam Integer size,
			@RequestParam String status) {
		Page<Member> pageInfo = new Page<>(page, size);
		IPage<Member> memberList = memberService.getAllMemberByStatus(pageInfo, status);

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

	@Operation(summary = "新增會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PostMapping
	public R<Long> saveMember(@Validated @RequestBody InsertMemberDTO insertMemberDTO) {
		Long memberId = memberService.insertMember(insertMemberDTO);
		return R.ok(memberId);

	}

	@Operation(summary = "更新會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateMember(@Validated @RequestBody UpdateMemberDTO updateMemberDTO) {
		memberService.updateMember(updateMemberDTO);
		return R.ok();

	}

	@Operation(summary = "批量更新會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping("batch")
	public R<Void> batchUpdateMember(@Validated @RequestBody @NotEmpty List<UpdateMemberDTO> updateMemberDTOList) {
		System.out.println("觸發批量更新");
		memberService.updateMember(updateMemberDTOList);
		return R.ok();

	}

	@Operation(summary = "刪除會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteMember(@PathVariable("id") Long memberId) {
		memberService.deleteMember(memberId);
		return R.ok();
	}

	@Operation(summary = "批量刪除會員")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
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
		Long insertMember = memberService.insertMember(insertMemberDTO);
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
	public R<Boolean> checkLogin(){
		boolean loginStatus = StpKit.MEMBER.isLogin();
		return R.ok(loginStatus);
	}

}
