package com.wednesday.kanban.service;

import com.wednesday.kanban.service.api.DaoTestService;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.Space;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("daoTestService")
public class DaoTestServiceImpl implements DaoTestService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public void insert(Space space) {
        superDAO.insert("SpaceMapper.insertSelective",space);
    }

    @Override
    public Space select(Long id) {
        return superDAO.getObject("SpaceMapper.selectByPrimaryKey",id);
    }
}
