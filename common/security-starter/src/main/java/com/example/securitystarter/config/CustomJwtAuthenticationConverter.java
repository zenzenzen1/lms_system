package com.example.securitystarter.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import com.example.securitystarter.entity.UserPrincipal;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);

        UserPrincipal principal = new UserPrincipal(
                jwt.getClaim("userId"), // custom claim
                jwt.getSubject(), // sub
                jwt.getClaimAsString("scope"), // roles
                authorities);

        // 👇 set the custom UserPrincipal instead of principal.getUsername()
        return new UserPrincipalAuthenticationToken(jwt, principal, authorities);
    }
}