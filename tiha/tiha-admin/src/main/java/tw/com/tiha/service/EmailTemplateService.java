package tw.com.tiha.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.com.tiha.pojo.DTO.InsertEmailTemplateDTO;
import tw.com.tiha.pojo.DTO.UpdateEmailTemplateDTO;
import tw.com.tiha.pojo.entity.EmailTemplate;

/**
 * <p>
 * 信件模板表 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-01-16
 */
public interface EmailTemplateService extends IService<EmailTemplate> {
	/**
	 * 獲取全部信件模板
	 * 
	 * @return
	 */
	List<EmailTemplate> getAllEmailTemplate();

	/**
	 * 獲取全部信件模板(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<EmailTemplate> getAllEmailTemplate(Page<EmailTemplate> page);

	/**
	 * 獲取單一信件模板
	 * 
	 * @param emailTemplateId
	 * @return
	 */
	EmailTemplate getEmailTemplate(Long emailTemplateId);

	/**
	 * 新增信件模板
	 * 
	 * @param insertEmailTemplateDTO
	 */
	Long insertEmailTemplate(InsertEmailTemplateDTO insertEmailTemplateDTO);

	/**
	 * 更新信件模板
	 * 
	 * @param updateEmailTemplateDTO
	 */
	void updateEmailTemplate(UpdateEmailTemplateDTO updateEmailTemplateDTO);

	/**
	 * 根據EmailTemplateId刪除信件模板
	 * 
	 * @param emailTemplateId
	 */
	void deleteEmailTemplate(Long emailTemplateId);

	/**
	 * 批量刪除信件模板
	 * 
	 * @param emailTemplateIdList
	 */
	void deleteEmailTemplate(List<Long> emailTemplateIdList);

}