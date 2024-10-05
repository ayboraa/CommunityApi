package org.community.api.service;

import jakarta.annotation.Nullable;
import org.community.api.common.Email;
import org.community.api.common.MemberId;
import org.springframework.util.Assert;

public class Member {
    private MemberId id;
    private String name;
    private String surname;
    private Email email;
    private String password;
    private boolean isAdmin;


        public Member(@Nullable MemberId memberId, @Nullable String name,  @Nullable String surname, Email email, String pwd, boolean isAdmin) {
            Assert.notNull(email, "Email cannot be null");
            Assert.notNull(pwd, "Password cannot be null");
            Assert.notNull(isAdmin, "Admin status cannot be null");

            this.id = (memberId == null) ? new MemberId() : memberId;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.password = pwd;
            this.isAdmin = isAdmin;

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

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isAdmin() {
        return isAdmin;
    }



}
