package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertArticleCategoryDTO;
import tw.org.organ.pojo.entity.ArticleCategory;
import tw.org.organ.service.ArticleCategoryService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 文章的細部類別 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */
@Tag(name = "文章類別API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/article-category")
public class ArticleCategoryController {
	
	private final ArticleCategoryService articleCategoryService;
	
	@GetMapping("category/{categoryId}")
	@Operation(summary = "根據ID查詢文章類別")
	public R<ArticleCategory> getArticleCategory(@PathVariable("categoryId") Long categoryId) {
		ArticleCategory articleCategory = articleCategoryService.getArticleCategory(categoryId);
		return R.ok(articleCategory);
	}
	

	@GetMapping("{group}")
	@Operation(summary = "根據組別查詢所有文章類別")
	public R<List<ArticleCategory>> getAllArticleCategory(@PathVariable("group") String group) {

		System.out.println("組別為:"+group);
		List<ArticleCategory> articleCategoryList = articleCategoryService
				.getAllArticleCategory(group);
		return R.ok(articleCategoryList);
	}


	@Operation(summary = "新增文章類別")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PostMapping
	public R<Void> saveArticleCategory(
			@Validated @RequestBody InsertArticleCategoryDTO insertArticleCategoryDTO) {
		articleCategoryService.insertArticleCategory(insertArticleCategoryDTO);
		return R.ok();

	}

	@Operation(summary = "刪除文章類別")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteArticleCategory(@PathVariable("id") Long articleCategoryId) {
		articleCategoryService.deleteArticleCategory(articleCategoryId);
		return R.ok();

	}

	@Operation(summary = "批量刪除文章類別")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping()
	public R<Void> deleteArticleCategory(
			@Valid @NotNull @RequestBody List<Long> articleCategoryIdList) {
		articleCategoryService.deleteArticleCategory(articleCategoryIdList);
		return R.ok();
	}

	

}
