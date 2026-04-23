package tw.com.tiha.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InsertSinglePageArticleDTO {

    @Schema(description = "對應頁面")
    private String path;

    @Schema(description = "文章標題")
    private String title;

    @Schema(description = "HTML文章內容")
    private String content;

    @Schema(description = "瀏覽數")
    private Integer views;
	
}
