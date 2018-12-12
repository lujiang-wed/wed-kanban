package com.wednesday.kanban.domain;

import java.util.List;

public class UserInstance extends BaseDomain {

    private Integer userType;

    private String userId;

    private String userName;

    private String userPwd;

    private List<Perm> perms;

    private String permissions;

    private String permissionDesc;

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

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
        return "UserInstance{" +
                "userType=" + userType +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", perms=" + perms +
                ", permissions='" + permissions + '\'' +
                ", permissionDesc='" + permissionDesc + '\'' +
                "} " + super.toString();
    }
}
