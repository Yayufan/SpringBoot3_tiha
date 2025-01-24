package tw.com.tiha.pojo.DTO;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Schema(description = "院所(部門)")
	private String department;

	@Schema(description = "職稱")
	private String jobTitle;

	@Schema(description = "性別")
	@TableField("gender")
	private String gender;

	@Schema(description = "性別補充")
	@TableField("gender_other")
	private String genderOther;

	@Schema(description = "國民身分證字號/居留證")
	@TableField("id_card")
	private String idCard;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "出生年月日")
	@TableField(value = "birthday")
	private LocalDate birthday;
	
	@Schema(description = "聯絡地址")
	@TableField("contact_address")
	private String contactAddress;

	@NotBlank
	@Schema(description = "電話號碼")
	private String phone;
	
	@Schema(description = "會員編號 , 顯示給用戶時要加HA")
	private Integer code;
	
	@NotBlank
	@Schema(description = "驗證碼key")
	private String verificationKey;
	
	@NotBlank
	@Schema(description = "用戶輸入的驗證碼")
	private String verificationCode;

}
