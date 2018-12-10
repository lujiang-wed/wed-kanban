package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.OpLogAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.OpLogQueryParam;
import com.wednesday.kanban.common.result.OpLogResult;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.domain.OpLog;
import com.wednesday.kanban.service.api.OpLogQueryService;
import com.wednesday.kanban.service.api.OpLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component("opLogAuditBiz")
public class OpLogAuditBizImpl implements OpLogAuditBiz {

    private static final Logger logger = LoggerFactory.getLogger(OpLogAuditBiz.class);

    @Resource
    private OpLogService opLogService;
    @Resource
    private OpLogQueryService opLogQueryService;

    @Override
    public Boolean addOpLog(OpLog opLog) {
        if (null == opLog ){
            logger.warn("日志参数为空");
            return Boolean.FALSE;
        }
        opLogService.addOpLog(opLog);
        return Boolean.TRUE;
    }

    @Override
    public OpLogResult findOne(Long id) {
        if (null == id){
            logger.warn("id为空");
            return null;
        }
        OpLog opLog = opLogQueryService.findOne(id);
        return BeanConverter.convertEntity2DTO(opLog,OpLogResult.class);
    }

    @Override
    public Page<OpLogResult> findByPage(OpLogQueryParam opLogQueryParam) {
        if (null == opLogQueryParam){
            logger.warn("日志查询参数为空");
            return null;
        }
        Page<OpLog> opLogPage = opLogQueryService.findByPage(opLogQueryParam);

        if (null == opLogPage){
            logger.warn("未知异常");
            return null;
        }

        Page<OpLogResult> opLogResultPage = new Page<OpLogResult>();
        opLogResultPage.setTotalCount(opLogPage.getTotalCount());
        opLogResultPage.setPageNo(opLogPage.getPageNo());
        opLogResultPage.setPageSize(opLogPage.getPageSize());

        List<OpLogResult> opLogResults = new LinkedList<OpLogResult>();
        for (OpLog opLog : opLogPage.getData()){
            OpLogResult opLogResult = BeanConverter.convertEntity2DTO(opLog,OpLogResult.class);
            opLogResults.add(opLogResult);
        }
        opLogResultPage.setData(opLogResults);
        return opLogResultPage;
    }
}
