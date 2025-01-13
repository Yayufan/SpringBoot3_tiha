package tw.org.organ.pojo.VO;

import cn.dev33.satoken.stp.SaTokenInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class MemberVO {

	@Schema(description = "會員id")
	private Long memberId;

	@Schema(description = "社交帳號登入提供商")
	private String provider;

	@Schema(description = "社交帳號使用者ID")
	private String providerUserId;

	@Schema(description = "姓名")
	private String name;

	@Schema(description = "信箱")
	private String email;

	@Schema(description = "密碼")
	private String password;

	@Schema(description = "院所(部門)")
	private String department;

	@Schema(description = "職稱")
	private String jobTitle;
	
	@Schema(description = "電話號碼")
	private String phone;
	
	@Schema(description = "狀態,0為待審核,1為審核通過,2為審核不通過")
	private String status;
	
	@Schema(description = "token資訊")
	private SaTokenInfo token;
	
	
}
