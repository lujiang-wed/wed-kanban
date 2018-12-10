package com.wednesday.kanban.domain;

public class Auth {
    /**
     域账号
     */
    private String user;

    /**
     空间id
     */
    private Long sid;



    /**
     空间Name
     */
    private String spaceName;
    /**
    权限：s为对空间有编辑权限，对空间内卡片有读写权限，m，对空间有查看权限，对卡片有读写权限，r为只读权限
     */
    private String privilege;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }
    @Override
    public String toString() {
        return "Auth{" +
                "user='" + user + '\'' +
                ", sid=" + sid +
                ", privilege='" + privilege + '\'' +
                '}';
    }
}