package com.wednesday.kanban.domain;

public class AuthorityContent {

    /**
     * 用户ID
     */
    private String user;

    /**
     * 用户权限JSON串
     */
    private String content;

    public AuthorityContent() {
    }

    public AuthorityContent(String user, String content) {
        this.user = user;
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AuthorityContent{" +
                "user='" + user + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
