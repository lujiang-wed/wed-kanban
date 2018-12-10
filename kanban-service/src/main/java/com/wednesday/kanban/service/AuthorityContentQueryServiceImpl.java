package com.wednesday.kanban.service;

import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.service.api.AuthorityContentQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("authorityContentQueryService")
public class AuthorityContentQueryServiceImpl implements AuthorityContentQueryService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public String findContentByUser(String user) {
        return superDAO.getObject("AuthorityContentMapper.select",user);
    }

    @Override
    public List<String> findAllUser() {
        return superDAO.getList("AuthorityContentMapper.selectAllUser",null);
    }
}
