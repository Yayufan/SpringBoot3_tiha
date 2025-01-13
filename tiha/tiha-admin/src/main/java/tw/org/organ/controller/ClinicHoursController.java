package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertClinicHoursDTO;
import tw.org.organ.pojo.DTO.UpdateClinicHoursDTO;
import tw.org.organ.pojo.entity.ClinicHours;
import tw.org.organ.service.ClinicHoursService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 門診時間圖片表 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Tag(name = "門診時間圖片API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/clinic-hours")
public class ClinicHoursController {

	private final ClinicHoursService clinicHoursService;

	@GetMapping("{id}")
	@Operation(summary = "查詢單一門診時間圖片")
	public R<ClinicHours> getClinicHours(@PathVariable Long clinicHoursId) {
		ClinicHours clinicHours = clinicHoursService.getClinicHours(clinicHoursId);
		return R.ok(clinicHours);
	}

	@GetMapping
	@Operation(summary = "查詢所有門診時間圖片")
	public R<List<ClinicHours>> getAllClinicHours() {

		List<ClinicHours> clinicHoursList = clinicHoursService.getAllClinicHours();
		return R.ok(clinicHoursList);
	}

	@GetMapping("pagination")
	@Operation(summary = "查詢所有門診時間圖片(分頁)")
	public R<IPage<ClinicHours>> getAllClinicHours(@RequestParam Integer page, @RequestParam Integer size) {
		Page<ClinicHours> pageInfo = new Page<>(page, size);
		IPage<ClinicHours> clinicHoursList = clinicHoursService.getAllClinicHours(pageInfo);

		return R.ok(clinicHoursList);
	}

	@Operation(summary = "新增門診時間圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PostMapping
	public R<Void> saveClinicHours(@RequestBody InsertClinicHoursDTO insertClinicHoursDTO) {
		clinicHoursService.insertClinicHours(insertClinicHoursDTO);
		return R.ok();

	}

	@Operation(summary = "更新門診時間圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateClinicHours(@RequestBody UpdateClinicHoursDTO clinicHoursDTO) {
		clinicHoursService.updateClinicHours(clinicHoursDTO);
		return R.ok();

	}

	@Operation(summary = "刪除門診時間圖片")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteClinicHours(@PathVariable("id") Long clinicHoursId) {
		clinicHoursService.deleteClinicHours(clinicHoursId);
		return R.ok();

	}

}
