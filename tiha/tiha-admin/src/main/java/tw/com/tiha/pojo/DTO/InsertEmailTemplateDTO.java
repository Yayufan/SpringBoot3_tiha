package tw.com.tiha.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertEmailTemplateDTO {
	
	@NotBlank
    @Schema(description = "信件模板名稱")
    private String name;
    
    @Schema(description = "信件模板描述")
    private String description;
}
