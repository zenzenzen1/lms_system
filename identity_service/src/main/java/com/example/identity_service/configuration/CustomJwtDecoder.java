package com.example.identity_service.configuration;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import com.nimbusds.jwt.SignedJWT;

@Component
@SuppressWarnings("outdated")
@Deprecated
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    // @Autowired
    // private AuthenticationService authenticationService;
    // private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        // Validate token is not null or empty
        if (token == null || token.trim().isEmpty()) {
            throw new JwtException("JWT token is null or empty");
        }

        // Basic format validation (JWT should have 3 parts separated by dots)
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new JwtException("Invalid JWT token format - must have 3 parts separated by dots");
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            
            // Additional validation - check if claims set exists
            if (signedJWT.getJWTClaimsSet() == null) {
                throw new JwtException("JWT token has no claims");
            }
            
            return new Jwt(
                    token,
                    signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
                    signedJWT.getHeader().toJSONObject(),
                    signedJWT.getJWTClaimsSet().toJSONObject());

        } catch (ParseException e) {
            System.out.println("Invalid token: " + e.getMessage());
            throw new JwtException("Invalid JWT token format", e);
        } catch (Exception e) {
            System.out.println("Error parsing token: " + e.getMessage());
            throw new JwtException("Error decoding JWT token", e);
        }
    }
}
