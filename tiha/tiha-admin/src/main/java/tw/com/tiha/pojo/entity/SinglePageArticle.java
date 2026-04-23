package tw.com.tiha.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 單頁文章，用來設計簡單的單頁內容
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Getter
@Setter
@TableName("single_page_article")
@Schema(name = "SinglePageArticle", description = "單頁文章，用來設計簡單的單頁內容")
public class SinglePageArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主鍵ID")
    @TableId("single_page_article_id")
    private Long singlePageArticleId;

    @Schema(description = "對應頁面")
    @TableField("path")
    private String path;

    @Schema(description = "文章標題")
    @TableField("title")
    private String title;

    @Schema(description = "HTML文章內容")
    @TableField("content")
    private String content;

    @Schema(description = "瀏覽數")
    @TableField("views")
    private Integer views;

    @Schema(description = "創建時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "創建者")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "最後更新時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "最後更新者")
    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "邏輯刪除(0為存在,1為刪除)")
    @TableField("is_deleted")
    @TableLogic
    private String isDeleted;
}
