package com.paradise.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT 工具：签发 Access / Refresh Token，并解析校验。
 * 载荷含 userId、角色列表、权限表达式列表（供 RbacInterceptor 鉴权）。
 */
@Component
public class JwtUtil {

    @Value("${paradise.jwt.secret}")
    private String secret;

    @Value("${paradise.jwt.access-ttl-minutes:30}")
    private long accessTtlMinutes;

    @Value("${paradise.jwt.refresh-ttl-days:7}")
    private long refreshTtlDays;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** 签发 Access Token。 */
    public String generateAccessToken(LoginUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("roles", user.getRoleCodes());
        claims.put("perms", List.copyOf(user.getPermissions()));
        return build(user.getUsername(), claims, accessTtlMinutes * 60L);
    }

    /** 签发 Refresh Token（仅携带 userId）。 */
    public String generateRefreshToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("type", "refresh");
        return build(String.valueOf(userId), claims, refreshTtlDays * 24 * 3600L);
    }

    private String build(String subject, Map<String, Object> claims, long ttlSeconds) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(key())
                .compact();
    }

    /** 解析并校验签名/过期，返回 Claims。失败抛出 {@link JwtException}。 */
    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
