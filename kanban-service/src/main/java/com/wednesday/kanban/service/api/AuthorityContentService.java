package com.wednesday.kanban.service.api;

import com.wednesday.kanban.domain.AuthorityContent;

public interface AuthorityContentService {

    /**
     * 新增用户权限内容（JSON）
     * @param authorityContent
     */
    public void addContent(AuthorityContent authorityContent);

    /**
     * 删除用户权限记录
     * @param user
     */
    public void deleteRecord(String user);

}
