package tw.org.organ.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum SettingEnum {

	EDUCATION_SURGERY_ASYNC_NEWS(1L,"手術衛教文章，同步至最新消息","news"),
	EDUCATION_SURGERY_ASYNC_MEDICAL_KNOWLEDGE(2L,"手術衛教文章，同步至醫療新知","medicalKnowledge"),
	PROFESSIONAL_MEDICAL_ASYNC_NEWS(3L, "專業醫療文章，同步至最新消息","news"),
	PROFESSIONAL_MEDICAL_ASYNC_MEDICAL_KNOWLEDGE(4L, "專業醫療文章，同步至醫療新知","medicalKnowledge");

	private final Long value;
	private final String description;
	private final String group;

	// 根據代碼獲取對應的枚舉常量
	public static SettingEnum fromValue(Long value) {
		return Arrays.stream(SettingEnum.values()).filter(setting -> setting.value.equals(value)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("無效的狀態代碼: " + value));
	}

}
