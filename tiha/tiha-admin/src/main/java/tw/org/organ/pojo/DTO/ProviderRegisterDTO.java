package tw.org.organ.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProviderRegisterDTO {

	@NotNull
	private Long memberId;

	@NotBlank
	@Schema(description = "社交帳號登入提供商")
	private String provider;

	@NotBlank
	@Schema(description = "社交帳號使用者ID")
	private String providerUserId;

	@NotBlank
	@Schema(description = "姓名")
	private String name;

	@NotBlank
	@Schema(description = "信箱")
	private String email;

	@NotBlank
	@Schema(description = "院所(部門)")
	private String department;

	@NotBlank
	@Schema(description = "職稱")
	private String jobTitle;

	@NotBlank
	@Schema(description = "電話號碼")
	private String phone;

}
