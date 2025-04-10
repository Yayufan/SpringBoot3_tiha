package tw.com.tiha.service;

import tw.com.tiha.pojo.DTO.InsertTagDTO;
import tw.com.tiha.pojo.DTO.UpdateTagDTO;
import tw.com.tiha.pojo.entity.Tag;
import tw.com.tiha.pojo.entity.Tag;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 標籤表,用於對Member進行分組 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-01-23
 */
public interface TagService extends IService<Tag> {

	/**
	 * 獲取全部標籤
	 * 
	 * @return
	 */
	List<Tag> getAllTag();
	
	
	/**
	 * 根據 type 獲取所有標籤
	 * 
	 * @param type
	 * @return
	 */
	List<Tag> getAllTagByType(String type);

	/**
	 * 獲取全部標籤(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<Tag> getAllTag(Page<Tag> page);
	
	

	/**
	 * 獲取單一標籤
	 * 
	 * @param tagId
	 * @return
	 */
	Tag getTag(Long tagId);

	/**
	 * 新增標籤
	 * 
	 * @param insertTagDTO
	 */
	void insertTag(InsertTagDTO insertTagDTO);

	/**
	 * 更新標籤
	 * 
	 * @param updateTagDTO
	 */
	void updateTag(UpdateTagDTO updateTagDTO);

	/**
	 * 根據tagId刪除標籤
	 * 
	 * @param tagId
	 */
	void deleteTag(Long tagId);
	
	
	
	/**
	 * 為複數member 添加/更新/刪除 tag
	 * 
	 * @param memberIdList
	 * @param tagId
	 */
	void assignMemberToTag(List<Long> targetMemberIdList,Long tagId);
	
	
}
