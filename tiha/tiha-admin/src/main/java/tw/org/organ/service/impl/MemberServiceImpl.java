package tw.org.organ.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;

import cn.dev33.satoken.stp.SaTokenInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.MemberConvert;
import tw.org.organ.enums.MemberReviewEnum;
import tw.org.organ.mapper.MemberMapper;
import tw.org.organ.pojo.DTO.InsertMemberDTO;
import tw.org.organ.pojo.DTO.MemberLoginInfo;
import tw.org.organ.pojo.DTO.ProviderRegisterDTO;
import tw.org.organ.pojo.DTO.UpdateMemberDTO;
import tw.org.organ.pojo.VO.MemberVO;
import tw.org.organ.pojo.entity.Member;
import tw.org.organ.saToken.StpKit;
import tw.org.organ.service.MemberService;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

	@Value("${spring.mail.username}")
	private String fromEmail;

	private final MemberConvert memberConvert;
	private final JavaMailSender mailSender;

	@Override
	public List<Member> getAllMember() {
		// TODO Auto-generated method stub
		List<Member> memberList = baseMapper.selectList(null);
		return memberList;
	}

	@Override
	public IPage<Member> getAllMember(Page<Member> page) {
		// TODO Auto-generated method stub
		Page<Member> memberList = baseMapper.selectPage(page, null);
		return memberList;
	}

	@Override
	public IPage<Member> getAllMemberByStatus(Page<Member> page, String status) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
		memberQueryWrapper.eq(Member::getStatus, status);

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
		// TODO Auto-generated method stub
		Member member = memberConvert.insertDTOToEntity(insertMemberDTO);
		member.setStatus(MemberReviewEnum.APPROVED.getCode());
		baseMapper.insert(member);
		return member.getMemberId();
	}

	@Override
	public void updateMember(UpdateMemberDTO updateMemberDTO) {
		// TODO Auto-generated method stub
		Member member = memberConvert.updateDTOToEntity(updateMemberDTO);
		baseMapper.updateById(member);

	}

	@Override
	public void updateMember(List<UpdateMemberDTO> updateMemberDTOList) {
		// TODO Auto-generated method stub
		for (UpdateMemberDTO updateMemberDTO : updateMemberDTOList) {
			Member member = memberConvert.updateDTOToEntity(updateMemberDTO);
			baseMapper.updateById(member);
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
			if (!Strings.isNullOrEmpty(memberLoginInfo.getEmail())
					&& !Strings.isNullOrEmpty(memberLoginInfo.getPassword())) {

				// 去資料庫找有沒有符合這個供應商id的資料
				LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
				memberQueryWrapper.eq(Member::getEmail, memberLoginInfo.getEmail()).eq(Member::getPassword,
						memberLoginInfo.getPassword());

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

		//透過Email查詢Member
		LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();
		memberQueryWrapper.eq(Member::getEmail, email);

		//獲得查詢結果
		Member member = baseMapper.selectOne(memberQueryWrapper);

		/**
		 * 
		 * MimeMessage 是 JavaMail API 中的一个类，用于代表一封 MIME 格式的电子邮件。 MIME（Multipurpose
		 * Internet Mail Extensions）是一个互联网标准，它允许电子邮件包含： 1.HTML 格式的内容（而不仅仅是纯文本）
		 * 2.不同的字符编码（如 UTF-8，支持各种语言） 3.附件 4.内嵌图片
		 * 
		 */

		if (member != null) {
			
			//查詢他有沒有第三方社群帳號,如果有也是直接返回
			if(member.getProviderUserId() != null) {
				return null;
			}

			//開始編寫信件,準備寄給一般註冊者找回密碼的信
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

}
