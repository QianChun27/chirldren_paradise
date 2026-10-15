package com.paradise.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色（RBAC）。对应表 {@code sys_role}。
 */
@Data
@TableName("sys_role")
public class SysRole {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色代码，如 super_admin / editor / reviewer / operator / viewer */
    private String code;

    private String name;

    private String description;
}
