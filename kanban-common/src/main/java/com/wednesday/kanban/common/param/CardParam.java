package com.wednesday.kanban.common.param;

import com.wednesday.kanban.common.BasePageParam;

import java.util.List;

public class CardParam extends BasePageParam {
    /**
     所属空间
     */
    private Long spaceId;

    /**
     卡片类型
     */
    private Integer cardType;

    /**
     卡片标题
     */
    private String cardTitle;

    /**
     模板ID
     */
    private Long templateId;

    /**
     卡片描述
     */
    private String cardDesc;

    /**
     迭代
     */
    private Integer sprint;

    /**
     生命周期状态
     */
    private String lifeStatus;

    /**
     生命周期结束状态定义
     */
    private String endLife;

    /**
     自定义属性值（保留）
     */
    private String attrValues;

    /**
     * 卡片自定义属性表
     */
    private List<CardAttrParam> cardAttrList;

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getCardDesc() {
        return cardDesc;
    }

    public void setCardDesc(String cardDesc) {
        this.cardDesc = cardDesc;
    }

    public Integer getSprint() { return sprint; }

    public void setSprint(Integer sprint) { this.sprint = sprint; }

    public String getLifeStatus() {
        return lifeStatus;
    }

    public void setLifeStatus(String lifeStatus) {
        this.lifeStatus = lifeStatus;
    }

    public String getEndLife() {
        return endLife;
    }

    public void setEndLife(String endLife) {
        this.endLife = endLife;
    }

    public String getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues;
    }

    public List<CardAttrParam> getCardAttrList() {
        return cardAttrList;
    }

    public void setCardAttrList(List<CardAttrParam> cardAttrList) {
        this.cardAttrList = cardAttrList;
    }

    @Override
    public String toString() {
        return "CardParam{" +
                "spaceId=" + spaceId +
                ", cardType=" + cardType +
                ", cardTitle='" + cardTitle + '\'' +
                ", templateId=" + templateId +
                ", cardDesc='" + cardDesc + '\'' +
                ", sprint='" + sprint + '\'' +
                ", lifeStatus='" + lifeStatus + '\'' +
                ", endLife='" + endLife + '\'' +
                ", attrValues='" + attrValues + '\'' +
                ", cardAttrList=" + cardAttrList +
                '}';
    }
}
