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
 * 系統設定表
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Getter
@Setter
@TableName("setting")
@Schema(name = "Setting", description = "系統設定表")
public class Setting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "系統設定ID")
	@TableId("setting_id")
	private Long settingId;

	@Schema(description = "功能設定簡介")
	@TableField("function_introduction")
	private String functionIntroduction;

	@Schema(description = "功能詳細介紹")
	@TableField("remark")
	private String remark;

	@Schema(description = "功能設定啟用狀態,0為禁用,1為啟用")
	@TableField("status")
	private String status;
	
	@Schema(description = "網站瀏覽人次計數")
	@TableField("view_count")
	private Integer viewCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "創建時間")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@Schema(description = "創建者")
	@TableField(value = "create_by", fill = FieldFill.INSERT)
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "最後更新時間")
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@Schema(description = "最後更新者")
	@TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	@Schema(description = "邏輯刪除(0為存在,1為刪除)")
	@TableField("is_deleted")
	@TableLogic
	private String isDeleted;

}
