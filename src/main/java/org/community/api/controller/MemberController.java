package org.community.api.controller;


import org.community.api.common.MemberId;
import org.community.api.service.Member;
import org.community.api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


    @GetMapping(value = "/", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<Member>> getMembers() {
        List<Member> members = memberService.getAllMembers();

        return ResponseEntity.ok(members);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Member> getMemberById(@PathVariable UUID id) {
        Member member = memberService.findMemberById(new MemberId(id));
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable UUID id, @RequestBody Member newMember) {
        Member updatedMemberData = memberService.updateMember(new MemberId(id), newMember);
        return ResponseEntity.ok(updatedMemberData);
    }

    @PostMapping("/")
    public ResponseEntity<Member> createMember(@RequestBody Member newMember) {
        Member createdMember = memberService.saveMember(newMember);
        return ResponseEntity.ok(createdMember);
    }


    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteMember(@PathVariable UUID id) {
        memberService.deleteMember(new MemberId(id));
        return ResponseEntity.ok().build();
    }



}
