package com.paradise.auth.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口级权限声明：标注在 Controller 方法上，由 {@link RbacInterceptor} 校验。
 *
 * <p>示例：{@code @RequiresPermission(resource = "content_song", action = "publish")}。</p>
 *
 * <p>{@code rowLevel = true} 时表示需要行级校验（如编辑只能操作自己所属内容库的草稿），
 * 行级判定逻辑由业务模块扩展实现，本拦截器仅做标记与接口级前置检查。</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {

    String resource();

    String action();

    boolean rowLevel() default false;
}
