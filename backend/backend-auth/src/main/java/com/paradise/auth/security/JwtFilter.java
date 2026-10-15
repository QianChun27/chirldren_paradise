package com.paradise.auth.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JWT 校验过滤器：解析 Authorization 头，构建 {@link LoginUser} 并写入 SecurityContext 与请求属性。
 * 校验失败（缺失/过期/非法）时不清空上下文，交由 SecurityFilterChain 的 authorizeHttpRequests 统一返回 401。
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                var claims = jwtUtil.parse(token);
                LoginUser loginUser = new LoginUser();
                loginUser.setUserId(claims.get("userId", Long.class));
                loginUser.setUsername(claims.getSubject());
                loginUser.setRoleCodes(claims.get("roles", List.class));
                loginUser.setPermissions(Set.copyOf(claims.get("perms", List.class)));

                List<SimpleGrantedAuthority> authorities = loginUser.getPermissions().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("loginUser", loginUser);
            } catch (JwtException | IllegalArgumentException ignored) {
                // 非法/过期令牌：保持匿名，后续授权链返回 401
            }
        }
        filterChain.doFilter(request, response);
    }
}
