package com.wednesday.kanban.service;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.OpLogQueryParam;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.OpLog;
import com.wednesday.kanban.service.api.OpLogQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("opLogQueryService")
public class OpLogQueryServiceImpl implements OpLogQueryService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public OpLog findOne(Long id) {
        return superDAO.getObject("OpLogMapper.findOne",id);
    }

    @Override
    public Page<OpLog> findByPage(OpLogQueryParam opLogQueryParam) {
        return superDAO.queryPagination("OpLogMapper.findByPage",opLogQueryParam);
    }
}
