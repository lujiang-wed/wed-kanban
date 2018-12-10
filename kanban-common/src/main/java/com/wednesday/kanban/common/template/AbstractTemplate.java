package com.wednesday.kanban.common.template;

/**
 * Created by wyzhangdong on 2014/12/9.
 */
public abstract class AbstractTemplate {

    /**
     * 模板属性ID
     */
    private Long id;

    /**
     * 模板属性名
     */
    private String attrLabel;

    /**
     * 模板属性默认值
     */
    private String defaultValue;

    /**
     * 自定义属性用户输入值
     */
    private String inputValue;

    public abstract  String getTemplateName() ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrLabel() {
        return attrLabel;
    }

    public void setAttrLabel(String attrLabel) {
        this.attrLabel = attrLabel;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    @Override
    public String toString() {
        return "AbstractTemplate{" +
                "id=" + id +
                ", attrLabel='" + attrLabel + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", inputValue='" + inputValue + '\'' +
                '}';
    }
}
