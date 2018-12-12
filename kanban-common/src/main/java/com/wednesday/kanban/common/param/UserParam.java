package com.wednesday.kanban.common.param;

import com.wednesday.kanban.common.BasePageParam;

public class UserParam extends BasePageParam {

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

    /**
     * 密码
     */
    private String userPwd;

    /**
     * 原密码
     */
    private String oldPwd;

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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    @Override
    public String toString() {
        return "UserParam{" +
                "userId=" + userId +
                ", userName=" + userName +
                ", userPwd=" + userPwd +
                ", userType=" + userType +
                "}";
    }
}
