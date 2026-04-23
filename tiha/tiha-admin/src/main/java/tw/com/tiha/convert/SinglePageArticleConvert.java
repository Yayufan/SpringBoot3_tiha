package  tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertSinglePageArticleDTO;
import tw.com.tiha.pojo.DTO.UpdateSinglePageArticleDTO;
import tw.com.tiha.pojo.entity.SinglePageArticle;


@Mapper(componentModel = "spring")
public interface SinglePageArticleConvert {

	SinglePageArticle insertDTOToEntity(InsertSinglePageArticleDTO insertSinglePageArticleDTO );

	SinglePageArticle updateDTOToEntity(UpdateSinglePageArticleDTO updateSinglePageArticleDTO );
	
}
