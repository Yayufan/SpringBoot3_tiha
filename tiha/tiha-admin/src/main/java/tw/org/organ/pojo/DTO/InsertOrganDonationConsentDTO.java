package tw.org.organ.pojo.DTO;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InsertOrganDonationConsentDTO {

	@NotBlank
	@Size(max = 255, message = "簽署者姓名過長")
	@Schema(description = "簽署者姓名")
	private String name;

	@NotBlank
	@Size(max = 255, message = "身分證字號過長")
	@Schema(description = "身分證字號")
	private String idCard;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "生日")
	private LocalDate birthday;
	
    
	@NotBlank
    @Schema(description = "性別,1為男 2為女")
    private String gender;

	@NotBlank
	@Size(max = 255, message = "連絡電話過長")
	@Schema(description = "連絡電話")
	private String contactNumber;

	@NotBlank
	@Size(max = 255, message = "手機電話過長")
	@Schema(description = "手機電話")
	private String phoneNumber;

	@Size(max = 255, message = "E-Mail過長")
	@Schema(description = "E-Mail")
	private String email;

	@NotBlank
	@Size(max = 255, message = "聯絡地址過長")
	@Schema(description = "聯絡地址")
	private String address;

	@NotBlank
	@Size(max = 255, message = "法定代理人姓名過長")
	@Schema(description = "法定代理人姓名")
	private String legalRepresentativeName;

	@NotBlank
	@Size(max = 255, message = "法定代理人身份證字號過長")
	@Schema(description = "法定代理人身份證字號")
	private String legalRepresentativeIdCard;

	@NotBlank
	@Schema(description = "是否需要簽署同意卡 -1 為不要, 1為要")
	private String consentCard;

	@Schema(description = "簽署的原因")
	private String reason;

	@Schema(description = "給家屬的話")
	private String wordToFamily;

	@NotEmpty
	@Schema(description = "要捐贈的器官列表")
	private List<String> donateOrgans;
	
	@NotBlank
	@Schema(description = "驗證碼key")
	private String verificationKey;
	
	@NotBlank
	@Schema(description = "用戶輸入的驗證碼")
	private String verificationCode;

}
