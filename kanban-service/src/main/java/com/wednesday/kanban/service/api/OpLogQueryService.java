package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.OpLogQueryParam;
import com.wednesday.kanban.domain.OpLog;

public interface OpLogQueryService {

    /**
     * 按ID查询日志
     * @param id
     * @return
     */
    public OpLog findOne(Long id);

    /**
     * 多条件联合查询日志
     * @param opLogQueryParam
     * @return
     */
    public Page<OpLog> findByPage(OpLogQueryParam opLogQueryParam);
}
