package com.paradise.auth.controller;

import com.paradise.auth.dto.LoginRequest;
import com.paradise.auth.dto.RefreshRequest;
import com.paradise.auth.dto.TokenResponse;
import com.paradise.auth.security.LoginUser;
import com.paradise.auth.service.AuthService;
import com.paradise.auth.vo.UserInfoVO;
import com.paradise.common.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证接口：登录 / 刷新 / 当前用户。
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<TokenResponse> login(@Valid @RequestBody LoginRequest req) {
        return Result.success(authService.login(req));
    }

    @PostMapping("/refresh")
    public Result<TokenResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return Result.success(authService.refresh(req.getRefreshToken()));
    }

    @GetMapping("/me")
    public Result<UserInfoVO> me(@AuthenticationPrincipal LoginUser loginUser) {
        return Result.success(authService.me(loginUser));
    }
}
