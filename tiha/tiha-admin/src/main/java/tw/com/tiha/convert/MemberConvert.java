package tw.com.tiha.convert;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import tw.com.tiha.pojo.DTO.InsertMemberDTO;
import tw.com.tiha.pojo.DTO.MemberLoginInfo;
import tw.com.tiha.pojo.DTO.ProviderRegisterDTO;
import tw.com.tiha.pojo.DTO.UpdateMemberDTO;
import tw.com.tiha.pojo.VO.MemberTagVO;
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

	MemberTagVO entityToMemberTagVO(Member member);

	@Mapping(target = "birthday", source = "birthday", qualifiedByName = "convertToROCDate")
	@Mapping(target = "status", source = "status", qualifiedByName = "convertStatus")
	@Mapping(target = "code", source = "code", qualifiedByName = "convertCode")
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

	@Named("convertToROCDate")
	default String convertToMinguoDate(LocalDate birthday) {
		if (birthday == null) {
			return null;
		}

		// 轉換為民國年
		int minguoYear = birthday.getYear() - 1911;

		// 格式化為民國年日期
		return String.format("%d-%02d-%02d", minguoYear, birthday.getMonthValue(), birthday.getDayOfMonth());

	}

	@Named("convertCode")
	default String convertCode(Integer code) {
		if (code == null) {
			return null;
		}

		// 轉換成HA0001這種格式
		String formatCode = String.format("HA%04d", code);
		return formatCode;

	}

}
