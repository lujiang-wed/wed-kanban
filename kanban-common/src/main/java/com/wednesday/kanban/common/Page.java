package com.wednesday.kanban.common;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private Integer pageSize;

    private Integer pageNo;

    private Integer totalCount;

    private List<T> data;

    public Page() {
    }

    public Page(Integer pageSize, Integer pageNo, Integer totalCount, List<T> data) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
        this.data = data;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", totalCount=" + totalCount +
                ", data=" + data +
                '}';
    }
}
