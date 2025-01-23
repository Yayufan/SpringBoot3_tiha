package tw.com.tiha.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.com.tiha.convert.TagConvert;
import tw.com.tiha.mapper.TagMapper;
import tw.com.tiha.pojo.DTO.InsertTagDTO;
import tw.com.tiha.pojo.DTO.UpdateTagDTO;
import tw.com.tiha.pojo.entity.Tag;
import tw.com.tiha.service.TagService;

/**
 * <p>
 * 標籤表,用於對Member進行分組 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-01-23
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

	private final TagConvert tagConvert;

	@Override
	public List<Tag> getAllTag() {
		List<Tag> tagList = baseMapper.selectList(null);
		return tagList;
	}

	@Override
	public IPage<Tag> getAllTag(Page<Tag> page) {
		Page<Tag> tagPage = baseMapper.selectPage(page, null);
		return tagPage;
	}

	@Override
	public Tag getTag(Long tagId) {
		Tag tag = baseMapper.selectById(tagId);
		return tag;
	}

	@Override
	public void insertTag(InsertTagDTO insertTagDTO) {
		Tag tag = tagConvert.insertDTOToEntity(insertTagDTO);
		baseMapper.insert(tag);
	}

	@Override
	public void updateTag(UpdateTagDTO updateTagDTO) {
		Tag tag = tagConvert.updateDTOToEntity(updateTagDTO);
		baseMapper.updateById(tag);
	}

	
	@Override
	public void deleteTag(Long tagId) {
		baseMapper.deleteById(tagId);
	}

}
