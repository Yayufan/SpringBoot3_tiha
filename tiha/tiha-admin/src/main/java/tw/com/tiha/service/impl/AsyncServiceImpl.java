package tw.com.tiha.service.impl;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tw.com.tiha.service.AsyncService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncServiceImpl implements AsyncService {

	private final JavaMailSender mailSender;

	// Semaphore 用來控制每次發送郵件之間的間隔
	private final Semaphore semaphore = new Semaphore(1);

	@Override
	@Async("taskExecutor")
	public void sendCommonEmail(String to, String subject, String htmlContent, String plainTextContent) {
		// 開始編寫信件,準備寄送單封郵件給會員
		try {
			MimeMessage message = mailSender.createMimeMessage();
			// message.setHeader("Content-Type", "text/html; charset=UTF-8");

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(plainTextContent, false); // 纯文本版本
			helper.setText(htmlContent, true); // HTML 版本

			mailSender.send(message);

		} catch (MessagingException e) {
			System.err.println("發送郵件失敗: " + e.getMessage());
			log.error("發送郵件失敗: " + e.getMessage());
		}
	}

	@Override
	@Async("taskExecutor")
	public void sendCommonEmail(String to, String subject, String htmlContent, String plainTextContent,
			List<ByteArrayResource> attachments) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(plainTextContent, false); // 純文本版本
			helper.setText(htmlContent, true); // HTML 版本

			// 添加附件
			if (attachments != null && !(attachments.isEmpty())) {
				for (ByteArrayResource attachment : attachments) {
					helper.addAttachment(attachment.getFilename(), attachment);

				}
			}

			mailSender.send(message);

		} catch (MessagingException e) {
			System.err.println("發送郵件失敗: " + e.getMessage());
			log.error("發送郵件失敗: " + e.getMessage());
		}
	}

	

}
