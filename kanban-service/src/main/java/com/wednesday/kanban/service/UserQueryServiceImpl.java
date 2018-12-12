package com.wednesday.kanban.service;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.service.api.UserQueryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("userQueryService")
public class UserQueryServiceImpl implements UserQueryService {
    @Resource
    private SuperDAO superDAO;

    @Override
    public UserInstance findById(Long id) {
        return superDAO.getObject("UserInstanceMapper.findById", id);
    }

    @Override
    public UserInstance findByUser(UserParam userParam) {
        return superDAO.getObject("UserInstanceMapper.findByUser", userParam);
    }

    @Override
    public Page<UserInstance> finaByPage(UserParam userParam) {
        return superDAO.queryPagination("UserInstanceMapper.findByPage",userParam);
    }

}
