package com.paradise.auth.exception;

import com.paradise.common.exception.BizException;
import com.paradise.common.result.Result;
import com.paradise.common.result.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.validation.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器：将异常统一转换为 {@link Result}。
 * 置于 {@code com.paradise.auth} 包下，随 {@code scanBasePackages=com.paradise} 被扫描，
 * 对所有模块的控制器生效（建议后续迁移至 {@code backend-common}，作为跨模块统一处理）。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result<Void> handleBiz(BizException e) {
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public Result<Void> handleExpiredJwt(ExpiredJwtException e) {
        return Result.fail(ResultCode.UNAUTHORIZED.getCode(), "登录已过期，请重新登录");
    }

    @ExceptionHandler(JwtException.class)
    public Result<Void> handleJwt(JwtException e) {
        return Result.fail(ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : ResultCode.PARAM_ERROR.getMessage();
        return Result.fail(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<Void> handleAccessDenied(AccessDeniedException e) {
        return Result.fail(ResultCode.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleOther(Exception e) {
        return Result.fail(ResultCode.SYSTEM_ERROR);
    }
}
