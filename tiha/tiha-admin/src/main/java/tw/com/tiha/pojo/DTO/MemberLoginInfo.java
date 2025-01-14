package tw.com.tiha.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemberLoginInfo {

	@Schema(description = "社交帳號登入提供商")
	private String provider;

	@Schema(description = "社交帳號使用者ID")
	private String providerUserId;
	
	@Schema(description = "身分證字號")
	private String idCard;

	@Schema(description = "聯絡電話")
	private String phone;
	
	
}
