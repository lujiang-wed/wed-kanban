package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.AuthAuditBiz;
import com.wednesday.kanban.biz.api.AuthorityContentBiz;
import com.wednesday.kanban.domain.Auth;
import com.wednesday.kanban.service.api.AuthAuditService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("authAuditBiz")
public class AuthAuditBizImpl implements AuthAuditBiz {
    @Resource
    AuthAuditService authAuditService;

    @Resource
    private AuthorityContentBiz authorityContentBiz;
    /**
     * 查询所有sid
     * @return
     */
    public List<Auth> findAll() {
        return authAuditService.findAll();
    }

    @Override
    public List<Auth> findByUser(String user) {
        return authAuditService.findByUser(user);
    }

    @Override
    public List<Auth> findBySid(Long sid) {
        return authAuditService.findBySid(sid);
    }

    @Override
    public int addAuth(Auth auth) {
        int temp = authAuditService.addAuth(auth);
        //增加权限时，需要删除权限JSON串中，所有包含此空间的字段，当其再次读取权限列表时，会重新填充权限JSON表
        if (null != auth.getSid()){
            authorityContentBiz.deleteContentByUser(auth.getUser());
        }
        return temp;
    }

    @Override
    public int modifyAuth(Auth auth) {
        int temp = authAuditService.modifyAuth(auth);
        //增加权限时，需要删除权限JSON串中，所有包含此空间的字段，当其再次读取权限列表时，会重新填充权限JSON表
        if (null != auth.getSid()){
            authorityContentBiz.deleteContentByUser(auth.getUser());
        }
        return temp;
    }

    @Override
    public int delAuth(Auth auth) {
        int temp = authAuditService.delAuth(auth);
        //增加权限时，需要删除权限JSON串中，所有包含此空间的字段，当其再次读取权限列表时，会重新填充权限JSON表
        if (null != auth.getSid()){
            authorityContentBiz.deleteContentByUser(auth.getUser());
        }
        return temp;
    }
}
