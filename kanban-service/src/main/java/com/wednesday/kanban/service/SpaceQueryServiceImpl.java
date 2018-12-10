package com.wednesday.kanban.service;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.Space;
import com.wednesday.kanban.common.param.SpaceQueryParam;
import com.wednesday.kanban.service.api.SpaceQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SpaceQueryService")
public class SpaceQueryServiceImpl implements SpaceQueryService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public Space findOne(Long id) {
        return superDAO.getObject("SpaceMapper.findOne",id);
    }

    @Override
    public List<Long> findSonSpace(Long parentSpace) {
        return superDAO.getList("SpaceMapper.findSonSpace",parentSpace);
    }

    @Override
    public Page<Space> findByPage(SpaceQueryParam spaceQueryParam) {
        return superDAO.queryPagination("SpaceMapper.findByPage", spaceQueryParam);
    }
}
