package tw.com.tiha.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.com.tiha.pojo.DTO.InsertSinglePageArticleAttachmentDTO;
import tw.com.tiha.pojo.entity.SinglePageArticleAttachment;

/**
 * <p>
 * 單頁文章附件表，儲存單頁內容的附件 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
public interface SinglePageArticleAttachmentService extends IService<SinglePageArticleAttachment> {

	/**
	 * 根據單頁文章ID,獲取該文章的所有附件
	 * 
	 * @param singlePageArticleId
	 * @return
	 */
	List<SinglePageArticleAttachment> getAllSinglePageArticleAttachmentByArticleId(Long singlePageArticleId);

	/**
	 * 根據單頁文章ID,獲取該文章的所有附件(分頁)
	 * 
	 * @param singlePageArticleId
	 * @param page
	 * @return
	 */
	IPage<SinglePageArticleAttachment> getAllSinglePageArticleAttachmentByArticleId(Long singlePageArticleId,
			Page<SinglePageArticleAttachment> page);

	/**
	 * 新增單頁文章附件
	 * 
	 * @param insertSinglePageArticleAttachmentDTO
	 * @param files
	 */
	void insertSinglePageArticleAttachment(InsertSinglePageArticleAttachmentDTO insertSinglePageArticleAttachmentDTO,
			MultipartFile[] files);

	/**
	 * 根據singlePageArticleAttachmentId刪除文章附件
	 * 
	 * @param singlePageArticleAttachmentId
	 */
	void deleteSinglePageArticleAttachment(Long singlePageArticleAttachmentId);

}
