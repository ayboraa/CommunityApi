package org.community.api.service.impl;

import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.entity.MemberEntity;
import org.community.api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws ResourceNotFoundException {
        Optional<MemberEntity> opt = memberRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return new UserDetailsImpl(opt.get());
    }
}