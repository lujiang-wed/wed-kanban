package com.wednesday.kanban.common.template;

import java.util.List;

public class SelectTemplate extends AbstractTemplate {
    private static final String templateName="select";
    //选择列表
   private List<String> valueList;

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }
}
