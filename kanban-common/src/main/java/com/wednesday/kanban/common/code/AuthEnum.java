package com.wednesday.kanban.common.code;

public enum  AuthEnum {

    SPACE_WRITE_CARD_WRITE(2,"s"),
    SPACE_READ_CARD_WRITE(1,"m"),
    SPACE_READ_CARD_READ(0,"r")

;
    private int code;
    private String des;

    AuthEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return this.des;
    }
}
