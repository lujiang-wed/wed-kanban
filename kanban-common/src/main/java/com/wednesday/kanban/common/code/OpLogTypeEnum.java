package com.wednesday.kanban.common.code;

public enum  OpLogTypeEnum {
    SPACE("空间"),
    CARD("卡片"),
    CARD_ATTR("卡片属性"),
    CARD_TEMPLATE("卡片模板"),
    OTHERS("其他")
    ;


    private String code;

    OpLogTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "OpLogTypeEnum{" +
                "code='" + code + '\'' +
                '}';
    }
}
