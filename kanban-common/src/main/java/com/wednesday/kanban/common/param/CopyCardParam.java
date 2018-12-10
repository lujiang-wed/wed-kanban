package com.wednesday.kanban.common.param;

public class CopyCardParam {

    private Long id;

    //被复制的卡片
    private Long beCopyCardId;

    private int sprint;

    public CopyCardParam() {
    }

    public CopyCardParam(Long beCopyCardId, int sprint) {
        this.beCopyCardId = beCopyCardId;
        this.sprint = sprint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBeCopyCardId() {
        return beCopyCardId;
    }

    public void setBeCopyCardId(Long beCopyCardId) {
        this.beCopyCardId = beCopyCardId;
    }

    public int getSprint() {
        return sprint;
    }

    public void setSprint(int sprint) {
        this.sprint = sprint;
    }
}
