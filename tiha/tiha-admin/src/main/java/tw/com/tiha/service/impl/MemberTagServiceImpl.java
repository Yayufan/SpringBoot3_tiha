package tw.com.tiha.service.impl;

import tw.com.tiha.pojo.entity.MemberTag;
import tw.com.tiha.mapper.MemberTagMapper;
import tw.com.tiha.service.MemberTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * member表 和 tag表的關聯表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-01-23
 */
@Service
public class MemberTagServiceImpl extends ServiceImpl<MemberTagMapper, MemberTag> implements MemberTagService {

}
