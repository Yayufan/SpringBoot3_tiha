package tw.com.tiha.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import tw.com.tiha.pojo.DTO.InsertMemberDTO;
import tw.com.tiha.pojo.DTO.MemberLoginInfo;
import tw.com.tiha.pojo.DTO.ProviderRegisterDTO;
import tw.com.tiha.pojo.DTO.UpdateMemberDTO;
import tw.com.tiha.pojo.VO.MemberTagVO;
import tw.com.tiha.pojo.VO.MemberVO;
import tw.com.tiha.pojo.entity.Member;

/**
 * <p>
 * 會員表 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-09-12
 */
public interface MemberService extends IService<Member> {

	/**
	 * 獲取全部會員資料
	 * 
	 * @return
	 */
	List<Member> getAllMember();

	/**
	 * 獲取全部會員資料(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<Member> getAllMember(Page<Member> page);

	/**
	 * 獲取符合查詢條件的所有會員資料(分頁)
	 * 
	 * @param page
	 * @param status
	 * @return
	 */
	IPage<Member> getAllMemberByStatus(Page<Member> page, String status, String queryText);

	/**
	 * 獲取單一會員資料
	 * 
	 * @param newsId
	 * @return
	 */
	Member getMember(Long memberId);

	/**
	 * 獲取會員資料總數
	 * 
	 * @return
	 */
	Long getMemberCount();

	/**
	 * 根據審核狀態，獲取會員資料總數
	 * 
	 * @param status
	 * @return
	 */
	Long getMemberCount(String status);

	/**
	 * 新增會員資料
	 * 
	 * @param insertMemberDTO
	 */
	Long insertMember(InsertMemberDTO insertMemberDTO);

	/**
	 * 更新社群軟體登入者的資料
	 * 
	 * @param providerRegisterDTO
	 */
	void updateProviderMember(ProviderRegisterDTO providerRegisterDTO);

	/**
	 * 更新會員資料
	 * 
	 * @param updateMemberDTO
	 */
	void updateMember(UpdateMemberDTO updateMemberDTO);

	/**
	 * 提供一個簡單的API只負責更改會員的狀態為Approved
	 * 
	 * @param memberIdList
	 */
	void updateMember(List<UpdateMemberDTO> updateMemberDTOList);

	/**
	 * 刪除會員資料
	 * 
	 * @param newsId
	 */
	void deleteMember(Long memberId);

	/**
	 * 批量刪除會員資料
	 * 
	 * @param newsIdList
	 */
	void deleteMember(List<Long> memberIdList);

	/**
	 * 會員登入
	 * 
	 * @param memberLoginInfo
	 * @return
	 */
	MemberVO login(MemberLoginInfo memberLoginInfo);

	/**
	 * 會員登出
	 */
	void logout();

	/**
	 * 寄信找回密碼
	 * 
	 * @param email
	 * @throws MessagingException
	 */
	Member forgetPassword(String email) throws MessagingException;

	/**
	 * 下載同意書的Excel列表
	 * 
	 * @param response
	 * @throws IOException
	 */
	void downloadExcel(HttpServletResponse response) throws IOException;

	/**
	 * 為用戶新增/更新/刪除 複數tag
	 * 
	 * @param targetTagIdList
	 * @param memberId
	 */
	void assignTagToMember(List<Long> targetTagIdList, Long memberId);

	
	/**
	 * 根據memberId，獲取會員資料及持有的tag集合
	 * 
	 * @param memberId
	 * @return
	 */
	MemberTagVO getMemberTagVOByMember(Long memberId);
	
	
	/**
	 * 獲取所有 會員資料及持有的tag集合(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<MemberTagVO> getAllMemberTagVO(Page<Member> page);
	
	/**
	 * 根據搜尋條件 獲取會員資料及持有的tag集合(分頁)
	 * 
	 * @param page
	 * @param queryText
	 * @param status
	 * @return
	 */
	IPage<MemberTagVO> getAllMemberTagVOByQuery(Page<Member> page,String queryText,String status);
	
	
}
