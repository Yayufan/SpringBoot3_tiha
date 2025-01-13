package tw.org.organ.service.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.OrganDonationConsentConvert;
import tw.org.organ.mapper.OrganDonationConsentMapper;
import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.pojo.excelPojo.OrganDonationConsentExcel;
import tw.org.organ.service.OrganDonationConsentService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
@Service
@RequiredArgsConstructor
public class OrganDonationConsentServiceImpl extends ServiceImpl<OrganDonationConsentMapper, OrganDonationConsent>
		implements OrganDonationConsentService {

	private final OrganDonationConsentConvert organDonationConsentConvert;
	private String DEFAULT_STATUS = "0";

	@Override
	public OrganDonationConsent getOrganDonationConsent(Long organDonationConsentId) {
		OrganDonationConsent organDonationConsent = baseMapper.selectById(organDonationConsentId);
		return organDonationConsent;
	}

	@Override
	public List<OrganDonationConsent> getAllOrganDonationConsent() {
		List<OrganDonationConsent> organDonationConsentList = baseMapper.selectList(null);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsent(Page<OrganDonationConsent> page) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.orderByDesc(OrganDonationConsent::getOrganDonationConsentId);

		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page,
				organDonationConsentQueryWrapper);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsentByStatus(Page<OrganDonationConsent> page,
			String status, String queryText) {

		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();

		// 如果 status 不為空字串、空格字串、Null 時才加入篩選條件
		organDonationConsentQueryWrapper.eq(StringUtils.isNotBlank(status), OrganDonationConsent::getStatus, status)
				// 當 queryText 不為空字串、空格字串、Null 時才加入篩選條件
				.and(StringUtils.isNotBlank(queryText),
						wrapper -> wrapper.like(OrganDonationConsent::getName, queryText).or()
								.like(OrganDonationConsent::getIdCard, queryText).or()
								.like(OrganDonationConsent::getContactNumber, queryText).or()
								.like(OrganDonationConsent::getPhoneNumber, queryText))
				.orderByDesc(OrganDonationConsent::getOrganDonationConsentId);

		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page,
				organDonationConsentQueryWrapper);
		return organDonationConsentList;

	}

	@Override
	public Long getOrganDonationConsentCount() {
		Long selectCount = baseMapper.selectCount(null);
		return selectCount;
	}

	@Override
	public Long getOrganDonationConsentCount(String status) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.eq(OrganDonationConsent::getStatus, status);

		Long selectCount = baseMapper.selectCount(organDonationConsentQueryWrapper);
		return selectCount;
	}

	@Override
	public void insertOrganDonationConsent(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO) {
		OrganDonationConsent organDonationConsent = organDonationConsentConvert
				.insertDTOToEntity(insertOrganDonationConsentDTO);
		organDonationConsent.setSignatureDate(LocalDate.now());
		organDonationConsent.setStatus(DEFAULT_STATUS);

		baseMapper.insert(organDonationConsent);

	}

	@Override
	public void updateOrganDonationConsent(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO) {
		OrganDonationConsent organDonationConsent = organDonationConsentConvert
				.updateDTOToEntity(updateOrganDonationConsentDTO);
		baseMapper.updateById(organDonationConsent);
	}

	@Override
	public void updateOrganDonationConsent(List<UpdateOrganDonationConsentDTO> updateOrganDonationConsentDTOList) {
		for (UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO : updateOrganDonationConsentDTOList) {
			this.updateOrganDonationConsent(updateOrganDonationConsentDTO);
		}

	}

	@Override
	public void deleteOrganDonationConsent(Long organDonationConsentId) {
		baseMapper.deleteById(organDonationConsentId);
	}

	@Override
	public void deleteOrganDonationConsent(List<Long> organDonationConsentIdList) {
		baseMapper.deleteBatchIds(organDonationConsentIdList);
	}

	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		// 这里URLEncoder.encode可以防止中文乱码 ， 和easyexcel没有关系
		String fileName = URLEncoder.encode("測試", "UTF-8").replaceAll("\\+", "%20");
		response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");

		List<OrganDonationConsent> allOrganDonationConsent = this.getAllOrganDonationConsent();
		List<OrganDonationConsentExcel> excelData = allOrganDonationConsent.stream().map(organDonationConsent -> {
			return organDonationConsentConvert.entityToExcel(organDonationConsent);
		}).collect(Collectors.toList());

		EasyExcel.write(response.getOutputStream(), OrganDonationConsentExcel.class).sheet("清單").doWrite(excelData);

	}

}
