package tw.org.organ.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.org.organ.pojo.DTO.InsertFileDTO;
import tw.org.organ.pojo.entity.File;

/**
 * <p>
 * 上傳檔案表，用於應用在只有上傳檔案的頁面 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
public interface FileService extends IService<File> {

	List<File> getAllFileByGroup(String group);
	
	List<File> getAllFileByGroupAndType(String group,String type);
	
	IPage<File> getAllFileByGroup(String group, Page<File> pageInfo);

	void addFile(MultipartFile[] file, InsertFileDTO insertFileDTO);

	void deleteFile(Long fileId);

	void deleteFile(List<Long> fileIdList);

}
