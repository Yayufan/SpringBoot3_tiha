package tw.com.tiha.pojo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.ibatis.type.JdbcType;

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

	@TableId(value="member_id")
	private Long memberId;

	@Schema(description = "社交帳號登入提供商")
	@TableField(value="provider" )
	private String provider;

	@Schema(description = "社交帳號使用者ID")
	@TableField(value="provider_user_id" )
	private String providerUserId;

	@Schema(description = "姓名")
	@TableField(value="name")
	private String name;

	@Schema(description = "信箱")
	@TableField(value="email")
	private String email;

	@Schema(description = "密碼")
	@TableField(value="password")
	private String password;

	@Schema(description = "院所(部門)")
	@TableField(value="department")
	private String department;

	@Schema(description = "職稱")
	@TableField(value="job_title")
	private String jobTitle;

	@Schema(description = "性別")
	@TableField(value="gender")
	private String gender;

	@Schema(description = "性別補充")
	@TableField(value="gender_other")
	private String genderOther;

	@Schema(description = "國民身分證字號/居留證")
	@TableField(value="id_card")
	private String idCard;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "出生年月日")
	@TableField(value = "birthday")
	private LocalDate birthday;

	@Schema(description = "聯絡地址")
	@TableField(value="contact_address")
	private String contactAddress;

	@Schema(description = "電話號碼")
	@TableField(value="phone")
	private String phone;

	@Schema(description = "狀態,0為待審核,1為審核通過,2為審核不通過")
	@TableField(value="status")
	private String status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "創建時間")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@Schema(description = "創建者")
	@TableField(value = "create_by", fill = FieldFill.INSERT)
	private String createBy;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "最後更新時間")
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	@Schema(description = "最後更新者")
	@TableField(value = "update_by", fill = FieldFill.UPDATE)
	private String updateBy;

	@Schema(description = "邏輯刪除(0為存在,1為刪除)")
//	@TableField(value="is_deleted",jdbcType=JdbcType.VARCHAR)
	@TableField(value="is_deleted")
	@TableLogic
	private String isDeleted;
}
