package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.domain.AuthorityContent;

public interface AuthorityContentBiz {

    /**
     * 新增用户具体权限（JSON）
     * @param authorityContent
     */
    public void addContent(AuthorityContent authorityContent);

    /**
     * 查询用户JSON权限
     * @param user
     * @return
     */
    public String findContentByUser(String user);

    /**
     * 空间信息变更（或删除）时，删除掉包含此空间的JSON串
     * @param spaceId
     */
    public void deleteContentBySpaceId(Long spaceId);

    /**
     * 通过用户名删除权限JSON串
     * @param user
     */
    public void deleteContentByUser(String user);

}
