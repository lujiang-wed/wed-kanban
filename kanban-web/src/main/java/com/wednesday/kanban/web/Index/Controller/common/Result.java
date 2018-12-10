package com.wednesday.kanban.web.Index.Controller.common;

import com.google.gson.Gson;

import java.io.Serializable;

public class Result implements Serializable {
    private String code;
    private Boolean isSuccess = Boolean.FALSE;
    private Object data;

    public Result(Boolean isSuccess) {
        this.isSuccess = isSuccess;
        if(isSuccess) {
            this.code = "11111";
        }
        this.code = "00000";
    }

    public Result(String code, Boolean isSuccess) {
        this.code = code;
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
