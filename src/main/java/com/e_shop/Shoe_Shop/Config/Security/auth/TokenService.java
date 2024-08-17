package com.e_shop.Shoe_Shop.Config.Security.auth;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.Config.utils.RSAKeyProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;


@Service
public class TokenService {
    private final RSAKeyProperties keys;

    public TokenService(RSAKeyProperties keys) {
        this.keys = keys;
    }
                                        
    public String generateJwt(Authentication auth) throws JOSEException {

        RSAPrivateKey privateKey = keys.getRsaPrivateKey();
        JWSSigner signer = new RSASSASigner(privateKey);

        String scope = auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

        JWTClaimsSet.Builder claimBuilder = new JWTClaimsSet.Builder()
                    .subject(auth.getName())
                    .issuer("self")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                    .jwtID(UUID.randomUUID().toString())
                    ;
        
                    
        if (scope.isEmpty()) {
            claimBuilder.claim("roles", scope);
        }

        JWTClaimsSet claims = claimBuilder.build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).build(),
                claims
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token is null or empty");
        }
        RSAPublicKey publicKey = keys.getRsaPublicKey();
        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        SignedJWT signedJWT;
        try {
            signedJWT = SignedJWT.parse(token);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid token format", e);
        }

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new BadCredentialsException("Invalid token or token has expired!");
        }

        return signedJWT;
    }

}
