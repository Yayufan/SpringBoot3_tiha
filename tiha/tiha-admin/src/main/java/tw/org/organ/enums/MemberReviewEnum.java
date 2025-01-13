package tw.org.organ.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum MemberReviewEnum {

	PENDING("0", "待審核"), APPROVED("1", "審核通過"), FAILED("2", "審核不通過");

	private final String code;
	private final String description;

	// 根據代碼獲取對應的枚舉常量
	public static MemberReviewEnum fromCode(String code) {
		return Arrays.stream(MemberReviewEnum.values()).filter(status -> status.code.equals(code)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("無效的狀態代碼: " + code));
	}

}
