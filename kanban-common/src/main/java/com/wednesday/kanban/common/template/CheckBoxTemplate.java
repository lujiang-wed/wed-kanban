package com.wednesday.kanban.common.template;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wyzhangdong on 2014/12/9.
 */
public class CheckBoxTemplate extends AbstractTemplate {

    private static final String templateName="checkBox";

    private List<String> defaultCheckList;
    //输入值
    private List<String> inputValues;
    //选择列表
    private List<String> valueList;

    public List<String> getInputValues() {
        return inputValues;
    }

    public void setInputValues(List<String> inputValues) {
        this.inputValues = inputValues;
    }

    @Override
    public void setInputValue(String inputValue) {
        super.setInputValue(inputValue);
        if (StringUtils.isNotEmpty(inputValue)){
            String[] inputList = inputValue.split(",");
            this.setInputValues(Arrays.asList(inputList));
        }

    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public List<String> getDefaultCheckList() {
        return defaultCheckList;
    }

    public void setDefaultCheckList(List<String> defaultCheckList) {
        this.defaultCheckList = defaultCheckList;
    }

    /**
     * 将默认值转换为列表
     * @param defaultValue
     */
    @Override
    public void setDefaultValue(String defaultValue) {
        super.setDefaultValue(defaultValue);
        if (StringUtils.isNotEmpty(defaultValue)){
            String[] defaultList = defaultValue.split(",");
            this.setDefaultCheckList(Arrays.asList(defaultList));
        }
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public String toString() {
        return "CheckBoxTemplate{" +
                "defaultCheckList=" + defaultCheckList +
                ", inputValues=" + inputValues +
                ", valueList=" + valueList +
                '}';
    }
}
