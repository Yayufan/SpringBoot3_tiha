package tw.com.tiha.service.impl;

import tw.com.tiha.pojo.entity.Tag;
import tw.com.tiha.mapper.TagMapper;
import tw.com.tiha.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 標籤表,用於對Member進行分組 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-01-23
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
