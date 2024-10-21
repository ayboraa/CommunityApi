package org.community.api.service;


import jakarta.transaction.Transactional;
import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.dto.admin.AdminMemberDTO;
import org.community.api.dto.user.MemberDTO;
import org.community.api.dto.user.RegisterMemberDTO;
import org.community.api.entity.MemberEntity;
import org.community.api.repository.MemberRepository;
import org.community.api.mapper.MemberMapper;
import org.community.api.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public static MemberId getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getId();
        }
        throw new RuntimeException("User not authenticated");
    }

    public static boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        throw new RuntimeException("User not authenticated");
    }


    public RegisterMemberDTO registerMember(RegisterMemberDTO member) {
        // Validate ?


        MemberEntity entity = new MemberEntity(new MemberId().uuid(), member.getName(), member.getSurname(), member.getEmail().email(), passwordEncoder.encode(member.getPassword()));
        memberRepository.save(entity);
        return member;
    }

    public AdminMemberDTO saveMember(AdminMemberDTO member){
        MemberEntity entity = new MemberEntity(new MemberId().uuid(), member.getName(), member.getSurname(), member.getEmail().email(), passwordEncoder.encode(member.getPassword()), member.isAdmin());
        memberRepository.save(entity);
        return _mapper.toDTO(entity);
    }


    public AdminMemberDTO findMemberByEmail(Email email) {

        Optional<MemberEntity> opt = memberRepository.findByEmail(email.email());
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Member with email " + email.email() + " not found.");
        else
            return _mapper.toDTO(opt.get());

    }


    public AdminMemberDTO findMemberById(MemberId id) {

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

    public List<AdminMemberDTO> getAllMembers() {
        List<MemberEntity> entities = memberRepository.findAll();
        return _mapper.toDTOList(entities);
    }

    @Transactional
    public AdminMemberDTO updateMemberSelf(MemberId id, MemberDTO newMember) {
        return memberRepository.findById(id.uuid())
                .map(memberEntity -> {
                    memberEntity.setFirstName(newMember.getName());
                    memberEntity.setLastName(newMember.getSurname());
                    memberEntity.setEmail(newMember.getEmail().email());
                    return _mapper.toDTO(memberRepository.save(memberEntity));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + id.uuid()));
    }

    @Transactional
    public AdminMemberDTO updateMember(MemberId id, AdminMemberDTO newMember) {
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
