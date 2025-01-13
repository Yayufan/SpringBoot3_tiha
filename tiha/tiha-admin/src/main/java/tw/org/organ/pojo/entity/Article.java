package tw.org.organ.pojo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 文章表 - 各個group的文章都儲存在這
 * </p>
 *
 * @author Joey
 * @since 2024-09-23
 */
@Getter
@Setter
@ToString
@TableName("article")
@Schema(name = "Article", description = "文章表 - 各個group的文章都儲存在這")
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "文章ID")
	@TableId("article_id")
	private Long articleId;

	@Schema(description = "群組 - 用於分類文章的群組")
	@TableField("group_type")
	private String groupType;

	@Schema(description = "同步ID")
	@TableField("async_id")
	private Long asyncId;

	@Schema(description = "類別ID")
	@TableField("category_id")
	private Long categoryId;

	@Schema(description = "文章的類型")
	@TableField("type")
	private String type;

	@Schema(description = "文章標題")
	@TableField("title")
	private String title;

	@Schema(description = "對文章的描述")
	@TableField("description")
	private String description;

	@Schema(description = "公告此文章的日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("announcement_date")
	private LocalDate announcementDate;

	@Schema(description = "HTML文章內容")
	@TableField("content")
	private String content;

	@Schema(description = "封面縮圖URL")
	@TableField("cover_thumbnail_url")
	private String coverThumbnailUrl;

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
	@TableField(value = "update_by", fill = FieldFill.UPDATE)
	private String updateBy;

	@Schema(description = "邏輯刪除(0為存在,1為刪除)")
	@TableField("is_deleted")
	@TableLogic
	private String isDeleted;
}
