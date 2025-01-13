package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertFileDTO;
import tw.com.tiha.pojo.entity.File;

@Mapper(componentModel = "spring")
public interface FileConvert {

	File insertDTOToEntity(InsertFileDTO insertFileDTO);
	
}
