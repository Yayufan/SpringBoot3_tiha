package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertUserDTO;
import tw.org.organ.pojo.DTO.UpdateUserDTO;
import tw.org.organ.pojo.entity.User;

@Mapper(componentModel = "spring")
public interface UserConvert {

	User insertDTOToEntity(InsertUserDTO insertUserDTO);
	
	User updateDTOToEntity(UpdateUserDTO updateUserDTO);
	
}
