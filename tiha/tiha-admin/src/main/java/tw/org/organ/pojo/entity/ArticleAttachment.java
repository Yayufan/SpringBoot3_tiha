package tw.org.organ.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文章的附件
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Getter
@Setter
@TableName("article_attachment")
@Schema(name = "ArticleAttachment", description = "文章的附件")
public class ArticleAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文章附件主鍵ID")
    @TableId("article_attachment_id")
    private Long articleAttachmentId;

    @Schema(description = "文章ID")
    @TableField("article_id")
    private Long articleId;

    @Schema(description = "檔名")
    @TableField("name")
    private String name;

    @Schema(description = "檔案類型")
    @TableField("type")
    private String type;

    @Schema(description = "儲存地址")
    @TableField("path")
    private String path;

    @Schema(description = "創建時間")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "創建者")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "最後更新時間")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "最後更新者")
    @TableField("update_by")
    private String updateBy;

    @Schema(description = "邏輯刪除(0為存在,1為刪除)")
    @TableField("is_deleted")
    @TableLogic
    private String isDeleted;
}
