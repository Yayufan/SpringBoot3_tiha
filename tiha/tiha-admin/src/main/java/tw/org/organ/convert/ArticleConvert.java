package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertArticleDTO;
import tw.org.organ.pojo.DTO.UpdateArticleDTO;
import tw.org.organ.pojo.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleConvert {

	Article insertDTOToEntity(InsertArticleDTO insertArticleDTO);

	Article updateDTOToEntity(UpdateArticleDTO updateArticleDTO);
	
	Article copyEntity(Article article);
	
}
