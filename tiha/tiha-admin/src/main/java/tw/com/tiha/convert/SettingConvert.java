package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.UpdateSettingDTO;
import tw.com.tiha.pojo.entity.Setting;

@Mapper(componentModel = "spring")
public interface SettingConvert {

	Setting updateDTOToEntity(UpdateSettingDTO updateSettingDTO);
	
}
