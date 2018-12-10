package com.wednesday.kanban.domain;

import java.util.ArrayList;
import java.util.List;

public enum Perm {
    ALL("a","所有权限"),
    ADD_USER("b","添加用户"),
    MODIFY_USER("c","修改用户"),
    DEL_USER("d","删除用户"),
    LIST_USER("e","查看所有用户"),
    SHOW_SPACE("f","查看空间"),
    EXPORT_EXCEL("g","EXCEL导出");

    private String code;
    private String desc;
    public final static String SPLITOR = "#";

    Perm(String code) {
        this.code = code;
    }

    Perm(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static List<Perm> list(String permissions) {
        if(permissions == null || "".equals(permissions)) {
            return new ArrayList<Perm>(0);
        }

        String[] arr = permissions.split(SPLITOR);
        List<Perm> list = new ArrayList<Perm>(arr.length);
        for(String a : arr) {
            Perm p = getByCode(a);
            if(p != null) {
                list.add(p);
            }
        }

        return list;
    }

    public static String permissionDesc(String permissions) {
        if(permissions == null || "".equals(permissions)) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        String[] arr = permissions.split(SPLITOR);
        for(String a : arr) {
            Perm p = getByCode(a);
            if(p != null) {
                sb.append(p.getDesc()).append(SPLITOR);
            }
        }

        return sb.toString();
    }

    public static String toString(List<Perm> list){
        StringBuffer sb = new StringBuffer();
        for(Perm p : list) {
            sb.append(p.getCode()).append(SPLITOR);
        }
        return sb.toString();
    }

    public static Perm getByCode(String code) {
        for(Perm p : values()) {
            if(p.getCode().equals(code)){
                return p;
            }
        }
        return null;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static void main(String[] args) {
        String str = "a#b#c#d#e";
        System.out.println(Perm.list(str));
    }
}
