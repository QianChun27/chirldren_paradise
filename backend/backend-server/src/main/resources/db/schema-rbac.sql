-- ============================================================
-- 儿童学习乐园 后端 · RBAC 权限模块 DDL（backend-auth）
-- 对应实体：SysUser / SysRole / SysPermission / SysRolePermission / SysUserRole
-- 说明：逻辑删除字段 deleted（0 未删 / 1 已删）；本文件仅建表，种子数据见下方可选段。
-- ============================================================

CREATE TABLE IF NOT EXISTS sys_user (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    username     VARCHAR(64)  NOT NULL,
    password_hash VARCHAR(128) NOT NULL,
    nickname     VARCHAR(64),
    status       TINYINT      NOT NULL DEFAULT 1 COMMENT '1=启用 0=禁用',
    created_at   DATETIME     DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted      TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

CREATE TABLE IF NOT EXISTS sys_role (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    code         VARCHAR(64)  NOT NULL,
    name         VARCHAR(64)  NOT NULL,
    description  VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色';

CREATE TABLE IF NOT EXISTS sys_permission (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    resource     VARCHAR(32)  NOT NULL COMMENT 'pinyin|hanzi|poem|math|song|asset|system|report|stats',
    action       VARCHAR(32)  NOT NULL COMMENT 'create|read|update|delete|submit|review|publish|unpublish|import|configure',
    description  VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_res_action (resource, action)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限（资源×操作）';

CREATE TABLE IF NOT EXISTS sys_role_permission (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    role_id      BIGINT       NOT NULL,
    permission_id BIGINT      NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rp (role_id, permission_id),
    KEY idx_rp_permission (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联';

CREATE TABLE IF NOT EXISTS sys_user_role (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    user_id      BIGINT       NOT NULL,
    role_id      BIGINT       NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_ur (user_id, role_id),
    KEY idx_ur_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联';

-- ============================================================
-- 可选种子数据（首次部署时执行）：超级管理员 + 全量权限
-- 注意：password_hash 须为 BCrypt("admin123")，部署前请替换为真实哈希。
-- 可用：new BCryptPasswordEncoder().encode("admin123")
-- ============================================================
-- INSERT INTO sys_user (username, password_hash, nickname, status)
-- VALUES ('admin', '$2a$10$REPLACE_WITH_BCRYPT_HASH', '超级管理员', 1);
--
-- INSERT INTO sys_role (code, name, description)
-- VALUES ('super_admin', '超级管理员', '全量权限 + 用户/角色管理 + 系统配置');
--
-- INSERT INTO sys_permission (resource, action, description) VALUES
--   ('system','read','系统只读'), ('system','configure','系统配置'),
--   ('content_song','read','儿歌只读'), ('content_song','create','儿歌新增'),
--   ('content_song','update','儿歌修改'), ('content_song','delete','儿歌删除'),
--   ('content_song','submit','儿歌送审'), ('content_song','review','儿歌审核'),
--   ('content_song','publish','儿歌发布'), ('content_song','unpublish','儿歌下架'),
--   ('content_song','import','儿歌导入'), ('content_song','configure','儿歌配置');
-- （其余 content_* 资源同理，略）
--
-- -- 将全部权限赋予 super_admin，并将 admin 用户绑定该角色
-- INSERT INTO sys_role_permission (role_id, permission_id)
--   SELECT r.id, p.id FROM sys_role r, sys_permission p WHERE r.code='super_admin';
-- INSERT INTO sys_user_role (user_id, role_id)
--   SELECT u.id, r.id FROM sys_user u, sys_role r
--   WHERE u.username='admin' AND r.code='super_admin';
