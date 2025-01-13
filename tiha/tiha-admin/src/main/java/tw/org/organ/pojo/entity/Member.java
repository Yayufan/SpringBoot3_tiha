package tw.org.organ.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * 會員表
 * </p>
 *
 * @author Joey
 * @since 2024-09-12
 */
@ToString
@Getter
@Setter
@TableName("member")
@Schema(name = "Member", description = "會員表")
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId("member_id")
	private Long memberId;

	@Schema(description = "社交帳號登入提供商")
	@TableField("provider")
	private String provider;

	@Schema(description = "社交帳號使用者ID")
	@TableField("provider_user_id")
	private String providerUserId;

	@Schema(description = "姓名")
	@TableField("name")
	private String name;

	@Schema(description = "信箱")
	@TableField("email")
	private String email;

	@Schema(description = "密碼")
	@TableField("password")
	private String password;

	@Schema(description = "院所(部門)")
	@TableField("department")
	private String department;

	@Schema(description = "職稱")
	@TableField("job_title")
	private String jobTitle;
	
	@Schema(description = "電話號碼")
	@TableField("phone")
	private String phone;
	
	@Schema(description = "狀態,0為待審核,1為審核通過,2為審核不通過")
	@TableField("status")
	private String status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "創建時間")
	@TableField(value = "create_time")
	private LocalDateTime createTime;

	@Schema(description = "創建者")
	@TableField("create_by")
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "最後更新時間")
	@TableField(value = "update_time")
	private LocalDateTime updateTime;

	@Schema(description = "最後更新者")
	@TableField("update_by")
	private String updateBy;

	@Schema(description = "邏輯刪除(0為存在,1為刪除)")
	@TableField("is_deleted")
	@TableLogic
	private String isDeleted;
}
