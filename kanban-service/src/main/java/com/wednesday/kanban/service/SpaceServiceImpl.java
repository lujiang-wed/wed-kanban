package com.wednesday.kanban.service;

import com.wednesday.kanban.service.api.SpaceService;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.Space;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("spaceService")
public class SpaceServiceImpl implements SpaceService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public Long addSpace(Space space) {
        superDAO.insert("SpaceMapper.addSpace",space);
        return space.getId();
    }

    @Override
    public void modifySpace(Space space) {
        superDAO.update("SpaceMapper.modifySpace",space);
    }

    @Override
    public void deleteSpace(Long id) {
        superDAO.update("SpaceMapper.deleteSpace",id);
    }
}
