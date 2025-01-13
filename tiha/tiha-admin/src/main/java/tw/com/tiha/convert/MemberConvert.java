package tw.com.tiha.convert;

import org.mapstruct.Mapper;

import tw.com.tiha.pojo.DTO.InsertMemberDTO;
import tw.com.tiha.pojo.DTO.MemberLoginInfo;
import tw.com.tiha.pojo.DTO.ProviderRegisterDTO;
import tw.com.tiha.pojo.DTO.UpdateMemberDTO;
import tw.com.tiha.pojo.VO.MemberVO;
import tw.com.tiha.pojo.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberConvert {

	Member loginInfoToEntity(MemberLoginInfo memberLoginInfo);
	
	Member insertDTOToEntity(InsertMemberDTO insertMemberDTO);

	Member updateDTOToEntity(UpdateMemberDTO updateMemberDTO);
	
	Member providerRegisterDTO(ProviderRegisterDTO providerRegisterDTO);
	
	MemberVO entityToVO(Member member);
	
}
