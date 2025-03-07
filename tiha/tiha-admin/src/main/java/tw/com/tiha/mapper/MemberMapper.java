package tw.com.tiha.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import tw.com.tiha.pojo.entity.Member;

/**
 * <p>
 * 會員表 Mapper 接口
 * </p>
 *
 * @author Joey
 * @since 2024-09-12
 */
public interface MemberMapper extends BaseMapper<Member> {

	/**
	 * 全表查询
	 * @return
	 */
    List<Member> selectAllMembersMySelf();
    
    /**
     * 查詢當前最大的 member_code
     * @return
     */
    @Select("SELECT MAX(code) FROM member")
    Integer selectMaxMemberCode();
    
    
}
