package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class CardTemplate extends BaseDomain {

    /**
    卡片类型
     */
    private Integer cardType;

    /**
    所属空间
     */
    private Long spaceId;

    /**
    父模板
     */
    private Long parentTemplate;

    /**
    软删除（保留）
     */
    private Integer templateFlag;

    /**
     * 模版名称
     */
    private String name;

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getParentTemplate() {
        return parentTemplate;
    }

    public void setParentTemplate(Long parentTemplate) {
        this.parentTemplate = parentTemplate;
    }

    public Integer getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(Integer templateFlag) {
        this.templateFlag = templateFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CardTemplate{" +
                "cardType=" + cardType +
                ", spaceId=" + spaceId +
                ", parentTemplate=" + parentTemplate +
                ", templateFlag=" + templateFlag +
                ", name='" + name + '\'' +
                '}';
    }
}