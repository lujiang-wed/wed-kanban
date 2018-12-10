package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class CardTemplateAttr extends BaseDomain {

    /**
    所属模板ID
     */
    private Long templateId;

    /**
    属性label
     */
    private String attrLabel;

    /**
    属性类型
     */
    private String inputType;

    /**
    空间选项
     */
    private String inputOption;

    /**
    默认值
     */
    private String defaultValue;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getAttrLabel() {
        return attrLabel;
    }

    public void setAttrLabel(String attrLabel) {
        this.attrLabel = attrLabel;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputOption() {
        return inputOption;
    }

    public void setInputOption(String inputOption) {
        this.inputOption = inputOption;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "CardTemplateAttr{" +
                "templateId=" + templateId +
                ", attrLabel='" + attrLabel + '\'' +
                ", inputType='" + inputType + '\'' +
                ", inputOption='" + inputOption + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }
}