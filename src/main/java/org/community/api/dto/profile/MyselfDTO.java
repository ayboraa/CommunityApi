package org.community.api.dto.profile;

import org.community.api.service.Member;

import java.util.UUID;

public class MyselfDTO {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private boolean isAdmin;

    public MyselfDTO(Member member) {
        this.id = member.getId().uuid();
        this.name = member.getName();
        this.surname = member.getSurname();
        this.email = member.getEmail().email();
        this.isAdmin = member.isAdmin();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}