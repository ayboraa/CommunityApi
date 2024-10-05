package org.community.api.service.impl;

import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.community.api.entity.MemberEntity;
import org.community.api.service.Mapper;
import org.community.api.service.Member;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper implements Mapper<Member, MemberEntity> {
    @Override
    public MemberEntity toEntity(Member member) {
        return new MemberEntity(member.getId().uuid(), member.getName(), member.getSurname(), member.getEmail().email(), member.getPassword(), member.isAdmin());
    }

    @Override
    public Member toDTO(MemberEntity entity) {
        return new Member(new MemberId(entity.getId()), entity.getFirstName(), entity.getLastName(), new Email(entity.getEmail()), entity.getPassword(), entity.isAdmin());
    }

    @Override
    public List<Member> toDTOList(List<MemberEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}
