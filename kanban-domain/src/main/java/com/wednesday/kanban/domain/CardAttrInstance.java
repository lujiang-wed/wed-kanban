package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class CardAttrInstance extends BaseDomain {

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
        return "CardAttrInstance{" +
                "cardId=" + cardId +
                ", spaceId=" + spaceId +
                ", cardType=" + cardType +
                ", attrId=" + attrId +
                ", attrValue='" + attrValue + '\'' +
                '}';
    }
}