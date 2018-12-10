package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class OpLog extends BaseDomain {

    /**
    操作类型
     */
    private String opType;

    /**
    操作对象（空间ID，卡片ID）
     */
    private Long opObject;

    /**
    操作者
     */
    private String operator;

    /**
    初始状态
     */
    private String initStatus;

    /**
    操作后状态
     */
    private String changeStatus;

    /**
    备注
     */
    private String remark;

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public Long getOpObject() {
        return opObject;
    }

    public void setOpObject(Long opObject) {
        this.opObject = opObject;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(String initStatus) {
        this.initStatus = initStatus;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OpLog{" +
                "opType='" + opType + '\'' +
                ", opObject=" + opObject +
                ", operator='" + operator + '\'' +
                ", initStatus='" + initStatus + '\'' +
                ", changeStatus='" + changeStatus + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}