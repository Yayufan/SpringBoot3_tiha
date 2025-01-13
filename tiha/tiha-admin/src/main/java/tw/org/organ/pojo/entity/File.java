package tw.org.organ.pojo.entity;

import java.io.Serializable;
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

/**
 * <p>
 * 上傳檔案表，用於應用在只有上傳檔案的頁面
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Getter
@Setter
@TableName("file")
@Schema(name = "File", description = "上傳檔案表，用於應用在只有上傳檔案的頁面")
public class File implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "檔案表，主鍵ID")
	@TableId("file_id")
	private Long fileId;

	@Schema(description = "群組類型，用於分別是屬於哪個頁面的檔案")
	@TableField("group_type")
	private String groupType;

	@Schema(description = "二級類別,如果群組類別底下還有細分類別,可以用這個")
	@TableField("type")
	private String type;

	@Schema(description = "檔名")
	@TableField("name")
	private String name;

	@Schema(description = "檔案描述")
	@TableField("description")
	private String description;

	@Schema(description = "排序值")
	@TableField("sort")
	private Integer sort;

	@Schema(description = "儲存地址")
	@TableField("path")
	private String path;

	@Schema(description = "創建時間")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createTime;

	@Schema(description = "創建者")
	@TableField(value = "create_by", fill = FieldFill.INSERT)
	private String createBy;

	@Schema(description = "最後更新時間")
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime updateTime;

	@Schema(description = "最後更新者")
	@TableField(value = "update_by", fill = FieldFill.UPDATE)
	private String updateBy;

	@Schema(description = "邏輯刪除(0為存在,1為刪除)")
	@TableField("is_deleted")
	@TableLogic
	private String isDeleted;
}
