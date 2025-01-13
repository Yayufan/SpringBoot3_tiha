package tw.org.organ.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertMemberDTO {

	@NotBlank
	@Schema(description = "姓名")
	private String name;

	@NotBlank
	@Schema(description = "信箱")
	private String email;
	
	@NotBlank
	@Schema(description = "密碼")
	private String password;

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
