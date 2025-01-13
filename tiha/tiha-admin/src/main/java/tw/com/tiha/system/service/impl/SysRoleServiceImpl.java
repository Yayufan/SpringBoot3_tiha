package tw.com.tiha.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.com.tiha.system.mapper.SysRoleMapper;
import tw.com.tiha.system.pojo.entity.SysRole;
import tw.com.tiha.system.service.SysRoleService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 - 透過設置角色達成較廣泛的權限管理 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
