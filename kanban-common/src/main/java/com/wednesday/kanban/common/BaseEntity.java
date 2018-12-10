package com.wednesday.kanban.common;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 当前用户IP
     */
    private String ip;

    /**
     * 当前用户端口
     */
    private String host;

    /**
     * 当前用户名
     */
    private String currentUser;

    /**
     * 查询创建开始时间
     */
    private Date queryCreateBegin;

    /**
     * 查询创建结束时间
     */
    private Date queryCreateEnd;

    /**
     * 查询修改开始时间
     */
    private Date queryModifyBegin;

    /**
     * 查询修改结束时间
     */
    private Date queryModifyEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Date getQueryCreateBegin() {
        return queryCreateBegin;
    }

    public void setQueryCreateBegin(Date queryCreateBegin) {
        this.queryCreateBegin = queryCreateBegin;
    }

    public Date getQueryCreateEnd() {
        return queryCreateEnd;
    }

    public void setQueryCreateEnd(Date queryCreateEnd) {
        this.queryCreateEnd = queryCreateEnd;
    }

    public Date getQueryModifyBegin() {
        return queryModifyBegin;
    }

    public void setQueryModifyBegin(Date queryModifyBegin) {
        this.queryModifyBegin = queryModifyBegin;
    }

    public Date getQueryModifyEnd() {
        return queryModifyEnd;
    }

    public void setQueryModifyEnd(Date queryModifyEnd) {
        this.queryModifyEnd = queryModifyEnd;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", modifier='" + modifier + '\'' +
                ", modifyTime=" + modifyTime +
                ", ip='" + ip + '\'' +
                ", host='" + host + '\'' +
                ", currentUser='" + currentUser + '\'' +
                ", queryCreateBegin=" + queryCreateBegin +
                ", queryCreateEnd=" + queryCreateEnd +
                ", queryModifyBegin=" + queryModifyBegin +
                ", queryModifyEnd=" + queryModifyEnd +
                '}';
    }
}
