package com.wednesday.kanban.web.Index.Controller.permission.session;

import javax.servlet.http.HttpSession;
import java.util.Calendar;

public class SessionContext {
    /**
     * 这个常量需要对外配置
     */
    public static String UserLoginState = "_#_userloginstate";
    /**
     * 当前用户session
     */
    private HttpSession session = null;
    /**
     * 登录后状态置为true
     */
    private boolean isLogined = false;
    /**
     * 管理员进行对用户安全进行管理
     */
    private boolean isAllowedUseTheSystemByAdmin = true;
    /**
     * 登陆时间：可以约定过期时间,如果超过过期时间则session由系统销毁.
     */
    private long loginTime = 0L;

    @SuppressWarnings("unused")
    private double hourSinceLogin = 0;

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    /**
     * 在浏览jsp过程中会产生一个session.为了便于系统登录管理我们可以向session中加入一个属性,这样便可以轻松的区分是否登录
     */
    public void setLogin() {
        if (session == null) {
            throw new RuntimeException("session 为空");
        }
        /**
         * session中的登陆属性来判断SessionContext是否登陆
         */
//        if (session.getAttribute(SessionContext.UserLoginState) != null && (Boolean) session.getAttribute(SessionContext.UserLoginState) == true) {
//            this.isLogined = true;
//        } else {
//            this.isLogined = false;
//        }
        session.setAttribute(SessionContext.UserLoginState,true);
    }

    public boolean isLogin() {
        if (session == null) {
            throw new RuntimeException("session 为空");
        }
        /**
         * session中的登陆属性来判断SessionContext是否登陆
         */
        if (session.getAttribute(SessionContext.UserLoginState) != null && (Boolean) session.getAttribute(SessionContext.UserLoginState) == true) {
            this.isLogined = true;
        } else {
            this.isLogined = false;
        }
        return this.isLogined;
    }

    public void setAllowedUseTheSystemByAdmin(boolean isAllowedUseTheSystemByAdmin) {
        this.isAllowedUseTheSystemByAdmin = isAllowedUseTheSystemByAdmin;
        if (this.isAllowedUseTheSystemByAdmin == false) {
            this.session.invalidate();
        }
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public long getLoginTime() {
        return loginTime;
    }

    /**
     * 获取登录后时间长度,单位是小时
     *
     * @return
     */
    public double getHourSinceLogin() {
        return (double) ((Calendar.getInstance().getTimeInMillis() - this.loginTime) / (1000 * 60 * 60 * 1.0d));
    }
}
