package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertArticleDTO;
import tw.com.tiha.pojo.DTO.UpdateArticleDTO;
import tw.com.tiha.pojo.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleConvert {

	Article insertDTOToEntity(InsertArticleDTO insertArticleDTO);

	Article updateDTOToEntity(UpdateArticleDTO updateArticleDTO);
	
	Article copyEntity(Article article);
	
}
