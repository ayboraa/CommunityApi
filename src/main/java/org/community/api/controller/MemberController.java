package org.community.api.controller;


import jakarta.validation.Valid;
import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.community.api.dto.admin.AdminMemberDTO;
import org.community.api.dto.user.MemberDTO;
import org.community.api.service.MemberService;
import org.community.api.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/members", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }



    @GetMapping(value = "/me", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<MemberDTO> getMemberSelf() {
        String email = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        AdminMemberDTO member = memberService.findMemberByEmail(new Email(email));
        return ResponseEntity.ok(new MemberDTO(member.getId(), member.getName(), member.getSurname(), member.getEmail()));
    }

    @PutMapping("/update")
    public ResponseEntity<MemberDTO> updateSelf(@RequestBody @Valid MemberDTO newMember) {
        String email = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        var mail = new Email(email);
        AdminMemberDTO member = memberService.findMemberByEmail(mail);
        AdminMemberDTO updatedMemberData = memberService.updateMemberSelf(member.getId(), newMember);
        // This endpoint cannot change email.
        MemberDTO updatedMember = new MemberDTO(member.getId(), newMember.getName(), newMember.getSurname(), mail);
        return ResponseEntity.ok(updatedMember);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<AdminMemberDTO>> getMembers() {
        List<AdminMemberDTO> members = memberService.getAllMembers();


        return ResponseEntity.ok(members);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AdminMemberDTO> getMemberById(@PathVariable UUID id) {
        AdminMemberDTO member = memberService.findMemberById(new MemberId(id));
        return ResponseEntity.ok(member);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AdminMemberDTO> updateMember(@PathVariable UUID id, @RequestBody @Valid AdminMemberDTO newMember) {
        AdminMemberDTO updatedMemberData = memberService.updateMember(new MemberId(id), newMember);
        return ResponseEntity.ok(updatedMemberData);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<AdminMemberDTO> createMember(@RequestBody @Valid AdminMemberDTO newMember) {
        AdminMemberDTO createdMember = memberService.saveMember(newMember);
        return ResponseEntity.ok(createdMember);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        memberService.deleteMember(new MemberId(id));
        return ResponseEntity.ok().build();
    }



}
