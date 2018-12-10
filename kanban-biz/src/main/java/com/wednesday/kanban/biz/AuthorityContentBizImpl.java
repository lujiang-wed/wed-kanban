package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.AuthorityContentBiz;
import com.wednesday.kanban.domain.Auth;
import com.wednesday.kanban.domain.AuthorityContent;
import com.wednesday.kanban.service.api.AuthAuditService;
import com.wednesday.kanban.service.api.AuthorityContentQueryService;
import com.wednesday.kanban.service.api.AuthorityContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("authorityContentBiz")
public class AuthorityContentBizImpl implements AuthorityContentBiz {

    public static final Logger logger = LoggerFactory.getLogger(AuthorityContentBizImpl.class);

    @Resource
    private AuthorityContentService authorityContentService;
    @Resource
    private AuthorityContentQueryService authorityContentQueryService;
    @Resource
    private AuthAuditService authAuditService;

    @Override
    public void addContent(AuthorityContent authorityContent) {
        if (null == authorityContent || null == authorityContent.getUser() || null == authorityContent.getContent()){
            logger.info("参数为空");
        }
        authorityContentService.addContent(authorityContent);
    }

    @Override
    public String findContentByUser(String user) {
        if (null == user){
            logger.info("用户ID为空");
        }
        return authorityContentQueryService.findContentByUser(user);
    }

    @Override
    public void deleteContentBySpaceId(Long spaceId) {
        //查询所有用户列表
        List<String> userList = authorityContentQueryService.findAllUser();
        for (String user : userList){
            //查询用户拥有权限的空间
            List<Auth> authList = authAuditService.findByUser(user);

            //当用户包含此空间权限时，删除权限JSON串
            for (Auth auth : authList){
                if (auth.getSid() == spaceId){
                    authorityContentService.deleteRecord(user);
                    break;
                }
            }
        }
    }

    @Override
    public void deleteContentByUser(String user) {
        authorityContentService.deleteRecord(user);
    }
}
