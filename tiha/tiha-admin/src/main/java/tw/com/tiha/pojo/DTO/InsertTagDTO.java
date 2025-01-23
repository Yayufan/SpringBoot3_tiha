package tw.com.tiha.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertTagDTO {

	@Schema(description = "tag的分類,用於之後擴充member表有對應的標籤可用, 擴增表也有對應的標籤可用")
	private String type;

	@NotBlank
	@Schema(description = "標籤名稱,用於顯示")
	private String name;

	@Schema(description = "標籤的描述")
	private String description;

	@Schema(description = "標籤的狀態, 0為啟用  1為禁用")
	private Byte status;

	@Schema(description = "標籤的顯示顏色")
	private String color;

}
