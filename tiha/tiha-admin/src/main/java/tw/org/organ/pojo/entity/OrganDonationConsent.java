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
 * 
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
@Getter
@Setter
@ToString
@TableName("organ_donation_consent")
@Schema(name = "OrganDonationConsent", description = "")
public class OrganDonationConsent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主鍵ID")
    @TableId("organ_donation_consent_id")
    private Long organDonationConsentId;

    @Schema(description = "簽署者姓名")
    @TableField("name")
    private String name;

    @Schema(description = "身分證字號")
    @TableField("id_card")
    private String idCard;

    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("birthday")
    private LocalDate birthday;
    
    @Schema(description = "性別,1為男 2為女")
    @TableField("gender")
    private String gender;

    @Schema(description = "連絡電話")
    @TableField("contact_number")
    private String contactNumber;

    @Schema(description = "手機電話")
    @TableField("phone_number")
    private String phoneNumber;

    @Schema(description = "E-Mail")
    @TableField("email")
    private String email;

    @Schema(description = "聯絡地址")
    @TableField("address")
    private String address;

    @Schema(description = "法定代理人姓名")
    @TableField("legal_representative_name")
    private String legalRepresentativeName;

    @Schema(description = "法定代理人身份證字號")
    @TableField("legal_representative_id_card")
    private String legalRepresentativeIdCard;

    @Schema(description = "是否需要簽署同意卡 -1 為不要, 1為要")
    @TableField("consent_card")
    private String consentCard;

    @Schema(description = "簽署同意卡_卡號, 亦可為null")
    @TableField("consent_card_number")
    private String consentCardNumber;

    @Schema(description = "簽署的原因")
    @TableField("reason")
    private String reason;

    @Schema(description = "給家屬的話")
    @TableField("word_to_family")
    private String wordToFamily;

    @Schema(description = "要捐贈的器官列表")
    @TableField("donate_organs")
    private String donateOrgans;

    @Schema(description = "用來備註進行過的操作")
    @TableField("remark")
    private String remark;

    @Schema(description = "健保卡加註")
    @TableField("health_insurance_card_annotation")
    private String healthInsuranceCardAnnotation;

    @Schema(description = "健保卡加註日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("health_insurance_card_annotation_date")
    private LocalDate healthInsuranceCardAnnotationDate;

    @Schema(description = "簽署日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField("signature_date")
    private LocalDate signatureDate;

    @Schema(description = "審核狀態,簽署撤銷為-1 , 未處理為0, 簽署成功為1")
    @TableField("status")
    private String status;

    @Schema(description = "創建時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "創建者")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "最後更新時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
