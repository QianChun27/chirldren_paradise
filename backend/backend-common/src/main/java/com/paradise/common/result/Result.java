package com.paradise.common.result;

import lombok.Data;

import java.util.UUID;

/**
 * 统一响应体：{ code, message, data, traceId }。
 * 全工程 API 返回结构一致（见后端架构文档 §2）。
 */
@Data
public class Result<T> {

    private int code;
    private String message;
    private T data;
    private String traceId;

    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        r.setData(data);
        r.setTraceId(UUID.randomUUID().toString());
        return r;
    }

    public static <T> Result<T> fail(ResultCode code) {
        return fail(code.getCode(), code.getMessage());
    }

    public static <T> Result<T> fail(int code, String message) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(message);
        r.setTraceId(UUID.randomUUID().toString());
        return r;
    }
}
