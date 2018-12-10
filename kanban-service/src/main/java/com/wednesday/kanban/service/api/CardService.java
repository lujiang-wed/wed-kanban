package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.param.CopyCardParam;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;

import java.util.List;

public interface CardService {

    /**
     * 添加卡片
     * @param cardInstance
     */
    public Long addCard(CardInstance cardInstance);

    /**
     * 修改卡片基本属性
     * @param cardInstance
     */
    public void modifyCard(CardInstance cardInstance);

    /**
     * 删除卡片（逻辑删除），不修改自定义属性
     */
    public void deleteCard(Long id);

    /**
     * 添加卡片自定义属性
     * @param cardAttrInstance
     */
    public void addCardAttr(CardAttrInstance cardAttrInstance);

    /**
     * 批量添加卡片自定义属性
     * @param cardAttrList
     */
    public void batchAddCardAttr(List<CardAttrInstance> cardAttrList);

    /**
     * 修改卡片自定义属性
     * @param cardAttrInstance
     */
    public void modifyCardAttr(CardAttrInstance cardAttrInstance);

    /**
     * 复制卡片基本信息
     * @param copyCardParam
     * @return
     */
    public Long copyCard(CopyCardParam copyCardParam);
}
