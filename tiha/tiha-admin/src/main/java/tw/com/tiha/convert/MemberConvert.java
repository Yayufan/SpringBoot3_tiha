package tw.com.tiha.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import tw.com.tiha.pojo.DTO.InsertMemberDTO;
import tw.com.tiha.pojo.DTO.MemberLoginInfo;
import tw.com.tiha.pojo.DTO.ProviderRegisterDTO;
import tw.com.tiha.pojo.DTO.UpdateMemberDTO;
import tw.com.tiha.pojo.VO.MemberVO;
import tw.com.tiha.pojo.entity.Member;
import tw.com.tiha.pojo.excelPojo.MemberExcel;

@Mapper(componentModel = "spring")
public interface MemberConvert {

	Member loginInfoToEntity(MemberLoginInfo memberLoginInfo);
	
	Member insertDTOToEntity(InsertMemberDTO insertMemberDTO);

	Member updateDTOToEntity(UpdateMemberDTO updateMemberDTO);
	
	Member providerRegisterDTO(ProviderRegisterDTO providerRegisterDTO);
	
	MemberVO entityToVO(Member member);
	
	@Mapping(target = "status", source = "status", qualifiedByName = "convertStatus")
	MemberExcel entityToExcel(Member member);
	
	@Named("convertStatus")
	default String convertStatus(String status) {
		switch (status) {
		case "1":
			return "審核通過";
		case "0":
			return "未審核";
		case "-1":
			return "申請駁回";
		default:
			return "未知";
		}
	}

	
}
