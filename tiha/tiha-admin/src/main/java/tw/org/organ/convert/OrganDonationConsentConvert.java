package tw.org.organ.convert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.google.common.base.Joiner;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.pojo.excelPojo.OrganDonationConsentExcel;

@Mapper(componentModel = "spring")
public interface OrganDonationConsentConvert {

	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "listToString")
	OrganDonationConsent insertDTOToEntity(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO);

	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "listToString")
	OrganDonationConsent updateDTOToEntity(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO);

	@Mapping(target = "consentCard", source = "consentCard", qualifiedByName = "convertConsentCard")
	@Mapping(target = "status", source = "status", qualifiedByName = "convertStatus")
	@Mapping(target = "gender", source = "gender", qualifiedByName = "convertGender")
	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "convertDonateOrgans")
	OrganDonationConsentExcel entityToExcel(OrganDonationConsent organDonationConsent);

	@Named("listToString")
	default String listToString(List<String> donateOrgans) {
		return Joiner.on(",").skipNulls().join(donateOrgans);
	}

	@Named("convertStatus")
	default String convertStatus(String status) {
		switch (status) {
		case "1":
			return "審核通過";
		case "0":
			return "未審核";
		case "-1":
			return "廢除簽署";
		default:
			return "未知";
		}
	}

	@Named("convertConsentCard")
	default String convertConsentCard(String consentCard) {
		switch (consentCard) {
		case "1":
			return "需要";
		case "-1":
			return "不需要";
		default:
			return "";
		}
	}

	@Named("convertGender")
	default String convertGender(String gender) {
		if (gender == null) {
			return ""; // 如果 gender 为 null，返回空字符串
		}
		switch (gender) {
		case "1":
			return "男";
		case "2":
			return "女";
		default:
			return "";
		}
	}

	
	// @formatter:off
	@Named("convertDonateOrgans")
	default String convertDonateOrgans(String donateOrgans) {
		// 使用 Stream.of 創建不可變的 organTranslations 映射表,這樣就製作一個Map映射表了
		Map<String, String> organTranslations = Stream
				.of(new String[][] { 
					{ "all", "全部捐贈" },
					{ "lung", "肺臟" },
					{ "pancreas", "胰臟" },
					{ "smallIntestine", "小腸" }, 
					{ "skin", "皮膚" },
					{ "heartValve", "心瓣膜" },
					{ "heart", "心臟" },
					{ "liver", "肝臟" },
					{ "kidney", "腎臟" },
					{ "cornea", "眼角膜" },	
					{ "bones", "骨骼" },
					{ "bloodVessels", "血管" } 
					}
				).collect(Collectors.toMap(data -> data[0], data -> data[1]));

		// 如果输入为空或为空字符串，直接返回空字符串
		if (donateOrgans == null || donateOrgans.isBlank()) {
			return "";
		}

		// 处理逗号分隔的字符串
		return Stream.of(donateOrgans.split(",")) // 按逗号拆分字符串
				.map(String::trim) // 去掉空格
				.map(organTranslations::get) // 转换为中文
				.filter(value -> value != null) // 排除未匹配项
				.collect(Collectors.joining(",")); // 用逗号连接
	}
	
	
	// @formatter:on

}
