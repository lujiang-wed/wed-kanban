package com.wednesday.kanban.common.code;

public enum OpLogAuditEnum {
    ADD("添加操作"),
    UPDATE("修改操作"),
    DELETE("删除操作"),
    QUERY("查询操作");
    ;

    private String code;

    OpLogAuditEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
