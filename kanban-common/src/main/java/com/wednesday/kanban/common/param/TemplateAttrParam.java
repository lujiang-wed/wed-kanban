package com.wednesday.kanban.common.param;

import com.wednesday.kanban.common.BaseEntity;

/**
 * Created by wyzhangdong on 2014/12/10.
 */
public class TemplateAttrParam extends BaseEntity{

    /**
     所属模板ID
     */
    private Long templateId;

    /**
     属性label
     */
    private String attrLabel;

    /**
     属性label的位置
     */
    private Long attrLabelIndex = Long.valueOf(0);
    /**
     * 属性是否展示
     */
    private int isShow = 1;

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

    public void setIsShow(int isShow){
        this.isShow = isShow;
    }

    public  int getIsShow(){
        return  isShow;
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

    public void setAttrLabelIndex(Long attrLabelIndex){
        this.attrLabelIndex = attrLabelIndex;
    };

    public Long getAttrLabelIndex(){
        return attrLabelIndex;
    };
    @Override
    public String toString() {
        return "TemplateAttrParam{" +
                "id=" + getId() +
                ", templateId=" + templateId +
                ", attrLabel='" + attrLabel + '\'' +
                ", inputType='" + inputType + '\'' +
                ", inputOption='" + inputOption + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", attrLabelIndex='" + attrLabelIndex + '\'' +
                ", isShow='" + isShow + '\'' +
                '}';
    }
}
