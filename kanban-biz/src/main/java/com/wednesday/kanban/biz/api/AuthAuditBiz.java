package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.domain.Auth;

import java.util.List;

public interface AuthAuditBiz {
    public List<Auth> findAll();

    public List<Auth> findByUser(String user);
    public List<Auth> findBySid(Long sid);

    public int addAuth(Auth auth);
    public int modifyAuth(Auth auth);
    public int delAuth(Auth auth);
}
