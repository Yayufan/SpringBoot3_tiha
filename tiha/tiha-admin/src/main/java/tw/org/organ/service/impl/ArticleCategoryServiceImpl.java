package tw.org.organ.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.ArticleCategoryConvert;
import tw.org.organ.mapper.ArticleCategoryMapper;
import tw.org.organ.pojo.DTO.InsertArticleCategoryDTO;
import tw.org.organ.pojo.entity.ArticleCategory;
import tw.org.organ.service.ArticleCategoryService;
import tw.org.organ.utils.TreeUtil;

/**
 * <p>
 * 文章的細部類別 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */

@RequiredArgsConstructor
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory>
		implements ArticleCategoryService {

	private final ArticleCategoryConvert articleCategoryConvert;

	@Override
	public ArticleCategory getArticleCategory(Long categoryId) {
		// TODO Auto-generated method stub
		ArticleCategory articleCategory = baseMapper.selectById(categoryId);
		return articleCategory;
	}
	
	@Override
	public List<ArticleCategory> getAllArticleCategory() {
		// TODO Auto-generated method stub
		List<ArticleCategory> categoryList = baseMapper.selectList(null);
		List<ArticleCategory> categoryTreeList = TreeUtil.buildTree(categoryList);
		return categoryTreeList;

	}

	@Override
	public List<ArticleCategory> getAllArticleCategory(String group) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<ArticleCategory> categoryQueryWrapper = new LambdaQueryWrapper<>();
		categoryQueryWrapper.eq(ArticleCategory::getGroupType, group);

		List<ArticleCategory> categoryList = baseMapper.selectList(categoryQueryWrapper);
		List<ArticleCategory> categoryTreeList = TreeUtil.buildTree(categoryList);

		return categoryTreeList;
	}

	@Override
	public void insertArticleCategory(InsertArticleCategoryDTO insertArticleCategoryDTO) {
		// TODO Auto-generated method stub
		ArticleCategory articleCategory = articleCategoryConvert.insertDTOToEntity(insertArticleCategoryDTO);
		this.saveOrUpdate(articleCategory);
	}

	@Override
	public void deleteArticleCategory(Long articleCategoryId) {
		// TODO Auto-generated method stub

		List<ArticleCategory> children = findChildren(articleCategoryId);

		// 遞歸刪除子節點
		for (ArticleCategory child : children) {
			deleteArticleCategory(child.getArticleCategoryId());
		}

		// 最後刪除當前節點
		baseMapper.deleteById(articleCategoryId);

	}

	@Override
	public void deleteArticleCategory(List<Long> articleCategoryIdList) {
		// TODO Auto-generated method stub
		for (Long articleCategoryId : articleCategoryIdList) {
			// 執行單個ID遞歸刪除
			this.deleteArticleCategory(articleCategoryId);
		}
	}

	private List<ArticleCategory> findChildren(Long parentId) {
		LambdaQueryWrapper<ArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ArticleCategory::getParentId, parentId);
		return baseMapper.selectList(queryWrapper);
	}



}
