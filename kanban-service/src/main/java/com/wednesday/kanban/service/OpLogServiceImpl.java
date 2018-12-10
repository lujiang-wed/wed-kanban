package com.wednesday.kanban.service;

import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.OpLog;
import com.wednesday.kanban.service.api.OpLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("opLogService")
public class OpLogServiceImpl implements OpLogService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public void addOpLog(OpLog opLog) {
        superDAO.insert("OpLogMapper.insert",opLog);
    }
}
