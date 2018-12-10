package com.wednesday.kanban.service.api;

import com.wednesday.kanban.domain.OpLog;

public interface OpLogService {

    /**
     * 添加操作日志
     * @param opLog
     */
    public void addOpLog(OpLog opLog);

}
