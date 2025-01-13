package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertClinicHoursDTO;
import tw.com.tiha.pojo.DTO.UpdateClinicHoursDTO;
import tw.com.tiha.pojo.entity.ClinicHours;

@Mapper(componentModel = "spring")
public interface ClinicHoursConvert {

	ClinicHours insertDTOToEntity(InsertClinicHoursDTO insertClinicHoursDTO);

	ClinicHours updateDTOToEntity(UpdateClinicHoursDTO updateClinicHoursDTO);

}
