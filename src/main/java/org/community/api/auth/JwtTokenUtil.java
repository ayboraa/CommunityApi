package org.community.api.auth;

import io.jsonwebtoken.Claims;
import org.community.api.service.JwtBlacklistService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil {




    // Ensure your secret key is 32 bytes (256 bits) long
    @Value("${jwt.secret}")
    private String secretKey;


    @Autowired
    @Lazy
    private JwtBlacklistService jwtBlacklistService;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        // Initialize the signing key using the secret key
        signingKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractJti(String token) {
        return extractClaim(token, claims -> claims.get("jti", String.class));
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey) // Ensure you're using the same signing key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, List<String> roles) {
        return createToken(username, roles);
    }

    private String createToken(String subject, List<String> roles) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .claim("roles", roles)
                .setId(UUID.randomUUID().toString())
                .signWith(signingKey)
                .compact();
    }

    public Boolean validateToken(String token) {
        final String tokenUsername = extractUsername(token);
        return (!isTokenExpired(token) && !jwtBlacklistService.isTokenBlacklisted(token));
    }
}