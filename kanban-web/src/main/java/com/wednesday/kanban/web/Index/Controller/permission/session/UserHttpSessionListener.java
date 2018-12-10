package com.wednesday.kanban.web.Index.Controller.permission.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserHttpSessionListener implements HttpSessionListener {
    private final static Logger logging = LoggerFactory.getLogger(UserHttpSessionListener.class);

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        // 登陆时需要在session中设置登陆状态
        SessionContext sessionContext = new SessionContext();
        sessionContext.setSession(session);
        sessionContext.setLoginTime(System.currentTimeMillis());
        sessionContext.setAllowedUseTheSystemByAdmin(true);
        sessionContext.setLogin();

        SessionContextManager.addSessionContent(sessionContext);
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        SessionContext sessionContext = SessionContextManager.sessionContextMap.get(session.getId());
        if (sessionContext == null) {
            logging.info("不能在sessionContextMap找到特定的sessionContext");
        } else {
            SessionContextManager.removeSessionContent(sessionContext);
            sessionContext = null;//回收
        }
    }
}
