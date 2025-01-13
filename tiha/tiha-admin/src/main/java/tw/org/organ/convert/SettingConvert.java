package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.UpdateSettingDTO;
import tw.org.organ.pojo.entity.Setting;

@Mapper(componentModel = "spring")
public interface SettingConvert {

	Setting updateDTOToEntity(UpdateSettingDTO updateSettingDTO);
	
}
