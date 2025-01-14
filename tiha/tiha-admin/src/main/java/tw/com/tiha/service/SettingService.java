package tw.com.tiha.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import tw.com.tiha.pojo.DTO.UpdateSettingDTO;
import tw.com.tiha.pojo.entity.Setting;

/**
 * <p>
 * 系統設定表 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
public interface SettingService extends IService<Setting> {

	/**
	 * 獲取所有系統設定
	 * 
	 * @return
	 */
	List<Setting> getAllSetting();

	/**
	 * 更新系統設定
	 * 
	 * @param updateNewsDTO
	 */
	void updateSetting(List<UpdateSettingDTO> updateSettingDTOList);

}
