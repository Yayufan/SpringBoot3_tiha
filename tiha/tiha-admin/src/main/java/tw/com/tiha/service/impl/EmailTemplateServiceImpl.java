package tw.com.tiha.service.impl;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import tw.com.tiha.convert.EmailTemplateConvert;
import tw.com.tiha.mapper.EmailTemplateMapper;
import tw.com.tiha.mapper.MemberMapper;
import tw.com.tiha.pojo.DTO.InsertEmailTemplateDTO;
import tw.com.tiha.pojo.DTO.SendEmailDTO;
import tw.com.tiha.pojo.DTO.UpdateEmailTemplateDTO;
import tw.com.tiha.pojo.entity.EmailTemplate;
import tw.com.tiha.pojo.entity.Member;
import tw.com.tiha.service.EmailTemplateService;

/**
 * <p>
 * 信件模板表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-01-16
 */
@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl extends ServiceImpl<EmailTemplateMapper, EmailTemplate>
		implements EmailTemplateService {

	private final EmailTemplateConvert emailTemplateConvert;
	private final JavaMailSender mailSender;
	private final MemberMapper memberMapper;

	@Override
	public List<EmailTemplate> getAllEmailTemplate() {
		List<EmailTemplate> emailTemplateList = baseMapper.selectList(null);
		return emailTemplateList;
	}

	@Override
	public IPage<EmailTemplate> getAllEmailTemplate(Page<EmailTemplate> page) {
		Page<EmailTemplate> emailTemplatePage = baseMapper.selectPage(page, null);
		return emailTemplatePage;
	}

	@Override
	public EmailTemplate getEmailTemplate(Long emailTemplateId) {
		EmailTemplate emailTemplate = baseMapper.selectById(emailTemplateId);
		return emailTemplate;
	}

	@Override
	public Long insertEmailTemplate(InsertEmailTemplateDTO insertEmailTemplateDTO) {
		EmailTemplate emailTemplate = emailTemplateConvert.insertDTOToEntity(insertEmailTemplateDTO);
		baseMapper.insert(emailTemplate);
		return emailTemplate.getEmailTemplateId();
	}

	@Override
	public void updateEmailTemplate(UpdateEmailTemplateDTO updateEmailTemplateDTO) {
		EmailTemplate emailTemplate = emailTemplateConvert.updateDTOToEntity(updateEmailTemplateDTO);
		baseMapper.updateById(emailTemplate);
	}

	@Override
	public void deleteEmailTemplate(Long emailTemplateId) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(emailTemplateId);

	}

	@Override
	public void deleteEmailTemplate(List<Long> emailTemplateIdList) {
		// TODO Auto-generated method stub
		for (Long emailTemplateId : emailTemplateIdList) {
			deleteEmailTemplate(emailTemplateId);
		}

	}

	@Async("taskExecutor") // 指定使用的線程池
	@Override
	public void sendEmail(SendEmailDTO sendEmailDTO) {
		// 開始編寫信件給通過的會員
		LambdaQueryWrapper<Member> memberQueryWrapper = new LambdaQueryWrapper<>();

		memberQueryWrapper.eq(Member::getStatus, "1");

		List<Member> memberList = memberMapper.selectList(memberQueryWrapper);

		for (Member member : memberList) {
			try {
				MimeMessage message = mailSender.createMimeMessage();
				// message.setHeader("Content-Type", "text/html; charset=UTF-8");

				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

				helper.setTo(member.getEmail());
				helper.setSubject(sendEmailDTO.getSubject());

				String htmlContent = sendEmailDTO.getHtmlContent();
				String plainTextContent = sendEmailDTO.getPlainText();

				// 替換 {{memberName}} 和 {{memberCode}} 為真正的會員數據

				// 先做null值判斷,避免寄送缺失資訊
				String memberName = member.getName() != null ? member.getName() : "";
				String memberCode = member.getCode() != null ? String.valueOf(member.getCode()) : "";
				// 將 memberCode 格式化為 HA0001, HA0002, ..., HA9999
				String formattedMemberCode = String.format("HA%04d", memberCode);

				htmlContent = htmlContent.replace("{{memberName}}", memberName).replace("{{memberCode}}",
						formattedMemberCode);

				plainTextContent = plainTextContent.replace("{{memberName}}", memberName).replace("{{memberCode}}",
						formattedMemberCode);

				helper.setText(plainTextContent, false); // 纯文本版本
				helper.setText(htmlContent, true); // HTML 版本

				mailSender.send(message);

			} catch (MessagingException e) {

				System.err.println("發送郵件失敗: " + e.getMessage());
			}
		}

	}

}
