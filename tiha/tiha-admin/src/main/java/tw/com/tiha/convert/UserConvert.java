package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertUserDTO;
import tw.com.tiha.pojo.DTO.UpdateUserDTO;
import tw.com.tiha.pojo.entity.User;

@Mapper(componentModel = "spring")
public interface UserConvert {

	User insertDTOToEntity(InsertUserDTO insertUserDTO);
	
	User updateDTOToEntity(UpdateUserDTO updateUserDTO);
	
}
