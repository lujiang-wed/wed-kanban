package com.wednesday.kanban.common;

import java.io.Serializable;

public class BasePageParam extends BaseEntity implements Serializable {
    // 默认分页大小
    public static final int DEFAULT_PAGE_SIZE = 20;
    // 默认页码
    public static final int DEFAULT_PAGE_NUM = 1;
    // 分页参数，分页大小
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    // 分页参数，当前页数
    private Integer pageNo = DEFAULT_PAGE_NUM;
    // Oracle的分页参数
    private Integer startIdx;
    // Oracle的分页参数
    private Integer endIdx;
    //MySQL的分页参数
    private Integer start;
    //MySQL的分页参数
    private Integer limit;

    public Integer getEndIdx() {
        if(start != null && limit!= null){
            return start + limit - 1;
        }
        return endIdx;
    }

    public void setEndIdx(Integer endIdx) {
        this.endIdx = endIdx;
    }

    public Integer getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        if(start!=null && limit!= null){
            return start/limit + 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
        this.startIdx = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
        this.pageSize = limit;
    }

    @Override
    public String toString() {
        return "BasePageParam{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", startIdx=" + startIdx +
                ", endIdx=" + endIdx +
                ", start=" + start +
                ", limit=" + limit +
                '}';
    }
}
