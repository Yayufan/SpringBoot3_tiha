package tw.org.organ.pojo.entity;

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
 * 使用者表
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Getter
@Setter
@TableName("user")
@Schema(name = "User", description = "使用者表")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "使用者ID")
	@TableId("user_id")
	private Long userId;

	@Schema(description = "使用者姓名")
	@TableField("name")
	private String name;

	@Schema(description = "使用者信箱(帳號)")
	@TableField("email")
	private String email;

	@Schema(description = "密碼")
	@TableField("password")
	private String password;

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
