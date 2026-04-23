package tw.com.tiha.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.com.tiha.convert.SinglePageArticleAttachmentConvert;
import tw.com.tiha.mapper.SinglePageArticleAttachmentMapper;
import tw.com.tiha.pojo.DTO.InsertSinglePageArticleAttachmentDTO;
import tw.com.tiha.pojo.entity.SinglePageArticleAttachment;
import tw.com.tiha.service.SinglePageArticleAttachmentService;
import tw.com.tiha.utils.MinioUtil;

/**
 * <p>
 * 單頁文章附件表，儲存單頁內容的附件 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Service
@RequiredArgsConstructor
public class SinglePageArticleAttachmentServiceImpl
		extends ServiceImpl<SinglePageArticleAttachmentMapper, SinglePageArticleAttachment>
		implements SinglePageArticleAttachmentService {

	@Value("${minio.bucketName}")
	private String minioBucketName;

	private final SinglePageArticleAttachmentMapper singlePageArticleAttachmentMapper;
	private final SinglePageArticleAttachmentConvert singlePageArticleAttachmentConvert;
	private final MinioUtil minioUtil;
	private final String PATH = "singlePageArticleAttachment";

	@Override
	public List<SinglePageArticleAttachment> getAllSinglePageArticleAttachmentByArticleId(Long singlePageArticleId) {
		LambdaQueryWrapper<SinglePageArticleAttachment> articleAttachmentQueryWrapper = new LambdaQueryWrapper<>();
		articleAttachmentQueryWrapper.eq(SinglePageArticleAttachment::getSinglePageArticleId, singlePageArticleId);
		List<SinglePageArticleAttachment> singlePageArticleAttachmentList = singlePageArticleAttachmentMapper
				.selectList(articleAttachmentQueryWrapper);
		return singlePageArticleAttachmentList;
	}

	@Override
	public IPage<SinglePageArticleAttachment> getAllSinglePageArticleAttachmentByArticleId(Long singlePageArticleId,
			Page<SinglePageArticleAttachment> page) {
		LambdaQueryWrapper<SinglePageArticleAttachment> articleAttachmentQueryWrapper = new LambdaQueryWrapper<>();
		articleAttachmentQueryWrapper.eq(SinglePageArticleAttachment::getSinglePageArticleId, singlePageArticleId);
		Page<SinglePageArticleAttachment> articleAttachmentPage = singlePageArticleAttachmentMapper.selectPage(page,
				articleAttachmentQueryWrapper);
		return articleAttachmentPage;
	}

	@Override
	public void insertSinglePageArticleAttachment(
			InsertSinglePageArticleAttachmentDTO insertSinglePageArticleAttachmentDTO, MultipartFile[] files) {
		// 轉換檔案
		SinglePageArticleAttachment singlePageArticleAttachment = singlePageArticleAttachmentConvert
				.insertDTOToEntity(insertSinglePageArticleAttachmentDTO);

		// 檔案存在，處理檔案
		if (files != null && files.length > 0) {

			List<String> upload = minioUtil.upload(minioBucketName, PATH + "/", files);
			// 基本上只有有一個檔案跟著formData上傳,所以這邊直接寫死,把唯一的url增添進對象中
			String url = upload.get(0);
			// 將bucketName 組裝進url
			url = "/" + minioBucketName + "/" + url;
			// minio完整路徑放路對象中
			singlePageArticleAttachment.setPath(url);

			// 放入資料庫
			baseMapper.insert(singlePageArticleAttachment);

		}

		System.out.println("上傳完成");

	}

	@Override
	public void deleteSinglePageArticleAttachment(Long singlePageArticleAttachmentId) {

		SinglePageArticleAttachment singlePageArticleAttachment = singlePageArticleAttachmentMapper
				.selectById(singlePageArticleAttachmentId);

		String filePath = singlePageArticleAttachment.getPath();
		String result = filePath.substring(filePath.indexOf("/", 1));

		// 透過Minio進行刪除
		minioUtil.removeObject(minioBucketName, result);
		// 資料庫資料刪除
		baseMapper.deleteById(singlePageArticleAttachmentId);

	}

}
