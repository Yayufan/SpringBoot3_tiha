package tw.org.organ.pojo.DTO;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UpdateArticleDTO {

	@NotNull
	@Schema(description = "文章ID")
	private Long articleId;
	
	@NotBlank
    @Schema(description = "群組")
    private String groupType;
	
	@Schema(description = "同步ID")
	private Long asyncId;
	
	@Schema(description = "類別ID")
	private Long categoryId;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "公告此消息的日期")
	private LocalDate announcementDate;
	
	@Schema(description = "專業醫療文章的類型")
	private String type;

	@NotBlank
	@Schema(description = "文章標題")
	private String title;

	@Schema(description = "文章描述")
	private String description;

	@NotBlank
	@Schema(description = "HTML文章內容")
	private String content;

	@NotNull
	@Schema(description = "暫時上傳圖片的URL")
	private List<String> tempUploadUrl;
	
}
