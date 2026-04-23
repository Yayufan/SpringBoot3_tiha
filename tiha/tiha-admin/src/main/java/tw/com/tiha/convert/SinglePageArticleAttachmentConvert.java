package  tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertSinglePageArticleAttachmentDTO;
import tw.com.tiha.pojo.entity.SinglePageArticleAttachment;

@Mapper(componentModel = "spring")
public interface SinglePageArticleAttachmentConvert {
	SinglePageArticleAttachment insertDTOToEntity(InsertSinglePageArticleAttachmentDTO insertSinglePageArticleAttachmentDTO);

}
