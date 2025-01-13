package tw.org.organ.pojo.excelPojo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Data
public class OrganDonationConsentExcel {

	@ExcelProperty("簽署者姓名")
    private String name;

	@ExcelProperty("身分證字號")
    private String idCard;

	@ExcelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
	
	@ExcelProperty("性別")
	private String gender;

	@ExcelProperty("連絡電話")
    private String contactNumber;

	@ExcelProperty("手機電話")
    private String phoneNumber;

	@ExcelProperty("E-Mail")
    private String email;

	@ExcelProperty("聯絡地址")
    private String address;

	@ExcelProperty("法定代理人姓名")
    private String legalRepresentativeName;

	@ExcelProperty("法定代理人身份證字號")
    private String legalRepresentativeIdCard;
	
	@ExcelProperty("是否需要簽屬同意卡")
    private String consentCard;

    @ExcelProperty("簽署同意卡_卡號")
    private String consentCardNumber;

    @ExcelProperty("簽署的原因")
    private String reason;

    @ExcelProperty("給家屬的話")
    private String wordToFamily;

    @ExcelProperty("要捐贈的器官列表")
    private String donateOrgans;

    @ExcelProperty("用來備註進行過的操作")
    private String remark;

    @ExcelProperty("健保卡加註")
    private String healthInsuranceCardAnnotation;

    @ExcelProperty("健保卡加註日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate healthInsuranceCardAnnotationDate;

    @ExcelProperty("簽署日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate signatureDate;

    @ExcelProperty("審核狀態")
    private String status;

    @ExcelProperty("創建時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ExcelProperty("最後更新時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ExcelProperty("最後更新者")
    private String updateBy;

	
}
