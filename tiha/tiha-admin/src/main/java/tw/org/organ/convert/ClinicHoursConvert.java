package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertClinicHoursDTO;
import tw.org.organ.pojo.DTO.UpdateClinicHoursDTO;
import tw.org.organ.pojo.entity.ClinicHours;

@Mapper(componentModel = "spring")
public interface ClinicHoursConvert {

	ClinicHours insertDTOToEntity(InsertClinicHoursDTO insertClinicHoursDTO);

	ClinicHours updateDTOToEntity(UpdateClinicHoursDTO updateClinicHoursDTO);

}
