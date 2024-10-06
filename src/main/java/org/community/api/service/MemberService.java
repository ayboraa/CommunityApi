package org.community.api.service;


import jakarta.transaction.Transactional;
import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.entity.MemberEntity;
import org.community.api.repository.MemberRepository;
import org.community.api.service.impl.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final MemberMapper _mapper = new MemberMapper();

    public Member saveMember(Member member){
        MemberEntity entity = new MemberEntity(new MemberId().uuid(), member.getName(), member.getSurname(), member.getEmail().email(), passwordEncoder.encode(member.getPassword()), member.isAdmin());
        memberRepository.save(entity);
        return _mapper.toDTO(entity);
    }




    public Member findMemberByEmail(Email email) {

        Optional<MemberEntity> opt = memberRepository.findByEmail(email.email());
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Member with email " + email.email() + " not found.");
        else
            return _mapper.toDTO(opt.get());

    }


    public Member findMemberById(MemberId id) {

        Optional<MemberEntity> opt = memberRepository.findById(id.uuid());
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Member with ID " + id.uuid() + " not found.");
        else {
            MemberEntity e = opt.get();
            e.setPassword(null);
            return _mapper.toDTO(e);
        }

    }

    public void deleteMember(MemberId id) {

        if (!memberRepository.existsById(id.uuid())) {
            throw new ResourceNotFoundException("Member not found with id: " + id.uuid());
        }

        memberRepository.deleteById(id.uuid());

    }

    public List<Member> getAllMembers() {
        List<MemberEntity> entities = memberRepository.findAll();
        return _mapper.toDTOList(entities);
    }

    @Transactional
    public Member updateMember(MemberId id, Member newMember) {
        return memberRepository.findById(id.uuid())
                .map(memberEntity -> {
                    memberEntity.setFirstName(newMember.getName());
                    memberEntity.setLastName(newMember.getSurname());
                    memberEntity.setEmail(newMember.getEmail().email());
                    memberEntity.setPassword(newMember.getPassword());
                    memberEntity.setAdmin(newMember.isAdmin());
                    return _mapper.toDTO(memberRepository.save(memberEntity));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + id.uuid()));
    }

}
