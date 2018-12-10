package com.wednesday.kanban.domain;

import java.util.List;

public class UserInstance extends BaseDomain {

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

    private List<Perm> perms;

    private String permissions;

    private String permissionDesc;

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

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

    public List<Perm> getPerms() {
        return perms;
    }

    public void setPerms(List<Perm> perms) {
        this.perms = perms;
        this.permissions = Perm.toString(perms);
        this.permissionDesc = Perm.permissionDesc(this.permissions);
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
        this.perms = Perm.list(permissions);
        this.permissionDesc = Perm.permissionDesc(this.permissions);
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
