package tw.com.tiha.service.impl;

import tw.com.tiha.pojo.DTO.InsertEmailTemplateDTO;
import tw.com.tiha.pojo.DTO.UpdateEmailTemplateDTO;
import tw.com.tiha.pojo.entity.EmailTemplate;
import tw.com.tiha.convert.EmailTemplateConvert;
import tw.com.tiha.mapper.EmailTemplateMapper;
import tw.com.tiha.service.EmailTemplateService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 信件模板表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-01-16
 */
@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl extends ServiceImpl<EmailTemplateMapper, EmailTemplate>
		implements EmailTemplateService {

	private final EmailTemplateConvert emailTemplateConvert;

	@Override
	public List<EmailTemplate> getAllEmailTemplate() {
		List<EmailTemplate> emailTemplateList = baseMapper.selectList(null);
		return emailTemplateList;
	}

	@Override
	public IPage<EmailTemplate> getAllEmailTemplate(Page<EmailTemplate> page) {
		Page<EmailTemplate> emailTemplatePage = baseMapper.selectPage(page, null);
		return emailTemplatePage;
	}

	@Override
	public EmailTemplate getEmailTemplate(Long emailTemplateId) {
		EmailTemplate emailTemplate = baseMapper.selectById(emailTemplateId);
		return emailTemplate;
	}

	@Override
	public Long insertEmailTemplate(InsertEmailTemplateDTO insertEmailTemplateDTO) {
		EmailTemplate emailTemplate = emailTemplateConvert.insertDTOToEntity(insertEmailTemplateDTO);
		baseMapper.insert(emailTemplate);
		return emailTemplate.getEmailTemplateId();
	}

	@Override
	public void updateEmailTemplate(UpdateEmailTemplateDTO updateEmailTemplateDTO) {
		EmailTemplate emailTemplate = emailTemplateConvert.updateDTOToEntity(updateEmailTemplateDTO);
		baseMapper.updateById(emailTemplate);
	}

	@Override
	public void deleteEmailTemplate(Long emailTemplateId) {
		// TODO Auto-generated method stub
		baseMapper.deleteById(emailTemplateId);

	}

	@Override
	public void deleteEmailTemplate(List<Long> emailTemplateIdList) {
		// TODO Auto-generated method stub
		for (Long emailTemplateId : emailTemplateIdList) {
			deleteEmailTemplate(emailTemplateId);
		}

	}

}
