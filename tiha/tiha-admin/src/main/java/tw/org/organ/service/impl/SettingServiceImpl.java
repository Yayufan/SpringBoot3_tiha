package tw.org.organ.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.SettingConvert;
import tw.org.organ.mapper.SettingMapper;
import tw.org.organ.pojo.DTO.UpdateSettingDTO;
import tw.org.organ.pojo.entity.Setting;
import tw.org.organ.service.SettingService;

/**
 * <p>
 * 系統設定表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@RequiredArgsConstructor
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

	private final SettingConvert settingConvert;

	@Override
	public void updateSetting(List<UpdateSettingDTO> updateSettingDTOList) {
		// TODO Auto-generated method stub
		for (UpdateSettingDTO updateSettingDTO : updateSettingDTOList) {
			Setting setting = settingConvert.updateDTOToEntity(updateSettingDTO);
			baseMapper.updateById(setting);
		}

	}

	@Override
	public List<Setting> getAllSetting() {
		// TODO Auto-generated method stub
		List<Setting> settingList = baseMapper.selectList(null);
		return settingList;
	}

}
