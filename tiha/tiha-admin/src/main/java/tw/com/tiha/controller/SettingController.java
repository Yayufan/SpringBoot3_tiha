package tw.com.tiha.controller;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import tw.com.tiha.mapper.SettingMapper;
import tw.com.tiha.pojo.DTO.UpdateSettingDTO;
import tw.com.tiha.pojo.entity.Setting;
import tw.com.tiha.service.SettingService;
import tw.com.tiha.utils.R;

/**
 * <p>
 * 系統設定表 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Tag(name = "系統設定API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/setting")
public class SettingController {

	private final SettingService settingService;
	private final SettingMapper settingMapper;

	private final JavaMailSender mailSender;

	@GetMapping
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@Operation(summary = "查詢所有系統設置")
	public R<List<Setting>> getAllSetting() {
		List<Setting> settingList = settingService.getAllSetting();
		return R.ok(settingList);
	}

	@Operation(summary = "更新最新消息")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateSetting(@Validated @RequestBody List<UpdateSettingDTO> updateSettingDTOList) {
		settingService.updateSetting(updateSettingDTOList);
		return R.ok();

	}

	@GetMapping("get-view-count")
	@Operation(summary = "獲取網站瀏覽人次")
	public R<Setting> getViewCount() {
		Setting viewCountSetting = settingMapper.selectById(5L);
		return R.ok(viewCountSetting);
	}

	@GetMapping("add-view-count")
	@Operation(summary = "訪問時，瀏覽數+1")
	public R<Void> addViewCount() {
		Setting viewCountSetting = settingMapper.selectById(5L);
		Integer viewCount = viewCountSetting.getViewCount();

		// 瀏覽數加1
		viewCountSetting.setViewCount(viewCount + 1);

		settingMapper.updateById(viewCountSetting);
		return R.ok();
	}

	@GetMapping("send-email-test")
	@Operation(summary = "測試是不是可以透過Unlayer寄信")
	public R<Void> sendMail() {

		// 開始編寫信件,準備寄給一般註冊者找回密碼的信
		try {
			MimeMessage message = mailSender.createMimeMessage();
			// message.setHeader("Content-Type", "text/html; charset=UTF-8");

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo("kamikazey2200@gmail.com");
			helper.setSubject("Unlayer寄信");

			String htmlContent = 
					"""
<!doctype html> <html> <head> <meta charset="utf-8"> <meta http-equiv="x-ua-compatible"content="ie=edge"> <title></title> <meta name="description"content=""> <meta name="viewport"content="width=device-width,initial-scale=1"> <style type="text/css">.u-row{display:flex;flex-wrap:nowrap;margin-left:0;margin-right:0}.u-row .u-col{position:relative;width:100%;padding-right:0;padding-left:0}.u-row .u-col.u-col-100{flex:0 0 100%;max-width:100%}@media (max-width:480px){.container{max-width:100%!important}.u-row:not(.no-stack){flex-wrap:wrap}.u-row:not(.no-stack) .u-col{flex:0 0 100%!important;max-width:100%!important}}body,html{margin:0;padding:0}html{box-sizing:border-box}*,:after,:before{box-sizing:inherit}html{font-size:14px;-ms-overflow-style:scrollbar;-webkit-tap-highlight-color:transparent}p{margin:0}form .error-field{-webkit-animation-duration:1s;animation-duration:1s;-webkit-animation-fill-mode:both;animation-fill-mode:both;-webkit-animation-name:shake;animation-name:shake}form .error-field input,form .error-field textarea{border-color:#a94442!important;color:#a94442!important}form .field-error{font-size:14px;font-weight:700;padding:5px 10px;position:absolute;right:10px;top:-20px}form .field-error:after{border:solid transparent;border-color:#ebcccc rgba(136,183,213,0) rgba(136,183,213,0);border-width:5px;content:" ";height:0;left:50%;margin-left:-5px;pointer-events:none;position:absolute;top:100%;width:0}form .spinner{margin:0 auto;text-align:center;width:70px}form .spinner>div{-webkit-animation:sk-bouncedelay 1.4s ease-in-out infinite both;animation:sk-bouncedelay 1.4s ease-in-out infinite both;background-color:hsla(0,0%,100%,.5);border-radius:100%;display:inline-block;height:12px;margin:0 2px;width:12px}form .spinner .bounce1{-webkit-animation-delay:-.32s;animation-delay:-.32s}form .spinner .bounce2{-webkit-animation-delay:-.16s;animation-delay:-.16s}@-webkit-keyframes sk-bouncedelay{0%,80%,to{-webkit-transform:scale(0)}40%{-webkit-transform:scale(1)}}@keyframes sk-bouncedelay{0%,80%,to{-webkit-transform:scale(0);transform:scale(0)}40%{-webkit-transform:scale(1);transform:scale(1)}}@-webkit-keyframes shake{0%,to{-webkit-transform:translateZ(0);transform:translateZ(0)}10%,30%,50%,70%,90%{-webkit-transform:translate3d(-10px,0,0);transform:translate3d(-10px,0,0)}20%,40%,60%,80%{-webkit-transform:translate3d(10px,0,0);transform:translate3d(10px,0,0)}}@keyframes shake{0%,to{-webkit-transform:translateZ(0);transform:translateZ(0)}10%,30%,50%,70%,90%{-webkit-transform:translate3d(-10px,0,0);transform:translate3d(-10px,0,0)}20%,40%,60%,80%{-webkit-transform:translate3d(10px,0,0);transform:translate3d(10px,0,0)}}.container{--bs-gutter-x:0px;--bs-gutter-y:0;margin-left:auto;margin-right:auto;padding-left:calc(var(--bs-gutter-x)*.5);padding-right:calc(var(--bs-gutter-x)*.5);width:100%}a[onclick]{cursor:pointer}body{font-family:arial,helvetica,sans-serif;font-size:1rem;line-height:1.5;color:#000;background-color:transparent}#u_body a{color:#00e;text-decoration:underline}#u_body a:hover{color:#00e;text-decoration:underline}</style> </head> <body> <div id="u_body"class="u_body"style="min-height:100vh"> <div id="u_row_1"class="u_row"style="padding:0"> <div class="container"style="max-width:500px;margin:0 auto"> <div class="u-row"> <div id="u_column_1"class="u-col u-col-100 u_column"style="display:flex;background-color:transparent;border-top:0 solid transparent;border-left:0 solid transparent;border-right:0 solid transparent;border-bottom:0 solid transparent;border-radius:0"> <div style="width:100%;padding:0"> <div id="u_content_text_1"class="u_content_text"style="overflow-wrap:break-word;padding:10px"> <div style="font-size:14px;line-height:140%;text-align:left;word-wrap:break-word"> <p style="line-height:140%">This is a new Text block. Change the text</p> <p style="line-height:140%"> </p> <p style="line-height:140%"> </p> <p style="line-height:140%"> </p> <p style="line-height:140%"> </p> <p style="line-height:140%"><br>have a nice day</p> <p style="line-height:140%"> </p> <p style="line-height:140%"> </p> <p style="line-height:140%"> </p> <p style="line-height:140%"> </p> <p style="line-height:140%"><br>best</p> </div> </div> </div> </div> </div> </div> </div> </div> </body> </html>

					""";

			String plainTextContent = "Unlayer寄信";

			helper.setText(plainTextContent, false); // 纯文本版本
			helper.setText(htmlContent, true); // HTML 版本

			mailSender.send(message);

		} catch (MessagingException e) {
			System.err.println("發送郵件失敗: " + e.getMessage());
		}

		return R.ok();
	}

}
