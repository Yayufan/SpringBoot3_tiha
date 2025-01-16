package tw.com.tiha.controller;

import java.util.List;

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
import lombok.RequiredArgsConstructor;
import tw.com.tiha.pojo.DTO.InsertEmailTemplateDTO;
import tw.com.tiha.pojo.DTO.UpdateEmailTemplateDTO;
import tw.com.tiha.pojo.entity.EmailTemplate;
import tw.com.tiha.service.EmailTemplateService;
import tw.com.tiha.utils.R;

/**
 * <p>
 * 信件模板表 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-01-16
 */

@Tag(name = "信件模板API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/email-template")
public class EmailTemplateController {

	private final EmailTemplateService emailTemplateService;

	@GetMapping("{id}")
	@Operation(summary = "查詢單一信件模板圖片")
	public R<EmailTemplate> getEmailTemplate(@PathVariable Long emailTemplateId) {
		EmailTemplate emailTemplate = emailTemplateService.getEmailTemplate(emailTemplateId);
		return R.ok(emailTemplate);
	}

	@GetMapping
	@Operation(summary = "查詢所有信件模板圖片")
	public R<List<EmailTemplate>> getAllEmailTemplate() {

		List<EmailTemplate> emailTemplateList = emailTemplateService.getAllEmailTemplate();
		return R.ok(emailTemplateList);
	}

	@GetMapping("pagination")
	@Operation(summary = "查詢所有信件模板圖片(分頁)")
	public R<IPage<EmailTemplate>> getAllEmailTemplate(@RequestParam Integer page, @RequestParam Integer size) {
		Page<EmailTemplate> pageInfo = new Page<>(page, size);
		IPage<EmailTemplate> emailTemplateList = emailTemplateService.getAllEmailTemplate(pageInfo);
		return R.ok(emailTemplateList);
	}

	@Operation(summary = "新增信件模板圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PostMapping
	public R<Long> saveEmailTemplate(@RequestBody InsertEmailTemplateDTO insertEmailTemplateDTO) {
		Long emailTemplateId = emailTemplateService.insertEmailTemplate(insertEmailTemplateDTO);
		return R.ok(emailTemplateId);

	}

	@Operation(summary = "更新信件模板圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateEmailTemplate(@RequestBody UpdateEmailTemplateDTO emailTemplateDTO) {
		emailTemplateService.updateEmailTemplate(emailTemplateDTO);
		return R.ok();

	}

	@Operation(summary = "刪除信件模板圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteEmailTemplate(@PathVariable("id") Long emailTemplateId) {
		emailTemplateService.deleteEmailTemplate(emailTemplateId);
		return R.ok();

	}

	@Operation(summary = "批量刪除信件模板圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping()
	public R<Void> deleteEmailTemplate(List<Long> emailTemplateIdList) {
		emailTemplateService.deleteEmailTemplate(emailTemplateIdList);
		return R.ok();

	}

}