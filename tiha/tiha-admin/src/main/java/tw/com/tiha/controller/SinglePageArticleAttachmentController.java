package tw.com.tiha.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.com.tiha.pojo.DTO.InsertSinglePageArticleAttachmentDTO;
import tw.com.tiha.pojo.entity.SinglePageArticleAttachment;
import tw.com.tiha.service.SinglePageArticleAttachmentService;
import tw.com.tiha.utils.R;


/**
 * <p>
 * 單頁文章附件表，儲存單頁內容的附件 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Tag(name = "單頁文章附件API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/single-page-article-attachment")
public class SinglePageArticleAttachmentController {

	private final SinglePageArticleAttachmentService singlePageArticleAttachmentService;

	@GetMapping("{singlePageArticleId}")
	@Operation(summary = "根據單頁文章ID，查詢文章所有附件")
	public R<List<SinglePageArticleAttachment>> getAllArticleAttachment(
			@PathVariable("singlePageArticleId") Long singlePageArticleId) {
		List<SinglePageArticleAttachment> singlePageArticleAttachmentList = singlePageArticleAttachmentService
				.getAllSinglePageArticleAttachmentByArticleId(singlePageArticleId);
		return R.ok(singlePageArticleAttachmentList);
	}

	@PostMapping
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER),
			@Parameter(name = "data", description = "JSON 格式的附件資料", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = InsertSinglePageArticleAttachmentDTO.class)) })
	@SaCheckLogin
	@Operation(summary = "為某個單頁文章新增附件")
	public R<Void> addArticleAttachment(@RequestParam("file") MultipartFile[] files,
			@RequestParam("data") String jsonData) throws JsonMappingException, JsonProcessingException {
		// 將 JSON 字符串轉為對象
		ObjectMapper objectMapper = new ObjectMapper();
		InsertSinglePageArticleAttachmentDTO insertSinglePageArticleAttachmentDTO = objectMapper.readValue(jsonData,
				InsertSinglePageArticleAttachmentDTO.class);

		// 將檔案和資料對象傳給後端
		singlePageArticleAttachmentService.insertSinglePageArticleAttachment(insertSinglePageArticleAttachmentDTO,
				files);

		return R.ok();
	}

	@DeleteMapping("{id}")
	@SaCheckLogin
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@Operation(summary = "根據 singlePageArticleAttachmentId 來刪除單頁文章附件")
	public R<Void> deleteFile(@PathVariable("id") Long singlePageArticleAttachmentId) {
		singlePageArticleAttachmentService.deleteSinglePageArticleAttachment(singlePageArticleAttachmentId);
		return R.ok();
	}

}
