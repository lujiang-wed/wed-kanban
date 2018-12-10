/**
 * Copyright (c) 2014, wylipengming@jd.com|shouli1990@gmail.com. All rights reserved.
 *
 */
package com.wednesday.kanban.web.Index.Controller.permission.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

public class SessionContextManager {
    private final static Logger logging = LoggerFactory.getLogger(SessionContextManager.class);
    public final static Map<String, SessionContext> sessionContextMap = new HashMap<String, SessionContext>();

    private SessionContextManager(){}

    public static Map<String, SessionContext> getSessionContextMap() {
        return SessionContextManager.sessionContextMap;
    }

    /**
     * session创建的时候把一个SessionContext加入SessionContextManager
     */
    public static void addSessionContent(SessionContext sessionContext) {
        if (sessionContext == null) {
            throw new RuntimeException("sessionContext为空");
        }
        if (SessionContextManager.sessionContextMap.containsKey(sessionContext.getSession().getId())) {
            throw new RuntimeException("session id 重复");
        } else {
            SessionContextManager.sessionContextMap.put(sessionContext.getSession().getId(), sessionContext);
        }
    }

    /**
     * session创建销毁的时候把一个SessionContext从SessionContextManager删除
     */
    public static void removeSessionContent(SessionContext sessionContext) {
        if (sessionContext == null) {
            logging.debug("sessionContext为空");
            return;
        }
        if (SessionContextManager.sessionContextMap.containsKey(sessionContext.getSession().getId())) {
            SessionContextManager.sessionContextMap.remove(sessionContext.getSession().getId());
            logging.info("当前系统在线人数" + SessionContextManager.sessionContextMap.size());
        } else {
            logging.info("不能在sessionContextMap找到特定的sessionContext");
        }

    }

    /**
     * 根据sessionId把一个SessionContext从SessionContextManager删除:
     * 这种情况是直接通过SessionContextManager操作Session的销毁。
     *
     * @return 一个SessionContext实例,这样可以手动控制session的销毁
     */
    public static SessionContext removeSessionContent(String sessionId) {
        if (sessionId == null) {
            logging.debug("sessionId为空");
            return null;
        }
        if (SessionContextManager.sessionContextMap.containsKey(sessionId)) {
            SessionContext sessionContext = sessionContextMap.get(sessionId);
            SessionContextManager.sessionContextMap.remove(sessionId);
            logging.info("当前系统在线人数" + SessionContextManager.sessionContextMap.size());
            return sessionContext;
        } else {
            logging.info("不能在sessionContextMap找到特定的sessionContext");
            return null;
        }

    }

    /**
     * 简单的一个信息SessionContex显示。真正的和用户信息相关的SessionContex遍历需要自己实现
     */
    public static void list() {
        if (SessionContextManager.sessionContextMap != null && SessionContextManager.sessionContextMap.size() > 0) {
            Set<Entry<String, SessionContext>> set = SessionContextManager.sessionContextMap.entrySet();
            Iterator<Entry<String, SessionContext>> iterator = set.iterator();
            logging.info("SessionContext信息遍历:当前session个数为" + sessionContextMap.size());

            while (iterator.hasNext()) {
                Map.Entry<String, SessionContext> entry = iterator.next();
                SessionContext sessionContext = entry.getValue();
                HttpSession session = sessionContext.getSession();
                logging.info("当前用户session为" + session + "   session创建时间是" + session.getCreationTime() + "   已经使用了" + sessionContext.getHourSinceLogin() + "小时" + "  sessionId为:" + session.getId() + "  最近session活动时间：" + session.getLastAccessedTime() + "用户是否登录" + sessionContext.isLogin());
            }
        }
    }
}
