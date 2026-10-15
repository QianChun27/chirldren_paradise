package com.paradise.common.exception;

import com.paradise.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常：携带业务码（见 {@link ResultCode} 码段）。
 * 由 {@code @RestControllerAdvice} 统一转换为 {@code Result<T>}。
 */
@Getter
public class BizException extends RuntimeException {

    private final int code;

    public BizException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }
}
