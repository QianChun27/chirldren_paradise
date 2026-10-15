package com.paradise.common.result;

import lombok.Getter;

/**
 * 统一业务码段（与后端架构文档 §2 一致）：
 * 0 成功；1xxx 参数；2xxx 权限/越权；3xxx 内容状态冲突；4xxx 媒体/导入；5xxx 系统。
 */
@Getter
public enum ResultCode {

    SUCCESS(0, "ok"),

    // 1xxx 参数
    PARAM_ERROR(1000, "参数错误"),

    // 2xxx 权限 / 越权
    UNAUTHORIZED(2001, "未登录或登录已过期"),
    FORBIDDEN(2002, "无权限访问"),

    // 3xxx 内容状态冲突（预留，资源模块使用）
    CONTENT_STATE_CONFLICT(3000, "内容状态冲突"),

    // 4xxx 媒体 / 导入（预留）
    MEDIA_ERROR(4000, "媒体处理失败"),

    // 5xxx 系统
    SYSTEM_ERROR(5000, "系统错误");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
