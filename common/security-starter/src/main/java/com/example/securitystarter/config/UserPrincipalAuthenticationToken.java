package com.example.securitystarter.config;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.securitystarter.entity.UserPrincipal;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final Jwt jwt;
    private final UserPrincipal principal;

    public UserPrincipalAuthenticationToken(Jwt jwt, UserPrincipal principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jwt = jwt;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt.getTokenValue(); // or null
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public Jwt getJwt() {
        return jwt;
    }
}