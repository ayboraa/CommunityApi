package org.community.api.dto.user;

import jakarta.annotation.Nullable;
import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.springframework.util.Assert;


public class MemberDTO {
    private MemberId id;
    private String name;
    private String surname;
    private Email email;


        public MemberDTO(@Nullable MemberId memberId, @Nullable String name, @Nullable String surname, Email email) {
            Assert.notNull(email, "Email cannot be null");

            this.id = (memberId == null) ? new MemberId() : memberId;
            this.name = name;
            this.surname = surname;
            this.email = email;
        }


    public MemberId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

}
