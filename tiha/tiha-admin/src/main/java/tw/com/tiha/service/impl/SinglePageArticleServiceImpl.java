package tw.com.tiha.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.com.tiha.convert.SinglePageArticleConvert;
import tw.com.tiha.exception.ExistPageException;
import tw.com.tiha.mapper.SinglePageArticleMapper;
import tw.com.tiha.pojo.DTO.InsertSinglePageArticleDTO;
import tw.com.tiha.pojo.DTO.UpdateSinglePageArticleDTO;
import tw.com.tiha.pojo.entity.SinglePageArticle;
import tw.com.tiha.service.CmsService;
import tw.com.tiha.service.SinglePageArticleService;
import tw.com.tiha.utils.ArticleViewsCounterUtil;

/**
 * <p>
 * 單頁文章，用來設計簡單的單頁內容 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Service
@RequiredArgsConstructor
public class SinglePageArticleServiceImpl extends ServiceImpl<SinglePageArticleMapper, SinglePageArticle>
		implements SinglePageArticleService {

	@Value("${minio.bucketName}")
	private String minioBucketName;

	private final SinglePageArticleConvert singlePageArticleConvert;
	private final ArticleViewsCounterUtil articleViewsCounterUtil;
	private final CmsService cmsService;

	@Override
	public SinglePageArticle getSinglePageArticle(String path) {
		LambdaQueryWrapper<SinglePageArticle> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SinglePageArticle::getPath, path);
		SinglePageArticle singlePageArticle = baseMapper.selectOne(queryWrapper);

		if (singlePageArticle == null) {
			SinglePageArticle emptySinglePageArticle = new SinglePageArticle();
			emptySinglePageArticle.setPath(path);
			// 這行主要是避免前端CKEditor 因為 content 為null 而報錯
			emptySinglePageArticle.setContent("");
			baseMapper.insert(emptySinglePageArticle);
			return emptySinglePageArticle;
		}

		return singlePageArticle;
	}

	@Override
	public SinglePageArticle getShowSinglePageArticle(String path) {
		LambdaQueryWrapper<SinglePageArticle> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SinglePageArticle::getPath, path);
		SinglePageArticle singlePageArticle = baseMapper.selectOne(queryWrapper);

		articleViewsCounterUtil.incrementViewCount(path, singlePageArticle.getSinglePageArticleId());
		return singlePageArticle;
	}

	@Override
	public Long insertSinglePageArticle(InsertSinglePageArticleDTO insertSinglePageArticleDTO) {
		LambdaQueryWrapper<SinglePageArticle> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SinglePageArticle::getPath, insertSinglePageArticleDTO.getPath());
		boolean exists = baseMapper.exists(queryWrapper);

		if (exists) {
			throw new ExistPageException("已經存在此頁面，請勿重複創建");
		}
		SinglePageArticle singlePageArticle = singlePageArticleConvert.insertDTOToEntity(insertSinglePageArticleDTO);

		// 直接創建一個空頁面
		SinglePageArticle emptySinglePageArticle = new SinglePageArticle();

		// 新增
		baseMapper.insert(singlePageArticle);

		return singlePageArticle.getSinglePageArticleId();
	}

	@Override
	public void updateSinglePageArticle(UpdateSinglePageArticleDTO updateSinglePageArticleDTO) {
		System.out.println("這是傳來的更新資料: " + updateSinglePageArticleDTO);

		// 先拿到舊的資料
		SinglePageArticle originalArticle = baseMapper.selectById(updateSinglePageArticleDTO.getSinglePageArticleId());

		// 拿到本次資料
		SinglePageArticle article = singlePageArticleConvert.updateDTOToEntity(updateSinglePageArticleDTO);

		// 獲取當前頁面有上傳過的圖片URL網址
		List<String> tempUploadUrl = updateSinglePageArticleDTO.getTempUploadUrl();

		// 獲取本次資料傳來的HTML字符串
		String newContent = article.getContent();

		// 獲得舊的資料的HTML字符串
		String oldContent = originalArticle.getContent();

		// 最後移除舊的無使用的圖片以及臨時的圖片路徑
		cmsService.cleanNotUsedImg(newContent, oldContent, tempUploadUrl, minioBucketName);

		// 更新數據
		baseMapper.updateById(article);

	}

	@Override
	public void deleteSinglePageArticle(Long singlePageArticleId) {
		// TODO Auto-generated method stub

	}

}
