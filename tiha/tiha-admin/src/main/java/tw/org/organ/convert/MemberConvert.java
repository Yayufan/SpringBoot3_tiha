package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertMemberDTO;
import tw.org.organ.pojo.DTO.MemberLoginInfo;
import tw.org.organ.pojo.DTO.ProviderRegisterDTO;
import tw.org.organ.pojo.DTO.UpdateMemberDTO;
import tw.org.organ.pojo.VO.MemberVO;
import tw.org.organ.pojo.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberConvert {

	Member loginInfoToEntity(MemberLoginInfo memberLoginInfo);
	
	Member insertDTOToEntity(InsertMemberDTO insertMemberDTO);

	Member updateDTOToEntity(UpdateMemberDTO updateMemberDTO);
	
	Member providerRegisterDTO(ProviderRegisterDTO providerRegisterDTO);
	
	MemberVO entityToVO(Member member);
	
}
