package tw.org.organ.pojo.VO;

import java.util.List;

import lombok.Data;

@Data
public class CatrgoryVO {

	private Long id;
	private Long parentId;
	private String label;
	private String description;
	private List<CatrgoryVO> children;
	
	
}
