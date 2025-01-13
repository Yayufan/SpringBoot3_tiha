package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertFileDTO;
import tw.org.organ.pojo.entity.File;

@Mapper(componentModel = "spring")
public interface FileConvert {

	File insertDTOToEntity(InsertFileDTO insertFileDTO);
	
}
