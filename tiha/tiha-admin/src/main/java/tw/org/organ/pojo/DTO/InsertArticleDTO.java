package tw.org.organ.pojo.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InsertArticleDTO {

	@NotBlank
    @Schema(description = "群組")
    private String groupType;
	
    @Schema(description = "類別ID")
    private Long categoryId;
	
    @Schema(description = "文章類型")
    private String type;
    
	@NotBlank
    @Schema(description = "文章標題")
    private String title;
    
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "公告此消息的日期")
	private LocalDate announcementDate;

    @Schema(description = "文章描述")
    private String description;
	
}
