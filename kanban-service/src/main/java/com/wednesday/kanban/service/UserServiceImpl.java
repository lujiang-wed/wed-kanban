package com.wednesday.kanban.service;

import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.service.api.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by sushidong on 2015/5/4.
 */
@Component("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private SuperDAO superDAO;

    @Override
    public boolean delUser(Long userId) {
        return superDAO.delete("UserInstanceMapper.deleteUser",userId) > 0;
    }

    @Override
    public boolean updatePwd(UserParam param) {
        return superDAO.update("UserInstanceMapper.updatePwd",param) == 1 ;
    }

    @Override
    public boolean insertUser(UserParam param){ return superDAO.insert("UserInstanceMapper.addUser",param) == 1;}

}
