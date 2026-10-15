package com.paradise.auth.dto;

import lombok.Data;

/**
 * 令牌响应：Access + Refresh。
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;
}
