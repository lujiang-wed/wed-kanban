package com.wednesday.kanban.common.result;

import com.wednesday.kanban.common.BaseEntity;

public class CardAttrResult extends BaseEntity {

    /**
     * 模板自定义属性ID
     */
    private Long attrId;
    /**
     * 自定义属性类型
     */
    private String attrType;
    private String attrName;
    private String attrValue;
    private String attrInputOption;

    public CardAttrResult() {
    }

    public CardAttrResult(Long attrId, String attrValue) {
        this.attrId = attrId;
        this.attrValue = attrValue;
    }

    public CardAttrResult(Long attrId, String attrType, String attrName, String attrValue, String inputOption) {
        this.attrId = attrId;
        this.attrType = attrType;
        this.attrName = attrName;
        this.attrValue = attrValue;
        this.attrInputOption = inputOption;
    }

    public String getAttrInputOption(){return attrInputOption;}

    public void setAttrInputOption(String attrInputOption){this.attrInputOption = attrInputOption;}

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    @Override
    public String toString() {
        return "CardAttrResult{" +
                "attrId=" + attrId +
                ", attrType='" + attrType + '\'' +
                ", attrName='" + attrName + '\'' +
                ", attrValue='" + attrValue + '\'' +
                ", attrInputOption ='" + attrInputOption + '\'' +
                '}';
    }
}
