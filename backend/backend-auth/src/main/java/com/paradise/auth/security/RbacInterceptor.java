package com.paradise.auth.security;

import com.paradise.common.exception.BizException;
import com.paradise.common.result.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 接口级 RBAC 拦截器：校验 Controller 方法上的 {@link RequiresPermission}。
 * 结合 JwtFilter 写入的 {@link LoginUser}，判断当前用户是否持有 {@code resource:action} 权限。
 *
 * <p>行级校验（rowLevel=true，如编辑仅能操作自己内容库的草稿）由业务模块在 Service 层实现；
 * 本拦截器负责接口级前置拦截（无登录态→401，无权限→403）。</p>
 */
@Component
public class RbacInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        RequiresPermission required = handlerMethod.getMethodAnnotation(RequiresPermission.class);
        if (required == null) {
            return true;
        }

        LoginUser loginUser = (LoginUser) request.getAttribute("loginUser");
        if (loginUser == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }

        String needed = required.resource() + ":" + required.action();
        if (!loginUser.getPermissions().contains(needed)) {
            throw new BizException(ResultCode.FORBIDDEN);
        }
        return true;
    }
}
