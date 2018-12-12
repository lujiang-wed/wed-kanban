package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.UserAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.service.api.UserQueryService;
import com.wednesday.kanban.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("UserAuditBiz")
public class UserAuditBizImpl implements UserAuditBiz {

    private static final Logger logger = LoggerFactory.getLogger(UserAuditBizImpl.class);

    @Resource
    private UserQueryService userQueryService;

    @Resource
    private UserService userService;

    @Override
    public UserInstance findById(Long id) {
        if (null == id){
            logger.error("参数异常");
            return null;
        }

        UserInstance userInstance = userQueryService.findById(id);
        if (null == userInstance){
            logger.error("无此ID:{}对应的数据",id);
            return null;
        }

        return userInstance;
    }

    @Override
    public UserInstance findByUser(UserParam userParam) {
        if (null == userParam || null == userParam.getUserId() || null == userParam.getUserPwd()){
            logger.error("参数异常");
            return null;
        }

        UserInstance userInstance = userQueryService.findByUser(userParam);
        if (null == userInstance){
            logger.error("用户名和密码不匹配,userId:{}",userParam.getUserId());
            return null;
        }

        return userQueryService.findByUser(userParam);
    }

    @Override
    public Page<UserInstance> findByPage(UserParam userParam) {
        if(null == userParam) {
            logger.error("参数异常");
            return null;
        }

        Page<UserInstance> resultPage = userQueryService.finaByPage(userParam);

        return resultPage;
    }

    @Override
    public String updatePwd(UserParam userParam) {
        if (null == userParam || null == userParam.getId() || null == userParam.getUserPwd()
                || null == userParam.getOldPwd()){
            logger.error("参数异常");
            return "FAIL";
        }

        boolean result = userService.updatePwd(userParam);

        if(result) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    @Override
    public String delUser(Long userId) {
        boolean result = userService.delUser(userId);

        if(result) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    @Override
    public String addUser(UserParam userParam)
    {
        if (null == userParam || null == userParam.getUserId() || null == userParam.getUserPwd() || null == userParam.getUserName()){
            logger.error("参数异常");
            return "FAIL";
        }
        boolean result = userService.insertUser(userParam);

        if(result)
            return "SUCCESS";
        else
            return "FAIL";
    }
}
