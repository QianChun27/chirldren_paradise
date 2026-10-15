package com.paradise.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paradise.auth.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统用户 Mapper。
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /** 用户拥有的角色代码列表。 */
    @Select("SELECT r.code FROM sys_role r " +
            "JOIN sys_user_role ur ON ur.role_id = r.id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectRoleCodesByUserId(@Param("userId") Long userId);

    /** 用户拥有的权限表达式列表，形如 {@code content_song:publish}（资源:操作）。 */
    @Select("SELECT CONCAT(p.resource, ':', p.action) FROM sys_permission p " +
            "JOIN sys_role_permission rp ON rp.permission_id = p.id " +
            "JOIN sys_user_role ur ON ur.role_id = rp.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> selectPermissionCodesByUserId(@Param("userId") Long userId);
}
