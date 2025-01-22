package tw.com.tiha.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tw.com.tiha.pojo.entity.OrganDonationConsent;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
public interface OrganDonationConsentMapper extends BaseMapper<OrganDonationConsent> {

	List<OrganDonationConsent> selectOrganDonationConsentsByDate(String startDate,String endDate);
	
}
