package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class CardInstance extends BaseDomain {

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

    @Override
    public String toString() {
        return "CardInstance{" +
                "spaceId=" + spaceId +
                ", cardType=" + cardType +
                ", cardTitle='" + cardTitle + '\'' +
                ", templateId=" + templateId +
                ", cardDesc='" + cardDesc + '\'' +
                ", sprint='" + sprint + '\'' +
                ", lifeStatus='" + lifeStatus + '\'' +
                ", endLife='" + endLife + '\'' +
                ", attrValues='" + attrValues + '\'' +
                '}';
    }
}