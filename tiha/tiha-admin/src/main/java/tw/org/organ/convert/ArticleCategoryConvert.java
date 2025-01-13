package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertArticleCategoryDTO;
import tw.org.organ.pojo.DTO.UpdateArticleCategoryDTO;
import tw.org.organ.pojo.entity.ArticleCategory;

@Mapper(componentModel = "spring")
public interface ArticleCategoryConvert {

	ArticleCategory insertDTOToEntity(InsertArticleCategoryDTO insertArticleCategoryDTO);

	ArticleCategory updateDTOToEntity(UpdateArticleCategoryDTO updateArticleCategoryDTO);

}
