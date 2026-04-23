package tw.com.tiha.pojo.DTO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSinglePageArticleDTO {

	@Schema(description = "主鍵ID")
    private Long singlePageArticleId;

    @Schema(description = "對應頁面")
    private String path;

    @Schema(description = "文章標題")
    private String title;

    @Schema(description = "HTML文章內容")
    private String content;

    @Schema(description = "瀏覽數")
    private Integer views;
    
	@NotNull
	@Schema(description = "暫時上傳圖片的URL")
	private List<String> tempUploadUrl;
	
}
