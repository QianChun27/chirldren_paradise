package com.paradise.auth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.paradise.auth.dto.LoginRequest;
import com.paradise.auth.dto.RefreshRequest;
import com.paradise.auth.dto.TokenResponse;
import com.paradise.auth.entity.SysUser;
import com.paradise.auth.mapper.SysUserMapper;
import com.paradise.auth.security.JwtUtil;
import com.paradise.auth.security.LoginUser;
import com.paradise.auth.vo.UserInfoVO;
import com.paradise.common.exception.BizException;
import com.paradise.common.result.ResultCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 认证业务：登录签发、刷新、当前用户信息。
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TokenResponse login(LoginRequest req) {
        SysUser user = userMapper.selectOne(
                Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, req.getUsername()));
        if (user == null || user.getStatus() == null || user.getStatus() == 0) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        LoginUser loginUser = loadLoginUser(user);
        return new TokenResponse(jwtUtil.generateAccessToken(loginUser),
                                 jwtUtil.generateRefreshToken(user.getId()));
    }

    public TokenResponse refresh(String refreshToken) {
        Claims claims;
        try {
            claims = jwtUtil.parse(refreshToken);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        if (!"refresh".equals(claims.get("type", String.class))) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        Long userId = claims.get("userId", Long.class);
        SysUser user = userMapper.selectById(userId);
        if (user == null || user.getStatus() == null || user.getStatus() == 0) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        LoginUser loginUser = loadLoginUser(user);
        return new TokenResponse(jwtUtil.generateAccessToken(loginUser),
                                 jwtUtil.generateRefreshToken(userId));
    }

    public UserInfoVO me(LoginUser loginUser) {
        SysUser user = userMapper.selectById(loginUser.getUserId());
        UserInfoVO vo = new UserInfoVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setRoles(loginUser.getRoleCodes());
        vo.setPermissions(loginUser.getPermissions());
        return vo;
    }

    private LoginUser loadLoginUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setRoleCodes(userMapper.selectRoleCodesByUserId(user.getId()));
        Set<String> permissions = new HashSet<>(userMapper.selectPermissionCodesByUserId(user.getId()));
        loginUser.setPermissions(permissions);
        return loginUser;
    }
}
