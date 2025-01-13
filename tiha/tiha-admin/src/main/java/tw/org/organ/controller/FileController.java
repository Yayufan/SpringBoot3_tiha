package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertFileDTO;
import tw.org.organ.pojo.entity.File;
import tw.org.organ.service.FileService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 上傳檔案表，用於應用在只有上傳檔案的頁面 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Tag(name = "上傳至檔案中心API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

	private final FileService fileService;
	
	@GetMapping("{group}")
	@Operation(summary = "查詢某個組別所有檔案")
	public R<List<File>> getAllFileByGroup(@PathVariable("group") String group) {
		List<File> fileList = fileService.getAllFileByGroup(group);
		return R.ok(fileList);
	}

	@GetMapping("{group}/{type}")
	@Operation(summary = "查詢某個組別及類別的所有文章")
	public R<List<File>> getAllFileByGroupAndType(@PathVariable("group") String group,@PathVariable("type") String type) {
		List<File> fileList = fileService.getAllFileByGroupAndType(group, type);
		return R.ok(fileList);
	}

	@GetMapping("{group}/pagination")
	@Operation(summary = "查詢某個組別所有檔案(分頁)")
	public R<IPage<File>> getAllFileByGroup(@PathVariable("group") String group, @RequestParam Integer page,
			@RequestParam Integer size) {
		Page<File> pageInfo = new Page<>(page, size);
		IPage<File> fileList = fileService.getAllFileByGroup(group, pageInfo);
		System.out.println("已對檔案中心進行查詢");
		return R.ok(fileList);
	}

	@PostMapping
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@Operation(summary = "新增檔案至某個類別")
	public R<Void> addFile(@RequestParam("file") MultipartFile[] file, @RequestParam("data") String jsonData)
			throws JsonMappingException, JsonProcessingException {
		// 將 JSON 字符串轉為對象
		ObjectMapper objectMapper = new ObjectMapper();
		InsertFileDTO insertFileDTO = objectMapper.readValue(jsonData, InsertFileDTO.class);

		// 將檔案和資料對象傳給後端
		fileService.addFile(file, insertFileDTO);

		return R.ok();
	}

	@DeleteMapping("{id}")
	@SaCheckLogin
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@Operation(summary = "根據ID來刪除檔案")
	public R<Void> deleteFile(@PathVariable("id") Long fileId) {
		fileService.deleteFile(fileId);
		return R.ok();
	}

	@Operation(summary = "批量刪除文章")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping()
	public R<Void> batchDeleteArticle(@Valid @NotNull @RequestBody List<Long> fileIdList) {
		fileService.deleteFile(fileIdList);
		return R.ok();
	}

}
