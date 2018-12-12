package com.wednesday.kanban.common.param;

import com.wednesday.kanban.common.BaseEntity;

import java.util.List;

public class TemplateParam extends BaseEntity{

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
    /**
     * 模版属性排序值
     */
    private String templateAttrSort;

    private List<TemplateAttrParam> attrList;

    private  List<TemplateAttrParam> parentAttrList;

    private  List<TemplateAttrParam> allAttrList;

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

    public List<TemplateAttrParam> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<TemplateAttrParam> attrList) {
        this.attrList = attrList;
    }


    public List<TemplateAttrParam> getParentAttrList() {
        return parentAttrList;
    }

    public void setParentAttrList(List<TemplateAttrParam> parentAttrList) {
        this.parentAttrList = parentAttrList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllAttrList(List<TemplateAttrParam> allAttrList) {
        this.allAttrList = allAttrList;
    }
    public List<TemplateAttrParam> getAllAttrList() {
        return allAttrList;
    }

    public String getTemplateAttrSort(){
        return  templateAttrSort;
    }
    public void setTemplateAttrSort(String  templateAttrSort){
        this.templateAttrSort = templateAttrSort;
    }
    @Override
    public String toString() {
        return "TemplateParam{" +
                "id =" + getId()+
                ", cardType=" + cardType +
                ", spaceId=" + spaceId +
                ", parentTemplate=" + parentTemplate +
                ", templateFlag=" + templateFlag +
                ", name='" + name + '\'' +
                ", attrList=" + attrList +
                ", parentAttrList=" + parentAttrList +
                ", allAttrList=" + allAttrList +
                '}';
    }
}
