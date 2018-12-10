package com.wednesday.kanban.service.api;

import com.wednesday.kanban.domain.Auth;

import java.util.List;

/**
 * Created by wyyangyang1 on 2015/1/6.
 */
public interface AuthAuditService {

    List<Auth> findAll();

    /**
     * 按用户ID查询用户权限
     * @param user
     * @return
     */
    public List<Auth> findByUser(String user);
    /**
     * 按空间ID查询用户权限
     * @param sid
     * @return
     */
    public List<Auth> findBySid(Long sid);

    int addAuth(Auth auth);

    int modifyAuth(Auth auth);

    int delAuth(Auth auth);
}
