package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertArticleAttachmentDTO;
import tw.com.tiha.pojo.entity.ArticleAttachment;

@Mapper(componentModel = "spring")
public interface ArticleAttachmentConvert {
	ArticleAttachment insertDTOToEntity(InsertArticleAttachmentDTO insertArticleAttachmentDTO);
}
