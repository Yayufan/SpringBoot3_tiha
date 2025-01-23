package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertTagDTO;
import tw.com.tiha.pojo.DTO.UpdateTagDTO;
import tw.com.tiha.pojo.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagConvert {

	Tag insertDTOToEntity(InsertTagDTO insertTagDTO);
	
	Tag updateDTOToEntity(UpdateTagDTO updateTagDTO);
	
}
