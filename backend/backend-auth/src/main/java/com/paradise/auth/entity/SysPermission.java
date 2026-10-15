package com.paradise.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 权限（RBAC）：资源 × 操作 的原子单元。
 * 对应表 {@code sys_permission}；resource∈{pinyin,hanzi,poem,math,song,asset,system,report,stats}，
 * action∈{create,read,update,delete,submit,review,publish,unpublish,import,configure}。
 */
@Data
@TableName("sys_permission")
public class SysPermission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String resource;

    private String action;

    private String description;
}
