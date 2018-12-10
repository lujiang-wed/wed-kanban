package com.wednesday.kanban.biz.advice;

import com.wednesday.kanban.biz.api.OpLogAuditBiz;
import com.wednesday.kanban.common.code.OpLogTypeEnum;
import com.wednesday.kanban.domain.OpLog;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Component
@Aspect
public class LogAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    @Resource
    private OpLogAuditBiz opLogAuditBiz;

    /**
     * 添加操作方法切入点
     */
    @Pointcut("execution(* com.wednesday.kanban.web.Index.Controller.*.insert*(..))")
    public void insertServiceCall() { }

    /**
     * 修改操作方法切入点
     */
    @Pointcut("execution(* com.wednesday.kanban.web.Index.Controller.*.update*(..))")
    public void updateServiceCall() { }

    /**
     * 删除操作方法切入点
     */
    @Pointcut("execution(* com.wednesday.kanban.web.Index.Controller.*.delete*(..))")
    public void deleteServiceCall() { }


    //@AfterReturning(value = "insertServiceCall()",argNames="rtv", returning="rtv")
    public void insert(JoinPoint joinPoint){
        //TODO:获取状态变更
        this.writeLog(joinPoint,"","");
    }

    //@AfterReturning("updateServiceCall() && args(joinPoint)")
    public void update(JoinPoint joinPoint){
        //TODO:获取状态变更
        this.writeLog(joinPoint,"","");
    }

    //@AfterReturning("deleteServiceCall() && args(joinPoint)")
    public void delete(JoinPoint joinPoint){
        //TODO:获取状态变更
        this.writeLog(joinPoint,"","");
    }


    private String getOpType(String methodName){
        if (null == methodName){
            logger.info("切点方法为空");
        }

        String opType = null;
        if (methodName.contains("space")){
            opType = OpLogTypeEnum.SPACE.getCode();
        }else if (methodName.contains("cardAttr")){
            opType = OpLogTypeEnum.CARD_ATTR.getCode();
        }else if (methodName.contains("cardTemplate")){
            opType = OpLogTypeEnum.CARD_TEMPLATE.getCode();
        }else if (methodName.contains("card")){
            opType = OpLogTypeEnum.CARD.getCode();
        }else {
            opType = OpLogTypeEnum.OTHERS.getCode();
        }

        return opType;
    }

    public void writeLog(JoinPoint joinPoint,String initStatus,String changeStatus){
        //TODO:获取登录信息

        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //操作类型
        String opType = this.getOpType(methodName);
        //获取ID
        Long callerId = this.getCallerId(joinPoint.getArgs());

        OpLog opLog = new OpLog();
        opLog.setOpType(opType);
        opLog.setId(callerId);
        opLog.setRemark(methodName);
        opLog.setInitStatus(initStatus);
        opLog.setChangeStatus(changeStatus);

        opLogAuditBiz.addOpLog(opLog);
    }

    public Long getCallerId(Object[] args){
        if (args == null) {
            return null;
        }
        // 遍历参数对象
        for (Object info : args) {

            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();

            // 遍历方法，判断get方法
            for (Method method : methods) {

                String methodName = method.getName();

                if (methodName.indexOf("getId") == -1) {// 不是getId方法
                    continue;// 不处理
                }

                Object value = null;
                try {
                    // 调用get方法，获取返回值
                    value = method.invoke(info);
                    if (null == value || StringUtils.isNumeric(value.toString())){
                        logger.info("获取到的ID为空");
                        return null;
                    }else {
                        return Long.parseLong(value.toString());
                    }

                } catch (Exception e) {
                    logger.info("获取ID异常");
                    return null;
                }

            }
        }
        return null;
    }
}
