package com.wednesday.kanban.common.param;

import com.wednesday.kanban.common.BasePageParam;

import java.util.List;

public class CardAttrQueryParam extends BasePageParam {
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
     * 自定义属性 查询参数
     */
    private List<CardAttrParam> cardAttrList;

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

    public List<CardAttrParam> getCardAttrList() {
        return cardAttrList;
    }

    public void setCardAttrList(List<CardAttrParam> cardAttrList) {
        this.cardAttrList = cardAttrList;
    }
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "CardAttrQueryParam{" +
                "cardId=" + cardId +
                ", spaceId=" + spaceId +
                ", cardType=" + cardType +
                ", cardAttrList=" + cardAttrList +
                '}';
    }
}
