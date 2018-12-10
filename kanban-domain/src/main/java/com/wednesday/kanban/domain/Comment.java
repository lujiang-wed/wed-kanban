package com.wednesday.kanban.domain;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/4 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public class Comment extends BaseDomain {

    /**
    空间/卡片
     */
    private Integer commentType;

    /**
    评论对象(空间ID，卡片ID)
     */
    private Long commentObject;

    /**
    评论对象状态
     */
    private String objectStatus;

    /**
    评论者pin
     */
    private String reviewerPin;

    /**
    评论者昵称
     */
    private String reviewerNick;

    /**
    评论内容
     */
    private String commnet;

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public Long getCommentObject() {
        return commentObject;
    }

    public void setCommentObject(Long commentObject) {
        this.commentObject = commentObject;
    }

    public String getObjectStatus() {
        return objectStatus;
    }

    public void setObjectStatus(String objectStatus) {
        this.objectStatus = objectStatus;
    }

    public String getReviewerPin() {
        return reviewerPin;
    }

    public void setReviewerPin(String reviewerPin) {
        this.reviewerPin = reviewerPin;
    }

    public String getReviewerNick() {
        return reviewerNick;
    }

    public void setReviewerNick(String reviewerNick) {
        this.reviewerNick = reviewerNick;
    }

    public String getCommnet() {
        return commnet;
    }

    public void setCommnet(String commnet) {
        this.commnet = commnet;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentType=" + commentType +
                ", commentObject=" + commentObject +
                ", objectStatus='" + objectStatus + '\'' +
                ", reviewerPin='" + reviewerPin + '\'' +
                ", reviewerNick='" + reviewerNick + '\'' +
                ", commnet='" + commnet + '\'' +
                '}';
    }
}