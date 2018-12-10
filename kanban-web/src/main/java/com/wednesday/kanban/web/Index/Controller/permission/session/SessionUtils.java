/**
 * Copyright (c) 2014, wylipengming@jd.com|shouli1990@gmail.com. All rights reserved.
 *
 */
package com.wednesday.kanban.web.Index.Controller.permission.session;

import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.common.CookieUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    //==================session=====================//
    private final static String _SESSION_LOGIN = "_SESSION_LOGIN";
    //==================cookie=====================//
    private final static String _NAME_COOKIES = "_NAME_COOKIES";
    private final static String _UID_COOKIES = "_UID_COOKIES";
    private final static String _SID_COOKIES = "_SID_COOKIES";

    /**
     * 登录
     * @param request
     * @param response
     * @param userParam
     */
    public static void login(HttpServletRequest request,HttpServletResponse response,UserInstance userParam) {
        HttpSession session = request.getSession();
        session.setAttribute(_SESSION_LOGIN,userParam);
        //add cookies
        CookieUtils.setCookie(response,_NAME_COOKIES,userParam.getUserName());
        CookieUtils.setCookie(response,_UID_COOKIES,userParam.getUserId());
        CookieUtils.setCookie(response,_SID_COOKIES,session.getId());
    }

    /**
     * 登出
     * @param request
     * @param response
     */
    public static void logOut(HttpServletRequest request,HttpServletResponse response) {
        //del session
        HttpSession session = request.getSession();
        if(session != null) {
            SessionContext sessionContext = SessionContextManager.removeSessionContent(session.getId());
            if(session != null) {
                sessionContext.getSession().invalidate();
            }
        }
        //del cookie
        CookieUtils.setCookie(response,_NAME_COOKIES,"");
        CookieUtils.setCookie(response,_UID_COOKIES,"");
        CookieUtils.setCookie(response,_SID_COOKIES,"");
    }

    public static UserInstance getUser(HttpServletRequest request) {

        HttpSession session = request.getSession();
        return (UserInstance) session.getAttribute(_SESSION_LOGIN);
//
//        UserInstance instance = new UserInstance();
//
//        String name = CookieUtils.getCookie(request,_NAME_COOKIES);
//        String userId = CookieUtils.getCookie(request,_UID_COOKIES);
//
//        if(StringUtils.isNotBlank(userId)) {
//
//        }
//
//        instance.setUserId(userId);
//        instance.setUserName(name);
//        return instance;
    }

    /**
     * 是否在线
     * @param request
     * @return
     */
    public static boolean isOnline(HttpServletRequest request) {
        String sessionId = CookieUtils.getCookie(request,_SID_COOKIES);
        if(StringUtils.isBlank(sessionId)) {
            UserInstance userInstance = (UserInstance) request.getSession().getAttribute(_SESSION_LOGIN);
            return userInstance != null;
        } else {
            return SessionContextManager.getSessionContextMap().containsKey(sessionId);
        }
    }
}
