package com.wednesday.kanban.web.Index.Controller.permission.listener;

import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.permission.CookieUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("globalInterceptor")
public class GlobalInterceptor extends HandlerInterceptorAdapter {
    private final static String userName = "userName";
    private final static String userId   = "userId";
    private final static String flag     = "onLine";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (CookieUtil.parseToken(CookieUtil.getValue(request,CookieUtil.LOGIN_IDENTITY)) != null) {
            setLoginInitMes(request, response);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 设置登录信息
     *
     * @param request
     * @param response
     */
    public static void setLoginInitMes(HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getValue(request, CookieUtil.LOGIN_IDENTITY) != null) {
            UserInstance userInstance = CookieUtil.parseToken(CookieUtil.getValue(request, CookieUtil.LOGIN_IDENTITY));
            if (userInstance != null) {
                request.setAttribute(userName, userInstance.getUserName());
                request.setAttribute(userId, userInstance.getUserId());
                request.setAttribute(flag, "true");
            }
        }
    }
}
