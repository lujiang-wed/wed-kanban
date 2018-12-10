package com.wednesday.kanban.common.param;

import com.wednesday.kanban.common.BasePageParam;

public class CardAttrParam extends BasePageParam {
    /**
     所属卡片ID
     */
    private Long cardId;

    /**
     所属空间
     */
    private Long spaceId;



    /**
     所属模板
     */
    private Long templateId;
    /**
     卡片类型
     */
    private Integer cardType;

    /**
     所属自定义属性
     */
    private Long attrId;

    /**
     自定义属性值
     */
    private String attrValue;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

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

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "CardAttrParam{" +
                "cardId=" + cardId +
                ", spaceId=" + spaceId +
                ", cardType=" + cardType +
                ", attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                '}';
    }
}
