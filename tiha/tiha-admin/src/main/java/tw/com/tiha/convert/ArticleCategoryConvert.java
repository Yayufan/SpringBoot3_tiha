package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertArticleCategoryDTO;
import tw.com.tiha.pojo.DTO.UpdateArticleCategoryDTO;
import tw.com.tiha.pojo.entity.ArticleCategory;

@Mapper(componentModel = "spring")
public interface ArticleCategoryConvert {

	ArticleCategory insertDTOToEntity(InsertArticleCategoryDTO insertArticleCategoryDTO);

	ArticleCategory updateDTOToEntity(UpdateArticleCategoryDTO updateArticleCategoryDTO);

}
