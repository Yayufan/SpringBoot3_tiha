package tw.org.organ.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import tw.org.organ.pojo.DTO.InsertArticleCategoryDTO;
import tw.org.organ.pojo.entity.ArticleCategory;

/**
 * <p>
 * 文章的細部類別 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {

	/**
	 * 根據id獲取文章類別
	 * @param categoryId
	 * @return
	 */
	ArticleCategory getArticleCategory(Long categoryId);
	
	/**
	 * 獲取全部文章類別
	 * 
	 * @return
	 */
	List<ArticleCategory> getAllArticleCategory();
	
	/**
	 * 查詢某個組別的所有文章類別
	 * 
	 * @param group
	 * @return
	 */
	List<ArticleCategory> getAllArticleCategory(String group);


	/**
	 * 新增文章類別
	 * 
	 * @param insertArticleCategoryDTO
	 */
	void insertArticleCategory(InsertArticleCategoryDTO insertArticleCategoryDTO);


	/**
	 * 根據ArticleCategoryId刪除文章類別
	 * 
	 * @param articleCategoryId
	 */
	void deleteArticleCategory(Long articleCategoryId);
	
	
	/**
	 * 批量刪除文章類別
	 * @param articleCategoryIdList
	 */
	void deleteArticleCategory(List<Long> articleCategoryIdList);
	
	
	
	
}
