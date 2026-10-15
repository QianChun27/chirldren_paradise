package com.paradise.common.result;

import lombok.Data;

import java.util.List;

/**
 * 统一分页响应体：{ list, total, page, size }（见后端架构文档 §2）。
 */
@Data
public class PageResult<T> {

    private List<T> list;
    private long total;
    private long page;
    private long size;

    public static <T> PageResult<T> of(List<T> list, long total, long page, long size) {
        PageResult<T> r = new PageResult<>();
        r.setList(list);
        r.setTotal(total);
        r.setPage(page);
        r.setSize(size);
        return r;
    }
}
