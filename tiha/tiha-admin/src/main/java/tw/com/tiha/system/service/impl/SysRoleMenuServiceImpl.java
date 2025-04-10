package tw.com.tiha.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.com.tiha.system.mapper.SysRoleMenuMapper;
import tw.com.tiha.system.pojo.entity.SysRoleMenu;
import tw.com.tiha.system.service.SysRoleMenuService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色與菜單 - 多對多關聯表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

}
