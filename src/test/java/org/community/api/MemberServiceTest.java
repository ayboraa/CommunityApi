package org.community.api;


import org.community.api.common.Email;
import org.community.api.service.Member;
import org.community.api.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    String password = "pass123";
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = passwordEncoder.encode(password);


    @Test
    public void testSingleCRUD() {

        // Test create.
        Member member = new Member(null, null, null, new Email("janesmith@example.com"), hashedPassword, true);
        Member savedMember = memberService.saveMember(member);
        assertThat(savedMember).isNotNull();
        //// assertThat(savedMember.getName()).isEqualTo("Jane");
        assertThat(savedMember.getEmail().email()).isEqualTo("janesmith@example.com");

        // Test read.
        Member foundMember = memberService.findMemberById(savedMember.getId());
        assertThat(foundMember).isNotNull();
        //// assertThat(foundMember.getName()).isEqualTo("Jane");
        assertThat(foundMember.getEmail().email()).isEqualTo("janesmith@example.com");

        // Test update.
        Member toUpdate = new Member(foundMember.getId(), member.getName(), member.getSurname(), member.getEmail(), hashedPassword, true);
        Member updatedMember = memberService.updateMember(savedMember.getId(), toUpdate);
        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.isAdmin()).isEqualTo(true);
        // Ensure update is correct.
        updatedMember = memberService.findMemberById(updatedMember.getId());
        assertThat(updatedMember).isNotNull();
        assertThat(updatedMember.isAdmin()).isEqualTo(true);

        // Test read.
        List<Member> memberList = memberService.getAllMembers();
        assertThat(memberList).isNotNull();
        assertThat(memberList.size()).isEqualTo(1);

        // Test delete.
        memberService.deleteMember(memberList.getFirst().getId());
        memberList = memberService.getAllMembers();
        assertThat(memberList).isNotNull();
        assertThat(memberList.size()).isEqualTo(0);

    }
}
