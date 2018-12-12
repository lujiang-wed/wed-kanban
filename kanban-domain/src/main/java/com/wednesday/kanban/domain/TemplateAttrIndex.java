package com.wednesday.kanban.domain;

public class TemplateAttrIndex extends BaseDomain {


    /**
     * 所属模板ID
     */
    private Long templateId;

    /**
     * 属性id
     */
    private Long attrId;

    /**
     * 属性名称
     */
    private String attrLabel;

    /**
     * 属性位置
     */
    private Long index;

    /**
     * 属性显示与否
     */
    private int show = 1;

    /**
     * 记录状态
     */
    private int status = 1;


    public Long getTemplateId() {
        return templateId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrLabel() {
        return attrLabel;
    }

    public void setAttrLabel(String attrLabel) {
        this.attrLabel = attrLabel;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }


    @Override
    public String toString() {
        return "TemplateAttrIndex{" +
                "templateId=" + templateId +
                ", attrId=" + attrId +
                ", attrLabel='" + attrLabel + '\'' +
                ", index=" + index +
                ", show=" + show +
                ", status=" + status +
                '}';
    }
}

