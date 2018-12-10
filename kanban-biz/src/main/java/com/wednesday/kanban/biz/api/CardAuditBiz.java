package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardAttrParam;
import com.wednesday.kanban.common.param.CardAttrQueryParam;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.common.result.CardAttrResult;
import com.wednesday.kanban.common.result.CardResult;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;

import java.util.List;
import java.util.Map;

public interface CardAuditBiz {

    /**
     * 创建卡片，获取模板自定义属性并插入
     * @param cardParam
     */
    public Long createCard(CardParam cardParam);

    /**
     * 批量添加卡片自定义属性
     * @param cardAttrList
     */
    public void fillCardAttr(List<CardAttrParam> cardAttrList);

    /**
     * 修改卡片自定义属性
     * @param cardAttrParam
     */
    public Boolean modifyCardAttr(CardAttrParam cardAttrParam);

    /**
     * 修改卡片基础属性
     * @param title
     * @param lifeStatus
     */
    public Boolean modifyCardTitleOrLifeStatus(Long cardId,String title, String lifeStatus);

    /**
     * 修改卡片基础属性
     * @param cardParam
     */
    public Boolean modifyCard(CardParam cardParam);

    /**
     * 根据条件，查询其所有卡片
     * @param cardParam
     * @return
     */
    public Page<CardInstance>  findByPage(CardParam cardParam);

    /**
     * 根据条件，查询其所有卡片
     * @param cardParam
     * @return
     */
    public Page<CardResult>  findAll(CardParam cardParam);

    /**
     * 根据卡片ID 查询卡片（基本属性和自定义属性）
     * @param id
     * @return
     */
    public CardResult findById(Long id);

    /**
     * 将单个卡片结果写入redis
     * @param CardResult
     * @return
     */
//    public boolean putCardResultToRedis(CardResult cardResult);
//    /**
//     * 将ridis信息取出
//     * @param CardInstance
//     * @return
//     */
//    public CardResult redisCardInstanceTransToCardResult(CardInstance cardInstance);
    /**
     * 获取卡片自定义属性
     * @param cardId
     * @return
     */
    public List<CardAttrResult> findAttrByCardId(Long cardId);
//    /**
//     * 封装redis get方法
//     * 返回值NULL为失败
//     * @param cardId
//     * @return
//     */
//    public String redisDaoGet(String s);

    /**
     * 根据自定义属性列表查询卡片信息
     * @param spaceId
     * @param templateId
     * @param attrId2attrValue
     * @return
     */
    public Page<CardResult> findByCondition(Long spaceId,Long templateId,Map<Long,String> attrId2attrValue,
                                            int pageSize, int pageNo);
    /**
     * 根据自定义属性列表查询卡片信息
     * @param cardAttrQueryParam
     * @return
     */
    public Page<CardResult> findByCondition(CardAttrQueryParam cardAttrQueryParam);

    /**
     * 对卡片进行软删除
     * @param cardId
     * @return
     */
    public Boolean deleteCard(Long cardId);


    /**
     * 获取空间下该模板所有属性值
     * @param cardAttrQueryParam
     * @return
     */
    public List<CardAttrInstance> findSpaceAllValue(CardAttrQueryParam cardAttrQueryParam);

    /**
     * 查询空间下 某个迭代 并统计卡片状态
     * @param cardParam
     * @return
     */
    public Page<CardInstance>  statisticsCompleteCard(CardParam cardParam);

    /**
     * 获取空间下的最大迭代编号
     * @param spaceId
     * @return
     */
    public Integer findSpaceMaxSprint(Long spaceId);


    /**
     * 将卡片复制到指定 迭代中，其他信息不变 (包括对应的自定义属性)
     * @param cardIdList
     */
    public void copyToTheSprint(List<Long> cardIdList, int sprint);

}
