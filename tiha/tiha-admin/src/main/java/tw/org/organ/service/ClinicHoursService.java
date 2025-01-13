package tw.org.organ.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.org.organ.pojo.DTO.InsertClinicHoursDTO;
import tw.org.organ.pojo.DTO.UpdateClinicHoursDTO;
import tw.org.organ.pojo.entity.ClinicHours;

/**
 * <p>
 * 門診時間圖片表 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
public interface ClinicHoursService extends IService<ClinicHours> {

	/**
	 * 獲取全部門診時間圖片
	 * 
	 * @return
	 */
	List<ClinicHours> getAllClinicHours();

	/**
	 * 獲取全部門診時間圖片(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<ClinicHours> getAllClinicHours(Page<ClinicHours> page);

	/**
	 * 獲取單一門診時間圖片
	 * 
	 * @param clinicHoursId
	 * @return
	 */
	ClinicHours getClinicHours(Long clinicHoursId);

	/**
	 * 新增門診時間圖片
	 * 
	 * @param insertClinicHoursDTO
	 */
	void insertClinicHours(InsertClinicHoursDTO insertClinicHoursDTO);

	/**
	 * 更新門診時間圖片
	 * 
	 * @param updateClinicHoursDTO
	 */
	void updateClinicHours(UpdateClinicHoursDTO updateClinicHoursDTO);

	/**
	 * 根據clinicHoursId刪除門診時間圖片
	 * 
	 * @param clinicHoursId
	 */
	void deleteClinicHours(Long clinicHoursId);

}
