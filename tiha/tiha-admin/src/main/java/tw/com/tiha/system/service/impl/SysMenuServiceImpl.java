package tw.com.tiha.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import tw.com.tiha.system.mapper.SysMenuMapper;
import tw.com.tiha.system.pojo.entity.SysMenu;
import tw.com.tiha.system.service.SysMenuService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜單表-最底層細部權限也存在這張表,包含路由、路由組件、路由參數... 組裝動態路由返回給前端 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-05-10
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

}
