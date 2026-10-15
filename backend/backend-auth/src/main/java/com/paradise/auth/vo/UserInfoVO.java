package com.paradise.auth.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 当前用户信息视图（/{@code api/auth/me} 返回）。
 */
@Data
public class UserInfoVO {

    private Long id;

    private String username;

    private String nickname;

    private List<String> roles;

    private Set<String> permissions;
}
