package tw.org.organ.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.org.organ.mapper.UserMapper;
import tw.org.organ.pojo.DTO.InsertUserDTO;
import tw.org.organ.pojo.DTO.UpdateUserDTO;
import tw.org.organ.pojo.entity.User;
import tw.org.organ.service.UserService;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 使用者表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<User> getAllUser(Page<User> page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertUser(InsertUserDTO insertUserDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(UpdateUserDTO updateUserDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub

	}

}
