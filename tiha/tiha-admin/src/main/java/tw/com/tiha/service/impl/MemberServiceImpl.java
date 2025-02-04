package tw.com.tiha.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;

import cn.dev33.satoken.stp.SaTokenInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tw.com.tiha.convert.MemberConvert;
import tw.com.tiha.mapper.MemberMapper;
import tw.com.tiha.mapper.MemberTagMapper;
import tw.com.tiha.mapper.TagMapper;
import tw.com.tiha.pojo.DTO.InsertMemberDTO;
import tw.com.tiha.pojo.DTO.MemberLoginInfo;
import tw.com.tiha.pojo.DTO.ProviderRegisterDTO;
import tw.com.tiha.pojo.DTO.UpdateMemberDTO;
import tw.com.tiha.pojo.VO.MemberTagVO;
import tw.com.tiha.pojo.VO.MemberVO;
import tw.com.tiha.pojo.entity.Member;
import tw.com.tiha.pojo.entity.MemberTag;
import tw.com.tiha.pojo.entity.Tag;
import tw.com.tiha.pojo.excelPojo.MemberExcel;
import tw.com.tiha.saToken.StpKit;
import tw.com.tiha.service.MemberService;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

	@Value("${spring.mail.username}")
	private String fromEmail;

	private final MemberConvert memberConvert;
	private final JavaMailSender mailSender;

	private final MemberTagMapper memberTagMapper;
	private final TagMapper tagMapper;

	@Override
	public List<Member> getAllMember() {
		// TODO Auto-generated method stub
		List<Member> memberList = baseMapper.selectList(null);
		return memberList;
	}

	@Override
	public IPage<Member> getAllMember(Page<Member> page) {
		// 越新的擺越前面
		LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
		memberQueryWrapper.orderByDesc(Member::getMemberId);

		Page<Member> memberList = baseMapper.selectPage(page, memberQueryWrapper);
		return memberList;
	}

	@Override
	public IPage<Member> getAllMemberByStatus(Page<Member> page, String status, String queryText) {

		LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();

		// 如果 status 不為空字串、空格字串、Null 時才加入篩選條件
		memberQueryWrapper.eq(StringUtils.isNotBlank(status), Member::getStatus, status)
				// 當 queryText 不為空字串、空格字串、Null 時才加入篩選條件
				.and(StringUtils.isNotBlank(queryText), wrapper -> wrapper.like(Member::getName, queryText).or()
						.like(Member::getIdCard, queryText).or().like(Member::getPhone, queryText).or())
				.orderByDesc(Member::getMemberId);

		Page<Member> memberList = baseMapper.selectPage(page, memberQueryWrapper);
		return memberList;

	}

	@Override
	public Member getMember(Long memberId) {
		// TODO Auto-generated method stub
		Member member = baseMapper.selectById(memberId);
		return member;
	}

	@Override
	public Long getMemberCount() {
		// TODO Auto-generated method stub
		Long memberCount = baseMapper.selectCount(null);
		return memberCount;
	}

	@Override
	public Long getMemberCount(String status) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<Member> memberCountQueryWrapper = new LambdaQueryWrapper<>();
		memberCountQueryWrapper.eq(Member::getStatus, status);
		Long memberCount = baseMapper.selectCount(memberCountQueryWrapper);

		return memberCount;
	}

	@Override
	public Long insertMember(InsertMemberDTO insertMemberDTO) {
		Member member = memberConvert.insertDTOToEntity(insertMemberDTO);
		baseMapper.insert(member);
		return member.getMemberId();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Override
	public void updateMember(UpdateMemberDTO updateMemberDTO) {
		// 轉換資料
		Member member = memberConvert.updateDTOToEntity(updateMemberDTO);

		// 當送過來要更新的資料 審核狀態status 為1 , 且會員編號尚未有值的情況下
		if (member.getStatus().equals("1") && member.getCode() == null) {
			// 查詢當下最大的code編號
			Integer selectMaxMemberCode = baseMapper.selectMaxMemberCode();

			if (selectMaxMemberCode == null) {
				// 沒最大的編號 則賦值為1
				member.setCode(1);
			} else {
				// 有最大編號的情況,最大的編號 + 1 為新值
				member.setCode(selectMaxMemberCode + 1);
			}

		}
		// 最終更新對象
		baseMapper.updateById(member);

	}

	@Override
	public void updateMember(List<UpdateMemberDTO> updateMemberDTOList) {
		for (UpdateMemberDTO updateMemberDTO : updateMemberDTOList) {
			updateMember(updateMemberDTO);
		}

	}

	@Override
	public void updateProviderMember(ProviderRegisterDTO providerRegisterDTO) {
		Member member = memberConvert.providerRegisterDTO(providerRegisterDTO);
		baseMapper.updateById(member);
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMember(Long memberId) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(memberId);

	}

	@Override
	public void deleteMember(List<Long> memberIdList) {
		// TODO Auto-generated method stub
		baseMapper.deleteBatchIds(memberIdList);

	}

	@Override
	public MemberVO login(MemberLoginInfo memberLoginInfo) {
		// TODO Auto-generated method stub
		// 如果這個供應商id 不為null或者不為空字串,則往下進行
		if (!Strings.isNullOrEmpty(memberLoginInfo.getProviderUserId())) {

			// 去資料庫找有沒有符合這個供應商id的資料
			LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
			memberQueryWrapper.eq(Member::getProviderUserId, memberLoginInfo.getProviderUserId());

			Member member = baseMapper.selectOne(memberQueryWrapper);

			// 如果有則直接帶入資料並登入
			if (member != null) {
				// 先轉換member對象成為VO對象
				MemberVO memberVO = memberConvert.entityToVO(member);

				// 如果會員有資料，且是已經被審核通過的狀態，那麼直接回傳token前端
				if (memberVO.getStatus().equals("1")) {
					// saToken 的登入代碼
					StpKit.MEMBER.login(memberVO.getMemberId());
					SaTokenInfo tokenInfo = StpKit.MEMBER.getTokenInfo();
					memberVO.setToken(tokenInfo);
				}

				// 沒有就不回傳token這樣前端可以透過有沒有token來判斷是否先擋下
				return memberVO;

			} else {
				// 如果沒有，則接這個紀錄先新增,然後返回Member給前端,引導他去註冊填寫其他資料,再觸發update function
				Member insertMember = memberConvert.loginInfoToEntity(memberLoginInfo);
				baseMapper.insert(insertMember);
				MemberVO memberVO = memberConvert.entityToVO(insertMember);
				// 因為他還沒有成功註冊成功先不給他token

				return memberVO;
			}

		} else {
			// 當確定不是提供商ID登入的,那我們就要驗證Email和Password 是不是正確
			// 先校驗email 和 password是否不為null && 空字串
			if (!Strings.isNullOrEmpty(memberLoginInfo.getIdCard())
					&& !Strings.isNullOrEmpty(memberLoginInfo.getPhone())) {

				// 去資料庫找有沒有符合這個供應商id的資料
				LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
				memberQueryWrapper.eq(Member::getIdCard, memberLoginInfo.getIdCard()).eq(Member::getPhone,
						memberLoginInfo.getPhone());

				Member member = baseMapper.selectOne(memberQueryWrapper);

				// 如果有找到，設定token紀錄登入狀態，並返回token
				if (member != null) {
					// 先轉換成VO對象
					MemberVO memberVO = memberConvert.entityToVO(member);

					// 如果會員有資料，且是已經被審核通過的狀態，那麼直接回傳token前端
					if (memberVO.getStatus().equals("1")) {
						// 設定token , 獲取後組裝並返回
						StpKit.MEMBER.login(memberVO.getMemberId());
						SaTokenInfo tokenInfo = StpKit.MEMBER.getTokenInfo();
						memberVO.setToken(tokenInfo);
					}
					return memberVO;
				}
			}
		}
		// 直接返回null代表,帳號密碼錯誤
		return null;

	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		StpKit.MEMBER.logout();

	}

	@Override
	public Member forgetPassword(String email) throws MessagingException {
		// TODO Auto-generated method stub

		// 透過Email查詢Member
		LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
		memberQueryWrapper.eq(Member::getEmail, email);

		// 獲得查詢結果
		Member member = baseMapper.selectOne(memberQueryWrapper);

		/**
		 * 
		 * MimeMessage 是 JavaMail API 中的一个类，用于代表一封 MIME 格式的电子邮件。 MIME（Multipurpose
		 * Internet Mail Extensions）是一个互联网标准，它允许电子邮件包含： 1.HTML 格式的内容（而不仅仅是纯文本）
		 * 2.不同的字符编码（如 UTF-8，支持各种语言） 3.附件 4.内嵌图片
		 * 
		 */

		if (member != null) {

			// 查詢他有沒有第三方社群帳號,如果有也是直接返回
			if (member.getProviderUserId() != null) {
				return null;
			}

			// 開始編寫信件,準備寄給一般註冊者找回密碼的信
			try {
				MimeMessage message = mailSender.createMimeMessage();
				// message.setHeader("Content-Type", "text/html; charset=UTF-8");

				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

				helper.setTo(email);
				helper.setSubject("新川醫療 - 找回密碼");

				String password = member.getPassword();
				String htmlContent = """
						<!DOCTYPE html>
						<html >
						<head>
							<meta charset="UTF-8">
							<meta name="viewport" content="width=device-width, initial-scale=1.0">
							<title>找回密碼</title>
						</head>

						<body >
							<table>
						    	<tr>
						        	<td style="font-size:1.5rem;" >為您找回您的密碼</td>
						        </tr>
						        <tr>
						            <td>您的密碼是：<strong>%s</strong></td>
						        </tr>
						        <tr>
						            <td>請紀錄您的密碼，避免再次遺失。</td>
						        </tr>
						        <tr>
						            <td>如果您沒有申請找回密碼，請忽略此郵件。</td>
						        </tr>
						    </table>
						</body>
						</html>
						""".formatted(password);

				String plainTextContent = "您的密碼是：" + password + "\n請紀錄您的密碼，避免再次遺失。\n如果您沒有申請找回密碼，請忽略此郵件。";

				helper.setText(plainTextContent, false); // 纯文本版本
				helper.setText(htmlContent, true); // HTML 版本

				mailSender.send(message);

			} catch (MessagingException e) {
				System.err.println("發送郵件失敗: " + e.getMessage());
			}
			return member;
		} else {
			return null;
		}

	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 ， 和easyexcel没有关系
		String fileName = URLEncoder.encode("測試", "UTF-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");

		// 關鍵設置：啟用分塊傳輸編碼，移除Content-Length
//		response.setHeader("Transfer-Encoding", "chunked");
//		response.setHeader("Content-Length", null);

		// 测量第一部分执行时间
//        long startTime1 = System.nanoTime();
		// 第一部分代码

		List<Member> memberList = baseMapper.selectAllMembersMySelf();

//		long endTime1 = System.nanoTime();

//		System.out.println("第一部分执行时间: " + (endTime1 - startTime1) / 1_000_000_000.0 + " 秒");

		System.out.println("--------接下來轉換數據------------");

		// 测量第二部分执行时间
//        long startTime2 = System.nanoTime();

		List<MemberExcel> excelData = memberList.stream().map(member -> {
			return memberConvert.entityToExcel(member);
		}).collect(Collectors.toList());

//		long endTime2 = System.nanoTime();

//        System.out.println("第二部分执行时间: " + (endTime2 - startTime2) / 1_000_000_000.0 + " 秒");

		System.out.println("接下來寫入數據");

		// 测量第三部分执行时间
//        long startTime3 = System.nanoTime();

		EasyExcel.write(response.getOutputStream(), MemberExcel.class).sheet("會員列表").doWrite(excelData);

//		long endTime3 = System.nanoTime();
//        System.out.println("第三部分执行时间: " + (endTime3 - startTime3) / 1_000_000_000.0 + " 秒");

	}

	@Override
	public MemberTagVO getMemberTagVOByMember(Long memberId) {

		// 1.獲取member 資料並轉換成 memberTagVO
		Member member = baseMapper.selectById(memberId);
		MemberTagVO memberTagVO = memberConvert.entityToMemberTagVO(member);

		// 2.查詢該member所有關聯的tag
		LambdaQueryWrapper<MemberTag> memberTagWrapper = new LambdaQueryWrapper<>();
		memberTagWrapper.eq(MemberTag::getMemberId, memberId);
		List<MemberTag> memberTagList = memberTagMapper.selectList(memberTagWrapper);

		// 如果沒有任何關聯,就可以直接返回了
		if (memberTagList.isEmpty()) {
			return memberTagVO;
		}

		// 3.獲取到所有memberTag的關聯關係後，提取出tagIdList
		List<Long> tagIdList = memberTagList.stream().map(memberTag -> memberTag.getTagId())
				.collect(Collectors.toList());

		// 4.去Tag表中查詢實際的Tag資料，並轉換成Set集合
		LambdaQueryWrapper<Tag> tagWrapper = new LambdaQueryWrapper<>();
		tagWrapper.in(Tag::getTagId, tagIdList);
		List<Tag> tagList = tagMapper.selectList(tagWrapper);
		Set<Tag> tagSet = new HashSet<>(tagList);

		// 5.最後填入memberTagVO對象並返回
		memberTagVO.setTagSet(tagSet);
		return memberTagVO;
	}

	@Override
	public IPage<MemberTagVO> getAllMemberTagVO(Page<Member> page) {

		IPage<MemberTagVO> voPage;

		// 1.以member當作基底查詢,越新的擺越前面
		LambdaQueryWrapper<Member> memberWrapper = new LambdaQueryWrapper<>();
		memberWrapper.orderByDesc(Member::getMemberId);

		// 2.查詢 MemberPage (分頁)
		IPage<Member> memberPage = baseMapper.selectPage(page, memberWrapper);

		// 3. 獲取所有 memberId 列表，
		List<Long> memberIds = memberPage.getRecords().stream().map(Member::getMemberId).collect(Collectors.toList());

		if (memberIds.isEmpty()) {
			System.out.println("沒有會員,所以直接返回");

			voPage = new Page<>(page.getCurrent(), page.getSize(), memberPage.getTotal());
			voPage.setRecords(null);
		}

		// 4. 批量查詢 MemberTag 關係表，獲取 memberId 对应的 tagId
		List<MemberTag> memberTagList = memberTagMapper
				.selectList(new LambdaQueryWrapper<MemberTag>().in(MemberTag::getMemberId, memberIds));

		// 5. 將 memberId 對應的 tagId 歸類，key 為memberId , value 為 tagIdList
		Map<Long, List<Long>> memberTagMap = memberTagList.stream().collect(Collectors
				.groupingBy(MemberTag::getMemberId, Collectors.mapping(MemberTag::getTagId, Collectors.toList())));

		// 6. 获取所有 tagId 列表
		List<Long> tagIds = memberTagList.stream().map(MemberTag::getTagId).distinct().collect(Collectors.toList());

		// 7. 批量查询所有的 Tag，如果關聯的tagIds為空, 那就不用查了，直接返回
		if (tagIds.isEmpty()) {
			System.out.println("沒有任何tag關聯,所以直接返回");
			List<MemberTagVO> memberTagVOList = memberPage.getRecords().stream().map(member -> {
				MemberTagVO vo = memberConvert.entityToMemberTagVO(member);
				vo.setTagSet(new HashSet<>());
				return vo;
			}).collect(Collectors.toList());
			voPage = new Page<>(page.getCurrent(), page.getSize(), memberPage.getTotal());
			voPage.setRecords(memberTagVOList);

			return voPage;

		}
		List<Tag> tagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>().in(Tag::getTagId, tagIds));

		// 8. 将 Tag 按 tagId 归类
		Map<Long, Tag> tagMap = tagList.stream().collect(Collectors.toMap(Tag::getTagId, tag -> tag));

		// 9. 组装 VO 数据
		List<MemberTagVO> voList = memberPage.getRecords().stream().map(member -> {
			MemberTagVO vo = memberConvert.entityToMemberTagVO(member);
			// 获取该 memberId 关联的 tagId 列表
			List<Long> relatedTagIds = memberTagMap.getOrDefault(member.getMemberId(), Collections.emptyList());
			// 获取所有对应的 Tag
			List<Tag> tags = relatedTagIds.stream().map(tagMap::get).filter(Objects::nonNull) // 避免空值
					.collect(Collectors.toList());
			Set<Tag> tagSet = new HashSet<>(tags);
			vo.setTagSet(tagSet);
			return vo;
		}).collect(Collectors.toList());

		// 10. 重新封装 VO 的分页对象
		voPage = new Page<>(page.getCurrent(), page.getSize(), memberPage.getTotal());
		voPage.setRecords(voList);

		return voPage;
	}

	@Transactional
	@Override
	public void assignTagToMember(List<Long> targetTagIdList, Long memberId) {
		// 1. 查詢當前 member 的所有關聯 tag
		LambdaQueryWrapper<MemberTag> currentQueryWrapper = new LambdaQueryWrapper<>();
		currentQueryWrapper.eq(MemberTag::getMemberId, memberId);
		List<MemberTag> currentMemberTags = memberTagMapper.selectList(currentQueryWrapper);

		// 2. 提取當前關聯的 tagId Set
		Set<Long> currentTagIdSet = currentMemberTags.stream().map(MemberTag::getTagId).collect(Collectors.toSet());

		// 3. 對比目標 memberIdList 和當前 memberIdList
		Set<Long> targetTagIdSet = new HashSet<>(targetTagIdList);

		// 4. 找出需要 刪除 的關聯關係
		Set<Long> tagsToRemove = new HashSet<>(currentTagIdSet);
		// 差集：當前有但目標沒有
		tagsToRemove.removeAll(targetTagIdSet);

		// 5. 找出需要 新增 的關聯關係
		Set<Long> tagsToAdd = new HashSet<>(targetTagIdSet);
		// 差集：目標有但當前沒有
		tagsToAdd.removeAll(currentTagIdSet);

		// 6. 執行刪除操作，如果 需刪除集合 中不為空，則開始刪除
		if (!tagsToRemove.isEmpty()) {
			LambdaQueryWrapper<MemberTag> deleteMemberTagWrapper = new LambdaQueryWrapper<>();
			deleteMemberTagWrapper.eq(MemberTag::getMemberId, memberId).in(MemberTag::getTagId, tagsToRemove);
			memberTagMapper.delete(deleteMemberTagWrapper);
		}

		// 7. 執行新增操作，如果 需新增集合 中不為空，則開始新增
		if (!tagsToAdd.isEmpty()) {
			List<MemberTag> newMemberTags = tagsToAdd.stream().map(tagId -> {
				MemberTag memberTag = new MemberTag();
				memberTag.setTagId(tagId);
				memberTag.setMemberId(memberId);
				return memberTag;
			}).collect(Collectors.toList());

			// 批量插入
			for (MemberTag memberTag : newMemberTags) {
				memberTagMapper.insert(memberTag);
			}
		}

	}

}
