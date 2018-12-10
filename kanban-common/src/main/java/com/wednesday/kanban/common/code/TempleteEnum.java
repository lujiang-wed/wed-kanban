package com.wednesday.kanban.common.code;

/**
 * Created by wyzhangdong on 2014/12/10.
 */
public enum TempleteEnum {

    Radio("radio","单选按钮",1),CheckBox("checkBox","多选按钮",2),Select("select","下拉列表",3),SingleText("singleText","单行文本",4),RichText("richText","富文本",5);

    private String name;
    private String desc;
    private int code;

     TempleteEnum(String name,String desc,int code){
        this.name=name;
        this.desc=desc;
        this.code=code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
