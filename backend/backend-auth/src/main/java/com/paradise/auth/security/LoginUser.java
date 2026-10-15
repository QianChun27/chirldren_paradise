package com.paradise.auth.security;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 登录用户主体：JWT 解析后在 {@code JwtFilter} 中构建，存入请求属性与 Spring Security 上下文。
 */
@Data
public class LoginUser {

    private Long userId;

    private String username;

    private List<String> roleCodes;

    private Set<String> permissions;
}
