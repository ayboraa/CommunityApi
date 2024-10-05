package org.community.api.service.impl;

import org.community.api.entity.MemberEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private String email; // Use email as the username
    private String password;
    private boolean active;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(MemberEntity member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
       // this.active = member.isActive();
       // this.authorities = member.getAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Return email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Define your own logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Define your own logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Define your own logic if needed
    }

    @Override
    public boolean isEnabled() {
        return active; // Return the active status of the user
    }
}