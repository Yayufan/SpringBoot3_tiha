package tw.com.tiha.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.com.tiha.pojo.DTO.UpdateSinglePageArticleDTO;
import tw.com.tiha.pojo.entity.SinglePageArticle;
import tw.com.tiha.service.SinglePageArticleService;
import tw.com.tiha.utils.R;

/**
 * <p>
 * 單頁文章，用來設計簡單的單頁內容 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Tag(name = "單一頁面文章API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/single-page-article")
public class SinglePageArticleController {
	private final SinglePageArticleService singlePageArticleService;

	@GetMapping("by-path")
	@Operation(summary = "根據path，查詢單一頁面文章(For管理後台)")
	public R<SinglePageArticle> getSinglePageArticle(@RequestParam("path") String path) {
		SinglePageArticle singlePageArticle = singlePageArticleService.getSinglePageArticle(path);
		return R.ok(singlePageArticle);
	}
	
	@GetMapping("show/by-path")
	@Operation(summary = "根據path，查詢單一頁面文章(For形象頁面)")
	public R<SinglePageArticle> getSinglePageArticleShow(@RequestParam("path") String path) {
		SinglePageArticle singlePageArticle = singlePageArticleService.getShowSinglePageArticle(path);
		return R.ok(singlePageArticle);
	}
	
	@Operation(summary = "更新單一頁面文章")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateSinglePageArticle(
			@RequestBody @Validated UpdateSinglePageArticleDTO updateSinglePageArticleDTO
			) {
		singlePageArticleService.updateSinglePageArticle(updateSinglePageArticleDTO);
		return R.ok();

	}

}
