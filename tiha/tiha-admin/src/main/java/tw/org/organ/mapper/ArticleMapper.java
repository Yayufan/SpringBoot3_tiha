package tw.org.organ.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tw.org.organ.pojo.entity.Article;

/**
 * <p>
 * 文章表 - 各個group的文章都儲存在這 Mapper 接口
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */
public interface ArticleMapper extends BaseMapper<Article> {

	 // 獲取總瀏覽量
    Long getViewCount();
	
}
