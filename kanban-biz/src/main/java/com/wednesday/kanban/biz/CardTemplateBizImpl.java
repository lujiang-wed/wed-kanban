package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.CardTemplateBiz;
import com.wednesday.kanban.biz.transfer.CardTemplateTransfer;
import com.wednesday.kanban.common.param.TemplateAttrParam;
import com.wednesday.kanban.common.param.TemplateParam;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.domain.CardTemplate;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.domain.TemplateAttrIndex;
import com.wednesday.kanban.service.api.CardTemplateQueryService;
import com.wednesday.kanban.service.api.CardTemplateService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("cardTemplateBiz")
public class CardTemplateBizImpl implements CardTemplateBiz {

    @Resource
    CardTemplateQueryService cardTemplateQueryService;

    @Resource
    CardTemplateService cardTemplateService;

    private static final Logger logger = LoggerFactory.getLogger(SpaceAuditBizImpl.class);

    @Override
    public TemplateParam getTemplateParam(long templateId) {
        if(templateId<=-2){
            logger.error("获取模版失败,templateId={}",templateId);
            return null;
        }
        CardTemplate cardTemplate=cardTemplateQueryService.getCardTemplateById(templateId);

        if(cardTemplate==null){
            logger.error("获取模版失败,templateId={}",templateId);
            return null;
        }
        logger.debug("获取模版成功,开始获取模版自定义属性");
        List<CardTemplateAttr> attrList=cardTemplateQueryService.getCardTemplateAttrsByTemplateId(templateId);
        List<CardTemplateAttr> parentAttrList =new ArrayList<CardTemplateAttr>();
        logger.debug("开始递归获取父模版的自定义属性");
        getParentTemplateAttrs(cardTemplate.getParentTemplate(),parentAttrList); //panting1
        return CardTemplateTransfer.transfer2TemplateParam(cardTemplate,attrList,parentAttrList);

    }

    @Override
    public CardTemplateAttr getCardTemplateAttrById(long id) {
        return cardTemplateQueryService.getCardTemplateAttrById(id);
    }

    @Override
    public TemplateParam filterTemplateAttrList(Long templateId) {
        if(templateId<=-2){
            logger.error("获取模版失败,templateId={}",templateId);
            return null;
        }
        CardTemplate cardTemplate=cardTemplateQueryService.getCardTemplateById(templateId);
        if(cardTemplate==null){
            logger.error("获取模版失败,templateId={}",templateId);
            return null;
        }
        logger.debug("获取模版成功,开始获取模版自定义属性");

        List<CardTemplateAttr> attrList=cardTemplateQueryService.getCardTemplateAttrsByTemplateId(templateId);
        List<CardTemplateAttr> parentAttrList =new ArrayList<CardTemplateAttr>();
        logger.debug("开始递归获取父模版的自定义属性");
        getParentTemplateAttrs(cardTemplate.getParentTemplate(),parentAttrList);
        List<CardTemplateAttr> allAttrList = new ArrayList<CardTemplateAttr>();
        allAttrList.addAll(parentAttrList);
        allAttrList.addAll(attrList);
        List<TemplateAttrIndex> templateAttrIndexList = cardTemplateQueryService.getTemplateAttrIndexService(templateId);
        allAttrList = filter(templateAttrIndexList,allAttrList);
        return CardTemplateTransfer.transfer2TemplateParamSort(cardTemplate,attrList,parentAttrList,allAttrList);
    }


    public  boolean isNumeric(String str){
        if(str.equals("")){
            return false;
        }
        for (int i = str.length()-1; i >= 0; i--){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }


    @Override
    public int updateTemplateAttrIndex(Long templateId, TemplateParam templateParam) {
        if(templateId<=-2){
            logger.error("获取模版失败,templateId={}",templateId);
            return -1;
        }
        List<TemplateAttrParam> parentAttrList = templateParam.getParentAttrList();
        List<TemplateAttrParam> attrList = templateParam.getAttrList();
        if(parentAttrList!= null){
            for(TemplateAttrParam param : parentAttrList){
                cardTemplateQueryService.updateTemplateAttrIndexByTemplateId(templateId, param);
            }
        }
        if(attrList!= null){
            for(TemplateAttrParam param : attrList){
                cardTemplateQueryService.updateTemplateAttrIndexByTemplateId(templateId, param);
            }
        }
        return 1;
    }

    @Override
    public int updateTemplateAttrShow(Long templateId, TemplateParam templateParam) {
        if(templateId<=-2){
            logger.error("获取模版失败,templateId={}",templateId);
            return -1;
        }
        List<TemplateAttrParam> parentAttrList = templateParam.getParentAttrList();
        List<TemplateAttrParam> attrList = templateParam.getAttrList();
        if(parentAttrList!= null){
            for(TemplateAttrParam param : parentAttrList){
                cardTemplateQueryService.updateTemplateAttrShowByTemplateId(templateId, param);
            }
        }
        if(attrList!= null){
            for(TemplateAttrParam param : attrList){
                cardTemplateQueryService.updateTemplateAttrShowByTemplateId(templateId, param);
            }
        }
        return 1;
    }

    @Override
    public List<TemplateAttrIndex> getTemplateAttrIndex(Long templateId) {
        if(templateId<=-2){
            logger.error("获取模版失败,templateId={}",templateId);
            return null;
        }
        return cardTemplateQueryService.getTemplateAttrIndexService(templateId);
    }

    private void getParentTemplateAttrs(long templateId,List<CardTemplateAttr> cardTemplateAttrList){
        if( templateId == 0){
            logger.debug("递归获取自定义属性完成");
            return ;
        }
        CardTemplate cardTemplate=cardTemplateQueryService.getCardTemplateById(templateId);
        if(cardTemplate==null){
            logger.error("获取模版失败,templateId={}",templateId);
            return;
        }
        getParentTemplateAttrs(cardTemplate.getParentTemplate(), cardTemplateAttrList);

        cardTemplateAttrList.addAll(cardTemplateQueryService.getCardTemplateAttrsByTemplateId(templateId));
    }

    @Override
    public List<TemplateParam> getTemplateParamBySpaceId(long spaceId) {
        if(spaceId<=0){
            logger.error("获取空间模版失败,spaceId={}",spaceId);
            return null;
        }
        List<CardTemplate> templateList=cardTemplateQueryService.getCardTemplatesBySpaceId(spaceId);
        if(CollectionUtils.isNotEmpty(templateList)){
            logger.debug("获取空间模版成功，开始获取其自定义属性");
            return fillTemplateParam(templateList);
        }

        logger.info("此空间无模版 spaceId={}",spaceId);
        return Collections.emptyList();
    }

    @Override
    public List<TemplateParam> getTemplateParamByParent(long templateId) {
        if(templateId<0){   //root默认为 0
            logger.error("获取模版失败,templateId={}",templateId);
            return null;
        }
        List<CardTemplate> templateList=cardTemplateQueryService.getCardTemplatesByParentId(templateId);
        if(CollectionUtils.isNotEmpty(templateList)){
            logger.debug("获取子模版成功，开始获取其自定义属性");
            return fillTemplateParam(templateList);
        }

        logger.info("此空间无模版 templateId={}",templateId);
        return Collections.emptyList();

    }

    private List<TemplateParam> fillTemplateParam(List<CardTemplate> templateList){
        List<TemplateParam> templateParamList=new ArrayList<TemplateParam>();
        for(CardTemplate cardTemplate:templateList){
            List<CardTemplateAttr> attrList=cardTemplateQueryService.getCardTemplateAttrsByTemplateId(cardTemplate.getId());
            List<CardTemplateAttr> parentAttrList =new ArrayList<CardTemplateAttr>();
            logger.debug("开始递归获取父模版的自定义属性");
            getParentTemplateAttrs(cardTemplate.getId(),parentAttrList);
            templateParamList.add(CardTemplateTransfer.transfer2TemplateParam(cardTemplate,attrList,parentAttrList));
        }
        return templateParamList;
    }

    @Override
    public boolean updateTemplate(TemplateParam templateParam) {
        if(templateParam==null){
            logger.error("修改模板失败,参数为空");
            return false;
        }
        CardTemplate cardTemplate= BeanConverter.convertEntity2DTO(templateParam, CardTemplate.class);
        if(cardTemplate==null||cardTemplate.getId()==null||cardTemplate.getId()<=0){
            logger.error("修改模版失败,参数异常 param:{}",templateParam);
            return false;
        }
        cardTemplateService.updateCardTemplate(cardTemplate);
        logger.debug("修改模版属性成功，开始修改模版自定义属性");
        if(CollectionUtils.isNotEmpty(templateParam.getAttrList())){
            for(TemplateAttrParam templateAttrParam:templateParam.getAttrList()){
                this.updateTemplateAttr(templateAttrParam);
            }
        }
        return true;
    }

    @Override
    public boolean updateTemplateAttr(TemplateAttrParam templateAttrParam) {
        CardTemplateAttr cardTemplateAttr=BeanConverter.convertEntity2DTO(templateAttrParam,CardTemplateAttr.class);
        if(cardTemplateAttr==null||cardTemplateAttr.getId()==null||cardTemplateAttr.getId()<0){
            logger.error("修改模版自定义属性失败 param:{}",templateAttrParam);
            return false;
        }
        cardTemplateService.updateCardTemplateAttr(cardTemplateAttr);
        return true;
    }

    @Override
    public TemplateParam addTemplateParam(TemplateParam templateParam) {
        CardTemplate cardTemplate= BeanConverter.convertEntity2DTO(templateParam,CardTemplate.class);
        if(cardTemplate==null||cardTemplate.getSpaceId()==null||cardTemplate.getSpaceId()<=0){
            logger.error("修改模版失败,参数异常 param:{}",templateParam);
            return null;
        }
        cardTemplateService.addCardTemplate(cardTemplate);
        logger.debug("添加自定义空间成功,开始添加其自定义属性");
        //获取自增长ID
        templateParam.setId(cardTemplate.getId());
        if(CollectionUtils.isNotEmpty(templateParam.getAttrList())){
            List<TemplateAttrParam> attrList=new ArrayList<TemplateAttrParam>();
            for(TemplateAttrParam templateAttrParam:templateParam.getAttrList()) {
                templateAttrParam.setTemplateId(cardTemplate.getId());
                attrList.add(this.addTemplateAttrParam(templateAttrParam));
            }
        }


        //将新增模板的父模板的所有属性insert入card_template_attr_index
        Long parentId = cardTemplateService.getParentTemplateId(templateParam.getId());
        while(parentId != 0) {
            List<CardTemplateAttr> attrList = cardTemplateQueryService.getCardTemplateAttrsByTemplateId(parentId);
            for(CardTemplateAttr attr : attrList){
                TemplateAttrParam templateAttrParam = BeanConverter.convertEntity2DTO(attr, TemplateAttrParam.class);
                TemplateAttrIndex templateAttrIndex = new TemplateAttrIndex();
                templateAttrIndex.setTemplateId(templateParam.getId());
                templateAttrIndex.setAttrLabel(templateAttrParam.getAttrLabel());
                templateAttrIndex.setIndex(templateAttrParam.getAttrLabelIndex());
                templateAttrIndex.setShow(templateAttrParam.getIsShow());

                if(templateAttrIndex == null || templateAttrIndex.getTemplateId() == null || StringUtils.isEmpty(templateAttrIndex.getAttrLabel())){
                    logger.error("添加模版自定义属性失败 param:{}",templateAttrParam);
                    return null;
                }
                cardTemplateService.addTemplateAttrIndex(templateAttrIndex);
            }
            parentId = cardTemplateService.getParentTemplateId(parentId);
        }

        return templateParam;
    }

    @Override
    public TemplateAttrParam addTemplateAttrParam(TemplateAttrParam templateAttrParam) {
        CardTemplateAttr cardTemplateAttr=BeanConverter.convertEntity2DTO(templateAttrParam,CardTemplateAttr.class);
        if(cardTemplateAttr==null||cardTemplateAttr.getTemplateId()==null||cardTemplateAttr.getTemplateId()<0|| StringUtils.isEmpty(cardTemplateAttr.getAttrLabel())||StringUtils.isEmpty(cardTemplateAttr.getInputType())){
            logger.error("添加模版自定义属性失败 param:{}",templateAttrParam);
            return null;
        }
        cardTemplateService.addCardTemplateAttr(cardTemplateAttr);
        logger.debug("添加自定义属性成功 result:{}", cardTemplateAttr);
        //添加属性到template_attr_for_index表中
        TemplateAttrIndex templateAttrIndex = new TemplateAttrIndex();
        templateAttrIndex.setTemplateId(templateAttrParam.getTemplateId());
        templateAttrIndex.setAttrLabel(templateAttrParam.getAttrLabel());
        templateAttrIndex.setIndex(templateAttrParam.getAttrLabelIndex());
        templateAttrIndex.setShow(templateAttrParam.getIsShow());
        if(templateAttrIndex == null || templateAttrIndex.getTemplateId() == null || StringUtils.isEmpty(templateAttrIndex.getAttrLabel())){
            logger.error("添加模版自定义属性失败 param:{}",templateAttrParam);
            return null;
        }
        cardTemplateService.addTemplateAttrIndex(templateAttrIndex);
        List<Long> childTemplate = new ArrayList<Long>();
        childTemplate = cardTemplateService.getChildTemplateId(templateAttrParam.getTemplateId());
        if(childTemplate != null || !childTemplate.isEmpty()){
            for(Long para : childTemplate){
                TemplateAttrIndex templateAttrIndex1 = new TemplateAttrIndex();
                templateAttrIndex1.setTemplateId(para);
                templateAttrIndex1.setAttrLabel(templateAttrParam.getAttrLabel());
                templateAttrIndex1.setIndex(Long.valueOf(0));
                templateAttrIndex1.setShow(1);
                cardTemplateService.addTemplateAttrIndex(templateAttrIndex1);
            }
        }

        return BeanConverter.convertEntity2DTO(cardTemplateAttr, TemplateAttrParam.class);

    }

    @Override
    public Boolean delTemplateAttrParam(Long templateAttrId) {
        if(templateAttrId==null||templateAttrId<=0){
            logger.error("删除模版属性失败");
            return false;
        }
        cardTemplateService.deleteCardTemplateAttr(templateAttrId);
        return true;
    }

    @Override
    public void delTemplateAttrIndex(Long templateId, Long templateAttrId) {
        if (templateAttrId == null || templateId == null) {
            logger.error("删除模版属性失败");
            return;
        }
        TemplateAttrIndex templateAttrIndex = new TemplateAttrIndex();
        templateAttrIndex.setTemplateId(templateId);
        String attrLabel = (cardTemplateService.getCardAttr(templateAttrId)).getAttrLabel();
        templateAttrIndex.setAttrLabel(attrLabel);
        cardTemplateService.deleteTemplateAttrIndex(templateAttrIndex);
        //同时删除所有子模板的属性index
        List<Long> childTemplate = new ArrayList<Long>();
        childTemplate = cardTemplateService.getChildTemplateId(templateId);
        if (childTemplate != null || !childTemplate.isEmpty()) {
            for (Long para : childTemplate) {
                TemplateAttrIndex templateAttrIndex1 = new TemplateAttrIndex();
                templateAttrIndex1.setTemplateId(para);
                String attrLabel1 = (cardTemplateService.getCardAttr(templateAttrId)).getAttrLabel();
                templateAttrIndex1.setAttrLabel(attrLabel1);
                cardTemplateService.deleteTemplateAttrIndex(templateAttrIndex1);
            }
        }
    }

    @Override
    public Boolean delTemplate(Long templateId) {
        List<CardTemplateAttr> attrList = cardTemplateQueryService.getCardTemplateAttrsByTemplateId(templateId);

        for (CardTemplateAttr attr : attrList){
            cardTemplateService.deleteCardTemplateAttr(attr.getId());
            logger.info("删除模板自定义属性,{}",attr);
        }
        logger.info("删除模板:{}",templateId);
        cardTemplateService.deleteCardTemplate(templateId);
        return true;
    }

    private List<CardTemplateAttr> filter(List<TemplateAttrIndex> templateAttrIndexList, List<CardTemplateAttr> allAttrList) {
        int length = templateAttrIndexList.size();
        List<CardTemplateAttr> returnAttrList = new ArrayList<CardTemplateAttr>();
        long[] indexArray = new long[length];
        for (int i = 0; i < length; i++) {
            indexArray[i] = templateAttrIndexList.get(i).getIndex();
        }
        long MAX = Long.MAX_VALUE;
        int index = 0;
        long min;
        for (int i = 0; i < length; i++) {
            min = indexArray[0];
            for (int j = length - 1; j >= 0; j--) {
                if (indexArray[j] <= min) {
                    index = j;
                    min = indexArray[j];
                }
            }
            indexArray[index] = MAX;
            TemplateAttrIndex templateAttrIndex = templateAttrIndexList.get(index);
            if (templateAttrIndex.getShow() == 1) {
                for (CardTemplateAttr para : allAttrList) {
                    if (templateAttrIndex.getAttrLabel().equals(para.getAttrLabel())) {
                        returnAttrList.add(para);
                        break;
                    }
                }
            }
        }
        return returnAttrList;
    }
}

