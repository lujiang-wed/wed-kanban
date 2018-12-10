package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class TemplateAttrSort extends BaseDomain {

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateAttrSort() {
        return templateAttrSort;
    }

    public void setTemplateAttrSort(String templateAttrSort) {
        this.templateAttrSort = templateAttrSort;
    }

    /**
    所属模板ID
     */
    private Long templateId;

    /**
    排序字符
     */
    private String templateAttrSort;

    /**
    属性类型
     */

    /**
    默认值
     */

    @Override
    public String toString() {
        return "CardTemplateAttr{" +
                "templateId=" + templateId +
                ", templateAttrSort='" + templateAttrSort + '\'' +
                '}';
    }
}