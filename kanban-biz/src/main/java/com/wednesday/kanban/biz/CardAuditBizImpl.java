package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.CardAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardAttrParam;
import com.wednesday.kanban.common.param.CardAttrQueryParam;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.common.param.CopyCardParam;
import com.wednesday.kanban.common.result.CardAttrResult;
import com.wednesday.kanban.common.result.CardResult;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.service.api.CardQueryService;
import com.wednesday.kanban.service.api.CardService;
import com.wednesday.kanban.service.api.CardTemplateQueryService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component("cardAuditBiz")
public class CardAuditBizImpl implements CardAuditBiz {

    private static final Logger logger = LoggerFactory.getLogger(CardAuditBizImpl.class);

    @Resource
    private CardService cardService;
    @Resource
    private CardQueryService cardQueryService;
    @Resource
    private CardTemplateQueryService cardTemplateQueryService;
    @Override
    public Long createCard(CardParam cardParam) {
        if (null == cardParam || null == cardParam.getCardTitle() || null == cardParam.getSpaceId() || null == cardParam.getTemplateId()){
            logger.error("卡片参数异常");
            return null;
        }
        CardInstance cardInstance = BeanConverter.convertDTO2Entity(cardParam, CardInstance.class);
        Long cardId =  cardService.addCard(cardInstance);
        if (null == cardId){
            logger.error("创建卡卡片异常");
            return null;
        }
        logger.info("创建卡片成功,cardId{}",cardId);
        return cardId;
    }

    @Override
    public void fillCardAttr(List<CardAttrParam> cardAttrList) {
        List<CardAttrInstance> cardAttrInstanceList = new LinkedList<CardAttrInstance>();
        for (CardAttrParam cardAttrParam : cardAttrList){
            if (null != cardAttrParam && null != cardAttrParam.getAttrId() && null != cardAttrParam.getAttrValue()){
                cardAttrInstanceList.add(BeanConverter.convertDTO2Entity(cardAttrParam,CardAttrInstance.class));
            }
        }
        cardService.batchAddCardAttr(cardAttrInstanceList);
    }

    @Override
    public Boolean modifyCardAttr(CardAttrParam cardAttrParam) {
        if (null == cardAttrParam){
            logger.error("卡片自定义属性参数类异常");
            return Boolean.FALSE;
        }
        logger.info("修改卡片自定义属性,{}", cardAttrParam);

        cardService.modifyCardAttr(BeanConverter.convertDTO2Entity(cardAttrParam, CardAttrInstance.class));
        return Boolean.TRUE;
    }

    @Override
    public Boolean modifyCardTitleOrLifeStatus(Long cardId, String title, String lifeStatus) {

        CardParam cardParam = new CardParam();
        cardParam.setId(cardId);
        if(title!=null){

            cardParam.setCardTitle(title);
        }
        if(lifeStatus!=null) {
            cardParam.setLifeStatus(lifeStatus);
        }

        return modifyCard(cardParam);
    }

    @Override
    public Boolean modifyCard(CardParam cardParam) {
        if (null == cardParam || null == cardParam.getId()){
            logger.error("卡片基本属性参数类异常");
            return Boolean.FALSE;
        }
        logger.info("修改卡片基本属性,{}",cardParam);
        cardService.modifyCard(BeanConverter.convertDTO2Entity(cardParam,CardInstance.class));
        return Boolean.TRUE;
    }

    @Override
    public Page<CardInstance> findByPage(CardParam cardParam) {
        if (null == cardParam || (null == cardParam.getSpaceId() && null == cardParam.getTemplateId())){
            logger.error("查询参数为空");
            return null;
        }

        Page<CardInstance> cardInstancePage = cardQueryService.findByPage(cardParam);
        if (null == cardInstancePage || null == cardInstancePage.getData()){
            logger.error("查询空间卡片异常");
            return null;
        }
        //logger.info("查询到的卡片为：{}",cardInstancePage);
        return cardInstancePage;
    }

    @Override
    public Page<CardResult> findAll(CardParam cardParam) {
        if (null == cardParam || null == cardParam.getSpaceId()){
            logger.error("查询参数为空");
            return null;
        }

        Page<CardInstance> cardInstancePage = cardQueryService.findByPage(cardParam);
        if (null == cardInstancePage || null == cardInstancePage.getData()){
            logger.error("查询空间卡片异常");
            return null;
        }
        //logger.info("查询到的卡片为：{}",cardInstancePage);

        List<CardResult> cardResultList = this.batchObtainCardAttrInfo(cardInstancePage.getData());
        return new Page<CardResult>(cardInstancePage.getPageSize(),
                cardInstancePage.getPageNo(), cardInstancePage.getTotalCount(), cardResultList);

    }

    //批量查询 属性键值对
    private List<CardResult> batchObtainCardAttrInfo(List<CardInstance> cardInstanceList){
        List<Long> cardIdList = new LinkedList<Long>();
        for (CardInstance cardInstance : cardInstanceList){
            cardIdList.add(cardInstance.getId());
        }

        List<CardResult> cardResultList = new LinkedList<CardResult>();
        Map<Long,CardTemplateAttr> templateAttrMap = new HashMap<Long, CardTemplateAttr>();  //  存储查询到的模板属性，减少数据库查询次数
        Map<Long,List<CardAttrInstance>> attrMap = cardQueryService.batchFindAttrByCardId(cardIdList);

        for (CardInstance cardInstance : cardInstanceList){
            CardResult cardResult = BeanConverter.convertEntity2DTO(cardInstance,CardResult.class);
            List<CardAttrInstance> attrValueList = attrMap.get(cardInstance.getId());
            if (CollectionUtils.isEmpty(attrValueList)){
                cardResultList.add(cardResult);
                continue;
            }

            List<CardAttrResult> cardAttrList = new LinkedList<CardAttrResult>();
            for (CardAttrInstance cardAttrInstance : attrValueList){
                CardTemplateAttr cardTemplateAttr;
                if (templateAttrMap.containsKey(cardAttrInstance.getAttrId())){
                    cardTemplateAttr = templateAttrMap.get(cardAttrInstance.getAttrId());
                }else {
                    cardTemplateAttr = cardTemplateQueryService.getCardTemplateAttrById(cardAttrInstance.getAttrId());//通过属性的id来找到属性信息
                    templateAttrMap.put(cardAttrInstance.getAttrId(),cardTemplateAttr);
                }

                CardAttrResult cardAttrResult =
                        new CardAttrResult(cardTemplateAttr.getId(),cardTemplateAttr.getInputType(),cardTemplateAttr.getAttrLabel(),cardAttrInstance.getAttrValue(),cardTemplateAttr.getInputOption());
                cardAttrList.add(cardAttrResult);
            }
            cardResult.setCardAttrList(cardAttrList);
            cardResultList.add(cardResult);
        }
        return cardResultList;
    }

    @Override
    public CardResult findById(Long id) {
        if (null == id){
            logger.error("卡片ID为空！");
            return null;
        }
        CardInstance cardInstance = cardQueryService.findById(id);
        return this.cardInstanceTransToCardResult(cardInstance);
    }

    @Override
    public Page<CardResult> findByCondition(Long spaceId,Long templateId,Map<Long,String> attrId2attrValue, int pageSize, int pageNo) {
        CardAttrQueryParam cardAttrQueryParam = new CardAttrQueryParam();
        List<CardAttrParam> cardAttrList = new ArrayList<CardAttrParam>();


        for(Long attr_id:attrId2attrValue.keySet()){
            CardAttrParam cap = new CardAttrParam();
            cap.setAttrId(attr_id);
            cap.setAttrValue(attrId2attrValue.get(attr_id));
            cardAttrList.add(cap);
        }
        cardAttrQueryParam.setCardAttrList(cardAttrList);
        cardAttrQueryParam.setSpaceId(spaceId);
        cardAttrQueryParam.setTemplateId(templateId);
        cardAttrQueryParam.setPageSize(pageSize);
        cardAttrQueryParam.setPageNo(pageNo);
        return findByCondition(cardAttrQueryParam);
    }
    @Override
    public Page<CardResult> findByCondition(CardAttrQueryParam cardAttrQueryParam) {
        if (null == cardAttrQueryParam){
            logger.error("查询条件为空");
            return null;
        }
        Page<Long> cardIdPage = cardQueryService.findAttrByPage(cardAttrQueryParam);
        if (null == cardIdPage || null == cardIdPage.getData()){
            logger.error("查询到的卡片ID列表为空");
            return null;
        }
        logger.info("查询到的卡片ID列表为：{}",cardIdPage);
        Page<CardResult> cardResultPage = new Page<CardResult>();
        cardResultPage.setPageSize(cardIdPage.getPageSize());
        cardResultPage.setPageNo(cardIdPage.getPageNo());
        cardResultPage.setTotalCount(cardIdPage.getTotalCount());

        List<CardResult> cardResultList = new LinkedList<CardResult>();
        for (Long cardId : cardIdPage.getData()){
            CardResult cardResult = this.findById(cardId);
            logger.info("cardId:{}对应的cardResult为{}",cardId,cardResult);
            cardResultList.add(cardResult);
        }
        cardResultPage.setData(cardResultList);
        logger.info("按条件查询到的卡片信息为:{}",cardResultPage);
        return cardResultPage;
    }

    @Override
    public List<CardAttrResult> findAttrByCardId(Long cardId) {
        List<CardAttrInstance> cardAttrInstanceList = cardQueryService.findAttrByCardId(cardId);

        List<CardAttrResult> cardAttrResults = new LinkedList<CardAttrResult>();
        for (CardAttrInstance cardAttrInstance : cardAttrInstanceList){
            CardAttrResult cardAttrResult = new CardAttrResult(cardAttrInstance.getAttrId(),cardAttrInstance.getAttrValue());
            cardAttrResult.setId(cardAttrInstance.getId());
            cardAttrResults.add(cardAttrResult);
        }

        return cardAttrResults;
    }



    private CardResult cardInstanceTransToCardResult(CardInstance cardInstance){
        CardResult cardResult = BeanConverter.convertEntity2DTO(cardInstance,CardResult.class);
        //查找自定义属性
        List<CardAttrInstance> cardAttrInstanceList = cardQueryService.findAttrByCardId(cardInstance.getId());//通过卡片ID来找到卡片的属性值列表
        logger.info("卡片自定义属性为:{}",cardAttrInstanceList);
        List<CardAttrResult> cardAttrList = new LinkedList<CardAttrResult>();

        for (CardAttrInstance cardAttrInstance : cardAttrInstanceList){
            CardTemplateAttr cardTemplateAttr = cardTemplateQueryService.getCardTemplateAttrById(cardAttrInstance.getAttrId());//通过属性的id来找到属性信息
            CardAttrResult cardAttrResult =
                    new CardAttrResult(cardTemplateAttr.getId(),cardTemplateAttr.getInputType(),cardTemplateAttr.getAttrLabel(),cardAttrInstance.getAttrValue(),cardTemplateAttr.getInputOption());
            cardAttrList.add(cardAttrResult);
        }
        cardResult.setCardAttrList(cardAttrList);
        return cardResult;
    }
    @Override
    public Boolean deleteCard(Long cardId) {
        cardService.deleteCard(cardId);
        return Boolean.TRUE;
    }

    @Override
    public List<CardAttrInstance> findSpaceAllValue(CardAttrQueryParam cardAttrQueryParam) {
        return cardQueryService.findSpaceAllValue(cardAttrQueryParam);
    }

    @Override
    public Page<CardInstance> statisticsCompleteCard(CardParam cardParam) {
        return cardQueryService.statisticsCompleteCard(cardParam);
    }

    @Override
    public Integer findSpaceMaxSprint(Long spaceId) {
        return cardQueryService.findSpaceMaxSprint(spaceId);
    }

    @Override
    public void copyToTheSprint(List<Long> beCopyCardIdList, int sprint) {
        //批量查询所有卡片的自定义属性
        Map<Long,List<CardAttrInstance>>  beCopyAttrMap = cardQueryService.batchFindAttrByCardId(beCopyCardIdList);
        List<CardAttrInstance> cardAttrInstanceList = new LinkedList<CardAttrInstance>();

        Map<Long, String> templateAttrMap = new HashMap<Long, String>();
        //循环复制卡片基本属性
        for (Long beCopyCardId : beCopyCardIdList){
            Long cardId = cardService.copyCard(new CopyCardParam(beCopyCardId,sprint));
            if (beCopyAttrMap.containsKey(beCopyCardId)){
                for (CardAttrInstance cardAttrInstance : beCopyAttrMap.get(beCopyCardId)){
                    String templateAttrLabel;
                    if (templateAttrMap.containsKey(cardAttrInstance.getAttrId())){
                        templateAttrLabel = templateAttrMap.get(cardAttrInstance.getAttrId());
                    }else {
                        CardTemplateAttr cardTemplateAttr = cardTemplateQueryService.getCardTemplateAttrById(cardAttrInstance.getAttrId());//通过属性的id来找到属性信息
                        templateAttrMap.put(cardAttrInstance.getAttrId(),cardTemplateAttr.getAttrLabel());
                        templateAttrLabel = cardTemplateAttr.getAttrLabel();
                    }
                    if ("RD估点".equals(templateAttrLabel) || "QA估点".equals(templateAttrLabel) || "RD用点".equals(templateAttrLabel) || "QA用点".equals(templateAttrLabel)){
                        cardAttrInstance.setAttrValue("");
                    }else if ("新增需求".equals(templateAttrLabel)){
                        cardAttrInstance.setAttrValue("否");
                    }
                    cardAttrInstance.setCardId(cardId);
                    cardAttrInstanceList.add(cardAttrInstance);
                }
            }
        }
        //批量复制卡片自定义属性
        cardService.batchAddCardAttr(cardAttrInstanceList);
    }
}
