package tw.org.organ.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.org.organ.system.mapper.SysUserRoleMapper;
import tw.org.organ.system.pojo.entity.SysUserRole;
import tw.org.organ.system.service.SysUserRoleService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 用戶與角色 - 多對多關聯表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
