package com.example.identity_service.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dto.identity_service.response.IntrospectResponse;
import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.request.IntrospectRequest;
import com.example.identity_service.dto.request.LogoutRequest;
import com.example.identity_service.dto.request.RefreshTokenRequest;
import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.entity.InvalidatedToken;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.repository.InvalidatedTokenRepository;
import com.example.identity_service.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Value("${jwt.issuer}")
    protected String ISSUER;

    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse googleAuthenticate(UserCreationRequest request) {
        return null;
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        SignedJWT signedJWT = null;
        try {
            signedJWT = verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder().isValid(isValid)
                .userId(signedJWT != null ? signedJWT.getJWTClaimsSet().getStringClaim("userId") : null)
                .roles(signedJWT != null ? signedJWT.getJWTClaimsSet().getStringClaim("scope") : null)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {
        byte[] keyBytes = Base64.from(SIGNER_KEY).decode();
        JWSVerifier verifier = new MACVerifier(keyBytes);
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiration = isRefresh
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        // System.out.println("signerKey bytes: " + SIGNER_KEY.getBytes().length);
        // System.out.println("decoded Base64 key bytes: " +
        // Base64.from(SIGNER_KEY).decode().length);
        if (!(verified && expiration.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {

            var signToken = verifyToken(request.getToken(), true);

            String jti = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jti).expiryTime(expiryTime).build();
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            log.warn("Token already expired");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository
                // .findAll().stream().filter(u ->
                // u.getUsername().equals(request.getUsername())).findAny()
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println(request + " " + authenticated);
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    private String generateToken(User user) {
        JwsHeader header = JwsHeader.with(JWT_ALGORITHM)
                .build();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getUsername())
                .issuer(ISSUER)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS))
                .claim("customClaim", "Hello world!")
                .claim("userId", user.getId())
                .claim("scope", buildScope(user))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, jwtClaimsSet)).getTokenValue();
        // Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // JWSObject jwsObject = new JWSObject(header, payload);
        // try {
        // jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
        // return jwsObject.serialize();
        // } catch (JOSEException e) {
        // log.error("Cannot read token", e);
        // throw new RuntimeException(e);
        // }
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jti = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder().id(jti).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();
        System.out.println(username);
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    private String buildScope(User user) {
        // StringJoiner stringJoiner = new StringJoiner(" ");
        // user.getRoles().forEach(stringJoiner::add);
        // return stringJoiner.toString();

        // return user.getRoles().size() == 1
        // ? user.getRoles().stream().findFirst().get()
        // : user.getRoles().stream().reduce("", (prev, curr) -> prev + " " + curr);

        return String.join(" ", user.getRoles().stream().map(r -> {
            return r.getName() + (CollectionUtils.isEmpty(r.getPermissions())
                    ? ""
                    : " "
                            + String.join(
                                    " ",
                                    r.getPermissions().stream()
                                            .map(p -> p.getName())
                                            .toArray(String[]::new)));
        })
                .toArray(String[]::new));

        // return "";
    }
}
