package tw.org.organ.pojo.DTO;

import com.esotericsoftware.kryo.util.Null;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateSettingDTO {
	
	@Null
	@Schema(description = "系統設定ID")
	private Long settingId;

	@NotBlank
	@Schema(description = "功能設定簡介")
	private String functionIntroduction;
	
	@NotBlank
	@Schema(description = "詳細介紹")
	private String remark;

	@NotBlank
    @Schema(description = "功能設定啟用狀態,0為禁用,1為啟用")
    private String status;
	
}
