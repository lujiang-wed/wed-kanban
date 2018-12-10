package com.wednesday.kanban.common;

import com.wednesday.kanban.common.code.AuthEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wyyangyang1 on 2015/1/6.
 */
public class SpaceTreeNode {


    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 空间名
     */
    private String spaceName;

    /**
     权限：s为对空间有编辑权限，对空间内卡片有读写权限，m，对空间有查看权限，对卡片有读写权限，r为只读权限
     */
    private AuthEnum privilege;

    /** 子树集合*/
    private List<SpaceTreeNode> childList;

    public SpaceTreeNode(Long spaceId, String spaceName, AuthEnum privilege) {
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.privilege = privilege;
        this.childList = new LinkedList<SpaceTreeNode>();
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public List<SpaceTreeNode> getChildList() {
        return childList;
    }

    public void setChildList(List<SpaceTreeNode> childList) {
        this.childList = childList;
    }

    public AuthEnum getPrivilege() {
        return privilege;
    }

    public void setPrivilege(AuthEnum privilege) {
        this.privilege = privilege;
    }

    @Override
    public String toString() {
        return "SpaceTreeNode{" +
                "spaceId=" + spaceId +
                ", spaceName='" + spaceName + '\'' +
                ", privilege='" + privilege + '\'' +
                ", childList=" + childList +
                '}';
    }
}
