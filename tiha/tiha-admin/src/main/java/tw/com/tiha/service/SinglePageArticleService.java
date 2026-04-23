package tw.com.tiha.service;

import com.baomidou.mybatisplus.extension.service.IService;

import tw.com.tiha.pojo.DTO.InsertSinglePageArticleDTO;
import tw.com.tiha.pojo.DTO.UpdateSinglePageArticleDTO;
import tw.com.tiha.pojo.entity.SinglePageArticle;

/**
 * <p>
 * 單頁單頁文章，用來設計簡單的單頁內容 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
public interface SinglePageArticleService extends IService<SinglePageArticle> {


	/**
	 * 根據路徑,獲取單一單頁文章，For後台
	 * 如果沒有這個路徑則會立刻創建
	 * 
	 * @param singlePageArticleId
	 * @return
	 */
	SinglePageArticle getSinglePageArticle(String path);
	
	

	/**
	 * 獲取單一單頁文章，並增加該篇單頁文章瀏覽量
	 * 
	 * @param singlePageArticleId
	 * @return
	 */
	SinglePageArticle getShowSinglePageArticle(String path);


	/**
	 * 新增單一單頁文章
	 * 
	 * @param insertSinglePageArticleDTO
	 */
	Long insertSinglePageArticle(InsertSinglePageArticleDTO insertSinglePageArticleDTO);

	/**
	 * 更新單一單頁文章
	 * 
	 * @param updateSinglePageArticleDTO
	 */
	void updateSinglePageArticle(UpdateSinglePageArticleDTO updateSinglePageArticleDTO);

	
	/**
	 * 根據SinglePageArticleId刪除單頁文章
	 * 
	 * @param singlePageArticleId
	 */
	void deleteSinglePageArticle(Long singlePageArticleId);

	
	
}
