package com.wednesday.kanban.service.api;

import java.util.List;

public interface AuthorityContentQueryService {

    /**
     * 获取用户JSON权限
     * @param user
     * @return
     */
    public String findContentByUser(String user);

    /**
     * 获取所有用户
     * @return
     */
    public List<String> findAllUser();
}
