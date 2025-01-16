package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertEmailTemplateDTO;
import tw.com.tiha.pojo.DTO.UpdateEmailTemplateDTO;
import tw.com.tiha.pojo.entity.EmailTemplate;

@Mapper(componentModel = "spring")
public interface EmailTemplateConvert {

	EmailTemplate insertDTOToEntity(InsertEmailTemplateDTO insertArticleDTO);

	EmailTemplate updateDTOToEntity(UpdateEmailTemplateDTO updateArticleDTO);
	
}
