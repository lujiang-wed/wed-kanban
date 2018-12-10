package com.wednesday.kanban.common.result;

import com.wednesday.kanban.common.BaseEntity;

public class UserResult extends BaseEntity {

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户登录名
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserParam{" +
                "userId=" + userId +
                ", userName=" + userName +
                ", userType=" + userType +
                "}";
    }
}
