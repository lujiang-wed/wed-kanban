package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.OpLogQueryParam;
import com.wednesday.kanban.common.result.OpLogResult;
import com.wednesday.kanban.domain.OpLog;

public interface OpLogAuditBiz {

    public Boolean addOpLog(OpLog opLog);

    public OpLogResult findOne(Long id);

    public Page<OpLogResult> findByPage(OpLogQueryParam opLogQueryParam);

}
