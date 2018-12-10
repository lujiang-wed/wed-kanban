package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardAttrQueryParam;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;

import java.util.List;
import java.util.Map;

public interface CardQueryService {

    /**
     * 按卡片ID进行查询
     * @param id
     * @return
     */
    public CardInstance findById(Long id);

    /**
     * 按条件进行查询
     * @param cardParam
     * @return
     */
    public Page<CardInstance> findByPage(CardParam cardParam);

    /**
     * 按自定义属性ID进行查询
     * @param id
     * @return
     */
    public CardAttrInstance findAttrById(Long id);

    /**
     * 按卡片ID查询相关的自定义属性
     * @param cardId
     * @return
     */
    public List<CardAttrInstance> findAttrByCardId(Long cardId);

    /**
     * 根据卡片ID列表批量查询自定义属性
     * @param cardIdList
     * @return
     */
    public Map<Long,List<CardAttrInstance>> batchFindAttrByCardId(List<Long> cardIdList);

    /**
     * 搜索满足条件的卡片自定义属性ID列表
     * @param cardAttrQueryParam
     * @return
     */
    public Page<Long> findAttrByPage(CardAttrQueryParam cardAttrQueryParam);

    /**
     * 获取空间下该模板所有属性值
     * @param cardAttrQueryParam
     * @return
     */
    public List<CardAttrInstance> findSpaceAllValue(CardAttrQueryParam cardAttrQueryParam);

    /**
     * 统计已经上线的卡片
     * @param cardParam
     * @return
     */
    public Page<CardInstance> statisticsCompleteCard(CardParam cardParam);

    /**
     * 获取空间下的最大迭代编号
     * @param spaceId
     * @return
     */
    public Integer findSpaceMaxSprint(Long spaceId);

}
