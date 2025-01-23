package tw.com.tiha.pojo.DTO;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMemberDTO {

	@NotNull
	private Long memberId;

	@NotBlank
	@Schema(description = "姓名")
	private String name;

	@NotBlank
	@Schema(description = "信箱")
	private String email;


	@Schema(description = "院所(部門)")
	private String department;


	@Schema(description = "職稱")
	private String jobTitle;

	@NotBlank
	@Schema(description = "電話號碼")
	private String phone;
	
	@Schema(description = "性別")
	private String gender;

	@Schema(description = "性別補充")
	private String genderOther;

	@Schema(description = "國民身分證字號/居留證")
	private String idCard;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "出生年月日")
	private LocalDate birthday;
	
	@Schema(description = "聯絡地址")
	private String contactAddress;

	@Schema(description = "狀態")
	private String status;
	
	
}
