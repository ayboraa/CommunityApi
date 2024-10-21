package org.community.api.mapper;

import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.community.api.entity.MemberEntity;
import org.community.api.dto.admin.AdminMemberDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper implements Mapper<AdminMemberDTO, MemberEntity> {
    @Override
    public MemberEntity toEntity(AdminMemberDTO member) {
        return new MemberEntity(member.getId().uuid(), member.getName(), member.getSurname(), member.getEmail().email(), member.getPassword(), member.isAdmin());
    }

    @Override
    public AdminMemberDTO toDTO(MemberEntity entity) {
        return new AdminMemberDTO(new MemberId(entity.getId()), entity.getFirstName(), entity.getLastName(), new Email(entity.getEmail()), entity.getPassword(), entity.isAdmin());
    }

    @Override
    public List<AdminMemberDTO> toDTOList(List<MemberEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}