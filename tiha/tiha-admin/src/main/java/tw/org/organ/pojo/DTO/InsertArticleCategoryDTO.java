package tw.org.organ.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InsertArticleCategoryDTO {

	@Schema(description = "文章類別 ID")
	private Long articleCategoryId;

	@NotBlank
	@Schema(description = "文章組別")
	private String groupType;
	
	@NotNull
	@Schema(description = "父類別ID")
	private Long parentId;

	@NotBlank
	@Schema(description = "專業醫療類別名稱")
	private String name;
	
	@NotBlank
	@Schema(description = "用於描述這個類別主要放的是什麼")
	private String description;
	
}
