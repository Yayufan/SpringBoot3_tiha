package tw.org.organ.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.ArticleConvert;
import tw.org.organ.enums.EnabledStatusEnum;
import tw.org.organ.enums.SettingEnum;
import tw.org.organ.mapper.ArticleCategoryMapper;
import tw.org.organ.mapper.ArticleMapper;
import tw.org.organ.mapper.SettingMapper;
import tw.org.organ.pojo.DTO.InsertArticleDTO;
import tw.org.organ.pojo.DTO.UpdateArticleDTO;
import tw.org.organ.pojo.entity.Article;
import tw.org.organ.pojo.entity.ArticleCategory;
import tw.org.organ.pojo.entity.Setting;
import tw.org.organ.service.ArticleService;
import tw.org.organ.service.CmsService;
import tw.org.organ.utils.ArticleViewsCounterUtil;
import tw.org.organ.utils.MinioUtil;

/**
 * <p>
 * 文章表 - 各個group的文章都儲存在這 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

	private static final String DEFAULT_IMAGE_PATH = "/organ/default-image/cta-img-1.jpg";

	@Value("${minio.bucketName}")
	private String minioBucketName;

	private final ArticleViewsCounterUtil articleViewsCounterUtil;
	private final MinioUtil minioUtil;
	private final ArticleConvert articleConvert;
	private final ArticleCategoryMapper articleCategoryMapper;
	private final SettingMapper settingMapper;
	private final CmsService cmsService;

	@Override
	public List<Article> getAllArticle() {
		// TODO Auto-generated method stub
		List<Article> articleList = baseMapper.selectList(null);
		return articleList;
	}

	@Override
	public IPage<Article> getAllArticle(Page<Article> page) {
		// TODO Auto-generated method stub
		Page<Article> articleList = baseMapper.selectPage(page, null);
		return articleList;
	}

	@Override
	public List<Article> getAllArticleByGroup(String group) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getGroupType, group);

		List<Article> articleList = baseMapper.selectList(articleQueryWrapper);
		return articleList;
	}

	@Override
	public IPage<Article> getAllArticleByGroup(String group, Page<Article> page) {
		// TODO Auto-generated method stub
		// 查詢群組、分頁，並倒序排列
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getGroupType, group).orderByDesc(Article::getArticleId);
		Page<Article> articleList = baseMapper.selectPage(page, articleQueryWrapper);

		return articleList;
	}

	@Override
	public List<Article> getAllArticleByGroupAndCategory(String group, Long category) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getGroupType, group).eq(Article::getCategoryId, category);

		List<Article> articleList = baseMapper.selectList(articleQueryWrapper);
		return articleList;
	}

	@Override
	public IPage<Article> getAllArticleByGroupAndCategory(String group, Long category, Page<Article> page) {
		// TODO Auto-generated method stub
		// 查詢群組、分頁，並倒序排列
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getGroupType, group).eq(Article::getCategoryId, category)
				.orderByDesc(Article::getArticleId);
		Page<Article> articleList = baseMapper.selectPage(page, articleQueryWrapper);

		return articleList;
	}

	@Override
	public Article getArticle(Long articleId) {
		// TODO Auto-generated method stub
		Article article = baseMapper.selectById(articleId);
		return article;
	}

	@Override
	public Article getShowArticle(Long articleId) {
		// TODO Auto-generated method stub
		Article article = baseMapper.selectById(articleId);

		articleViewsCounterUtil.incrementViewCount(article.getGroupType(), articleId);
		return article;
	}

	@Override
	public Long getArticleCount() {
		// TODO Auto-generated method stub
		Long articleCount = baseMapper.selectCount(null);
		return articleCount;
	}

	@Override
	public Long getArticleCountByGroup(String group) {
		// TODO Auto-generated method stub
		LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
		articleQueryWrapper.eq(Article::getGroupType, group);
		
		Long articleCount = baseMapper.selectCount(articleQueryWrapper);

		return articleCount;
	}

	@Override
	public Long getArticleViewsCount() {
		Long viewCount = baseMapper.getViewCount();
		return viewCount;
	}
	
	@Override
	public Long getArticleViewsCountByGroup(String group) {
		// TODO Auto-generated method stub
		Long groupViewCount = articleViewsCounterUtil.getTotalViewCount(group);
		return groupViewCount;
	}

	@Override
	public Long insertArticle(InsertArticleDTO insertArticleDTO, MultipartFile[] files) {
		// TODO Auto-generated method stub
		// 先獲取所屬類別的名稱,這邊一起新增進table裡這樣方便調取
		ArticleCategory articleCategory = articleCategoryMapper.selectById(insertArticleDTO.getCategoryId());
		String typeName = (articleCategory != null) ? articleCategory.getName() : insertArticleDTO.getType();
		Article article = articleConvert.insertDTOToEntity(insertArticleDTO);

		// 檔案存在，處理檔案
		if (files != null && files.length > 0) {

			List<String> upload = minioUtil.upload(minioBucketName, article.getGroupType() + "/", files);
			// 基本上只有有一個檔案跟著formData上傳,所以這邊直接寫死,把唯一的url增添進對象中
			String url = upload.get(0);
			// 將bucketName 組裝進url
			url = "/" + minioBucketName + "/" + url;
			// minio完整路徑放路對象中
			article.setCoverThumbnailUrl(url);
			// 將類別名稱放入對象中
			article.setType(typeName);

			// 放入資料庫
			baseMapper.insert(article);

		} else {
			// 沒有檔案,直接處理數據
			// 將類別名稱放入對象中
			article.setType(typeName);
			article.setCoverThumbnailUrl(DEFAULT_IMAGE_PATH);
			baseMapper.insert(article);
		}

		// 這邊將新增的ID獨立出來,為了要當作別人的asyncId使用
		Long currentArticleId = article.getArticleId();

		// 如果groupType不等於 news 或者 medicalKnowledge 才往下判斷設定狀態
		if (!article.getGroupType().equals(SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getGroup())
				&& !article.getGroupType().equals(SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getGroup())) {

			// 判斷最新消息有無同步新增需求,有則執行下列
			Setting asyncNewsSetting = settingMapper.selectById(SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getValue());
			// 如果設定為開啟,且當前文章的groupType不為news
			if (asyncNewsSetting.getStatus().equals(EnabledStatusEnum.ENABLED.getCode())) {

				LocalDate currentDate = LocalDate.now();
				article.setArticleId(null);
				article.setAnnouncementDate(currentDate);
				article.setAsyncId(currentArticleId);
				article.setGroupType(SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getGroup());

				// 新增同步ID,這是為了之後更新文章內容時,也同步到最新消息那邊
				baseMapper.insert(article);

			}

			// 判斷醫療新知有無新增同步需求,有則執行下列
			Setting asyncEducationSurgerySetting = settingMapper
					.selectById(SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getValue());
			if (asyncEducationSurgerySetting.getStatus().equals(EnabledStatusEnum.ENABLED.getCode()))

			{

				// 新增同步ID,這是為了之後更新文章內容時,也同步到醫療新知那邊
				article.setArticleId(null);
				article.setGroupType(SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getGroup());
				baseMapper.insert(article);
			}

		}

		// 最後都返回自增ID
		return currentArticleId;

	}

	@Override
	public void updateArticle(UpdateArticleDTO updateArticleDTO, MultipartFile[] files) {
		// TODO Auto-generated method stub

		// 先拿到舊的資料
		Article originalArticle = baseMapper.selectById(updateArticleDTO.getArticleId());

		// 拿到本次資料
		Article article = articleConvert.updateDTOToEntity(updateArticleDTO);

		// 獲取當前頁面有上傳過的圖片URL網址
		List<String> tempUploadUrl = updateArticleDTO.getTempUploadUrl();

		// 獲取本次資料傳來的HTML字符串
		String newContent = article.getContent();

		// 獲得舊的資料的HTML字符串
		String oldContent = originalArticle.getContent();

		// 先判斷這個要修改的文章,他是不是關聯其他文章，兩邊最大的不同就是對檔案的處理
		// 如果有關連其他文章,要小心不要刪除到原文章的圖檔
		if (updateArticleDTO.getAsyncId() != null) {

			// 獲取關聯的文章
			LambdaQueryWrapper<Article> mainArticleQueryWrapper = new LambdaQueryWrapper<>();
			mainArticleQueryWrapper.eq(Article::getArticleId, updateArticleDTO.getAsyncId());
			Article mainArticle = baseMapper.selectOne(mainArticleQueryWrapper);

			// 檔案存在，處理檔案
			if (files != null && files.length > 0) {

				// 獲取之前的縮圖,並刪除之前的圖檔
				String coverThumbnailUrl = originalArticle.getCoverThumbnailUrl();

				// 因為縮圖圖檔URL有包含 scuro, 這邊先進行截斷
				String result = coverThumbnailUrl.substring(coverThumbnailUrl.indexOf("/", 1));

				// 如果原縮圖不為預設值,且縮圖跟關聯的文章不一致,圖片才進行刪除
				if (!coverThumbnailUrl.equals(DEFAULT_IMAGE_PATH)
						&& !coverThumbnailUrl.equals(mainArticle.getCoverThumbnailUrl())) {
					minioUtil.removeObject(minioBucketName, result);
				}

				// 上傳檔案
				List<String> upload = minioUtil.upload(minioBucketName, article.getGroupType() + "/", files);
				// 基本上只有有一個檔案跟著formData上傳,所以這邊直接寫死,把唯一的url增添進對象中
				String url = upload.get(0);
				// 將bucketName 組裝進url
				url = "/" + minioBucketName + "/" + url;
				// minio完整路徑放路對象中
				article.setCoverThumbnailUrl(url);
			}

			// 清除沒有用到的檔案
			// 清除在oldContent中出現,但在newContent和主文章中沒有出現的檔案
			// 清除暫存檔案
			cmsService.cleanNotUsedImg(newContent, oldContent, mainArticle.getContent(), tempUploadUrl,
					minioBucketName);

			// 更新數據
			baseMapper.updateById(article);

		} else {

			// 如果類別沒變,則不動
			if (updateArticleDTO.getCategoryId() != originalArticle.getCategoryId()) {
				String typeName = articleCategoryMapper.selectById(updateArticleDTO.getCategoryId()).getName();
				article.setType(typeName);
			}

			// 檔案存在，處理檔案
			if (files != null && files.length > 0) {

				// 獲取之前的縮圖,並刪除之前的圖檔
				String coverThumbnailUrl = originalArticle.getCoverThumbnailUrl();

				// 因為縮圖圖檔URL有包含 scuro, 這邊先進行截斷
				String result = coverThumbnailUrl.substring(coverThumbnailUrl.indexOf("/", 1));

				// 如果原縮圖不為預設值,圖片進行刪除
				if (!coverThumbnailUrl.equals(DEFAULT_IMAGE_PATH)) {
					minioUtil.removeObject(minioBucketName, result);
				}

				// 上傳檔案
				List<String> upload = minioUtil.upload(minioBucketName, article.getGroupType() + "/", files);
				// 基本上只有有一個檔案跟著formData上傳,所以這邊直接寫死,把唯一的url增添進對象中
				String url = upload.get(0);
				// 將bucketName 組裝進url
				url = "/" + minioBucketName + "/" + url;
				// minio完整路徑放路對象中
				article.setCoverThumbnailUrl(url);
			}

			// 判斷最新消息有無同步更新需求,有則執行下列
			Setting asyncNewsSetting = settingMapper.selectById(SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getValue());
			if (asyncNewsSetting.getStatus().equals(EnabledStatusEnum.ENABLED.getCode())) {

				// 組裝查詢條件,如果有最新消息的同步需求,則要找asyncId 和 本文章articleId一致
				// 且 group 為news的資料,讓資料同步更新
				LambdaQueryWrapper<Article> asyncQueryWrapper = new LambdaQueryWrapper<>();
				asyncQueryWrapper.eq(Article::getGroupType, SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getGroup())
						.eq(Article::getAsyncId, article.getArticleId());

				// 複製這次更新的對象,用來更新被同步的文章
				Article asyncNewsEntity = articleConvert.copyEntity(article);

				// 查詢舊的資料
				Article originalAsyncNewsArticle = baseMapper.selectOne(asyncQueryWrapper);

				// 如果這篇被同步的資料沒有被刪除,則開始更新並清空原本的圖檔資料
				if (originalAsyncNewsArticle != null) {
					cmsService.cleanNotUsedImg(newContent, originalAsyncNewsArticle.getContent(), tempUploadUrl,
							minioBucketName);
					asyncNewsEntity.setArticleId(originalAsyncNewsArticle.getArticleId());
					asyncNewsEntity.setGroupType(originalAsyncNewsArticle.getGroupType());
					asyncNewsEntity.setAsyncId(originalAsyncNewsArticle.getAsyncId());

					baseMapper.update(asyncNewsEntity, asyncQueryWrapper);
				}

			}

			// 判斷醫療新知有無更新同步需求,有則執行下列
			Setting asyncEducationSurgerySetting = settingMapper
					.selectById(SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getValue());
			if (asyncEducationSurgerySetting.getStatus().equals(EnabledStatusEnum.ENABLED.getCode())) {

				// 組裝查詢條件,如果有最新消息的同步需求,則要找asyncId 和 本文章articleId一致
				// 且 group 為medicalKnowledge的資料,讓資料同步更新
				LambdaQueryWrapper<Article> asyncQueryWrapper = new LambdaQueryWrapper<>();
				asyncQueryWrapper
						.eq(Article::getGroupType, SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getGroup())
						.eq(Article::getAsyncId, article.getArticleId());

				// 複製這次更新的對象,用來更新被同步的文章
				Article asyncMedicalKnowledgeEntity = articleConvert.copyEntity(article);

				// 查詢舊的資料
				Article originalAsyncMedicalKnowledgeArticle = baseMapper.selectOne(asyncQueryWrapper);

				// 如果這篇被同步的資料沒有被刪除,則開始更新並清空原本的圖檔資料
				if (originalAsyncMedicalKnowledgeArticle != null) {
					cmsService.cleanNotUsedImg(newContent, originalAsyncMedicalKnowledgeArticle.getContent(),
							tempUploadUrl, minioBucketName);
					asyncMedicalKnowledgeEntity.setArticleId(originalAsyncMedicalKnowledgeArticle.getArticleId());
					asyncMedicalKnowledgeEntity.setGroupType(originalAsyncMedicalKnowledgeArticle.getGroupType());
					asyncMedicalKnowledgeEntity.setAsyncId(originalAsyncMedicalKnowledgeArticle.getAsyncId());

					// 獲取之前的縮圖,並刪除之前的圖檔
					String coverThumbnailUrl = originalAsyncMedicalKnowledgeArticle.getCoverThumbnailUrl();

					// 因為縮圖圖檔URL有包含 scuro, 這邊先進行截斷
					String result = coverThumbnailUrl.substring(coverThumbnailUrl.indexOf("/", 1));

					// 如果同步在醫療新知的縮圖不為預設值,圖片進行刪除
					if (!coverThumbnailUrl.equals(DEFAULT_IMAGE_PATH)) {
						minioUtil.removeObject(minioBucketName, result);
					}

					baseMapper.update(asyncMedicalKnowledgeEntity, asyncQueryWrapper);
				}

			}

			// 最後移除舊的無使用的圖片以及臨時的圖片路徑
			cmsService.cleanNotUsedImg(newContent, oldContent, tempUploadUrl, minioBucketName);

			// 更新數據
			baseMapper.updateById(article);

		}

	}

	@Override
	public void deleteArticle(Long articleId) {
		// TODO Auto-generated method stub
		Article article = baseMapper.selectById(articleId);

		// 如果這篇文章的同步ID不為null，代表它有關聯一個主文章
		if (article.getAsyncId() != null) {

			// 先找到自己關聯的主文章
			LambdaQueryWrapper<Article> asyncQueryWrapper = new LambdaQueryWrapper<>();
			asyncQueryWrapper.eq(Article::getArticleId, article.getAsyncId());
			Article mainArticle = baseMapper.selectOne(asyncQueryWrapper);

			// 清除自己的縮圖，注意別刪到主文章縮圖
			String coverThumbnailUrl = article.getCoverThumbnailUrl();

			// 因為縮圖圖檔URL有包含 scuro, 這邊先進行截斷
			String result = coverThumbnailUrl.substring(coverThumbnailUrl.indexOf("/", 1));

			// 如果縮圖不為預設值 且 縮圖跟主文章的路徑不一致,縮圖才進行刪除
			if (!coverThumbnailUrl.equals(DEFAULT_IMAGE_PATH)
					&& !coverThumbnailUrl.equals(mainArticle.getCoverThumbnailUrl())) {
				minioUtil.removeObject(minioBucketName, result);
			}

			// 清除自己的內容圖片，注意別刪到主文章內容圖片
			// 創建空的List符合function 所需參數
			List<String> emptyList = new ArrayList<>();

			// 清除沒有用到的檔案
			// 清除在oldContent中出現,但在newContent和主文章中沒有出現的檔案
			// 清除暫存檔案
			cmsService.cleanNotUsedImg(mainArticle.getContent(), article.getContent(), emptyList, minioBucketName);

			// 清除自己的資料
			// 最後都將這筆資料從數據庫中刪除
			baseMapper.deleteById(articleId);

		} else {
			/**
			 * 如果這篇文章的同步ID為null，代表他就是一個主文章 主文章要去找尋他有沒有引用這篇文章的資料， 清除引用文章的縮圖以及內容圖片 最後去刪除這筆資料
			 * 這邊要先做一個判斷,看設置上有沒有開啟同步
			 * 
			 */

			// 如果groupType不等於 news 或者 medicalKnowledge 才往下判斷設定狀態
			if (!article.getGroupType().equals(SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getGroup()) && !article
					.getGroupType().equals(SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getGroup())) {

				// 判斷最新消息有無同步刪除需求,有則執行下列
				Setting asyncNewsSetting = settingMapper
						.selectById(SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getValue());
				if (asyncNewsSetting.getStatus().equals(EnabledStatusEnum.ENABLED.getCode())) {
					// 組裝查詢條件,如果有最新消息的同步需求,則要找asyncId 和 本文章articleId一致
					// 且 group 為news的資料,讓資料同步更新
					LambdaQueryWrapper<Article> asyncQueryWrapper = new LambdaQueryWrapper<>();
					asyncQueryWrapper.eq(Article::getGroupType, SettingEnum.EDUCATION_SURGERY_ASYNC_NEWS.getGroup())
							.eq(Article::getAsyncId, article.getArticleId());

					// 查詢舊的資料
					Article originalAsyncNewsArticle = baseMapper.selectOne(asyncQueryWrapper);

					// 清除縮圖
					String coverThumbnailUrl = originalAsyncNewsArticle.getCoverThumbnailUrl();
					// 因為縮圖圖檔URL有包含 scuro, 這邊先進行截斷
					String result = coverThumbnailUrl.substring(coverThumbnailUrl.indexOf("/", 1));

					// 如果縮圖不為預設值，縮圖進行刪除
					if (!coverThumbnailUrl.equals(DEFAULT_IMAGE_PATH)) {
						minioUtil.removeObject(minioBucketName, result);
					}

					// 清除內容圖片
					cmsService.cleanNotUsedImg(originalAsyncNewsArticle.getContent(), minioBucketName);

					baseMapper.deleteById(originalAsyncNewsArticle.getArticleId());

				}

				// 判斷醫療新知有無刪除同步需求,有則執行下列
				Setting asyncEducationSurgerySetting = settingMapper
						.selectById(SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getValue());
				if (asyncEducationSurgerySetting.getStatus().equals(EnabledStatusEnum.ENABLED.getCode())) {

					// 組裝查詢條件,如果有最新消息的同步需求,則要找asyncId 和 本文章articleId一致
					// 且 group 為medicalKnowledge的資料,讓資料同步更新
					LambdaQueryWrapper<Article> asyncQueryWrapper = new LambdaQueryWrapper<>();
					asyncQueryWrapper
							.eq(Article::getGroupType, SettingEnum.EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE.getGroup())
							.eq(Article::getAsyncId, article.getArticleId());

					// 查詢舊的資料
					Article originalAsyncKnowledgeArticle = baseMapper.selectOne(asyncQueryWrapper);

					// 清除縮圖
					String coverThumbnailUrl = originalAsyncKnowledgeArticle.getCoverThumbnailUrl();
					// 因為縮圖圖檔URL有包含 scuro, 這邊先進行截斷
					String result = coverThumbnailUrl.substring(coverThumbnailUrl.indexOf("/", 1));

					// 如果縮圖不為預設值，縮圖進行刪除
					if (!coverThumbnailUrl.equals(DEFAULT_IMAGE_PATH)) {
						minioUtil.removeObject(minioBucketName, result);
					}

					// 清除內容圖片
					cmsService.cleanNotUsedImg(originalAsyncKnowledgeArticle.getContent(), minioBucketName);

					// 清除資料
					baseMapper.deleteById(originalAsyncKnowledgeArticle.getArticleId());

				}
			}

			// 刪除自身資料
			// 如果縮圖不為預設值,圖片才進行刪除
			if (!article.getCoverThumbnailUrl().equals(DEFAULT_IMAGE_PATH)) {
				List<String> list = new ArrayList<>();
				list.add(article.getCoverThumbnailUrl());
				List<String> paths = minioUtil.extractPaths(minioBucketName, list);
				minioUtil.removeObjects(minioBucketName, paths);
			}

			// 刪除資料前,刪除對應的內容圖片檔案
			cmsService.cleanNotUsedImg(article.getContent(), minioBucketName);

			// 刪除資料
			baseMapper.deleteById(article.getArticleId());
		}

	}

	@Override
	public void deleteArticle(List<Long> articleIdList) {
		// TODO Auto-generated method stub
		// 遍歷循環刪除
		for (Long articleId : articleIdList) {
			// 去執行單個刪除
			deleteArticle(articleId);

		}

	}



}
