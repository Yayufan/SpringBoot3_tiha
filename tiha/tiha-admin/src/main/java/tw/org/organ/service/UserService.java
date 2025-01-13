package tw.org.organ.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.org.organ.pojo.DTO.InsertUserDTO;
import tw.org.organ.pojo.DTO.UpdateUserDTO;
import tw.org.organ.pojo.entity.User;

/**
 * <p>
 * 使用者表 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
public interface UserService extends IService<User> {

	/**
	 * 獲取全部用戶
	 * 
	 * @return
	 */
	List<User> getAllUser();

	/**
	 * 獲取全部用戶(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<User> getAllUser(Page<User> page);

	/**
	 * 獲取單一用戶
	 * 
	 * @param userId
	 * @return
	 */
	User getUser(Long userId);

	/**
	 * 新增用戶
	 * 
	 * @param insertUserDTO
	 */
	void insertUser(InsertUserDTO insertUserDTO);

	/**
	 * 更新用戶
	 * 
	 * @param updateUserDTO
	 */
	void updateUser(UpdateUserDTO updateUserDTO);

	/**
	 * 根據userId刪除用戶
	 * 
	 * @param userId
	 */
	void deleteUser(Long userId);

}
