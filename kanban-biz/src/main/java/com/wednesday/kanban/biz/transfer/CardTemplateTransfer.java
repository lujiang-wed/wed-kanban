package com.wednesday.kanban.biz.transfer;

import com.wednesday.kanban.common.param.TemplateAttrParam;
import com.wednesday.kanban.common.param.TemplateParam;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.domain.CardTemplate;
import com.wednesday.kanban.domain.CardTemplateAttr;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CardTemplateTransfer {

    public static TemplateParam transfer2TemplateParam(CardTemplate cardTemplate,List<CardTemplateAttr> attrList,List<CardTemplateAttr> parentAttrList){
        TemplateParam templateParam= BeanConverter.convertEntity2DTO(cardTemplate,TemplateParam.class);
        templateParam.setAllAttrList(new LinkedList<TemplateAttrParam>());
        if(templateParam!=null){
            //转换自己的定义属性
            if(CollectionUtils.isNotEmpty(attrList)) {
                List<TemplateAttrParam> list = new ArrayList<TemplateAttrParam>();
                for (CardTemplateAttr cardTemplateAttr : attrList) {
                    list.add(BeanConverter.convertEntity2DTO(cardTemplateAttr, TemplateAttrParam.class));
                }
                templateParam.setAttrList(list);
                templateParam.getAllAttrList().addAll(list);
            }
            //转化父模板的自定义属性
            if(CollectionUtils.isNotEmpty(parentAttrList)){
                List<TemplateAttrParam> list=new ArrayList<TemplateAttrParam>();
                for(CardTemplateAttr cardTemplateAttr:parentAttrList){
                    list.add(BeanConverter.convertEntity2DTO(cardTemplateAttr,TemplateAttrParam.class));
                }
                templateParam.setParentAttrList(list);
                templateParam.getAllAttrList().addAll(list);
            }
        }

        return templateParam;
    }

    public static TemplateParam transfer2TemplateParamSort(CardTemplate cardTemplate,List<CardTemplateAttr> attrList,List<CardTemplateAttr> parentAttrList,List<CardTemplateAttr> allAttrList){
        TemplateParam templateParam= BeanConverter.convertEntity2DTO(cardTemplate,TemplateParam.class);
        if(templateParam!=null){
            //转换自己的定义属性
            if(CollectionUtils.isNotEmpty(attrList)) {
                List<TemplateAttrParam> list = new ArrayList<TemplateAttrParam>();
                for (CardTemplateAttr cardTemplateAttr : attrList) {
                    list.add(BeanConverter.convertEntity2DTO(cardTemplateAttr, TemplateAttrParam.class));
                }
                templateParam.setAttrList(list);
            }
            //转化父模板的自定义属性
            if(CollectionUtils.isNotEmpty(parentAttrList)){
                List<TemplateAttrParam> list=new ArrayList<TemplateAttrParam>();
                for(CardTemplateAttr cardTemplateAttr:parentAttrList){
                    list.add(BeanConverter.convertEntity2DTO(cardTemplateAttr,TemplateAttrParam.class));
                }
                templateParam.setParentAttrList(list);
            }
            //转化所有的自定义属性
            if(CollectionUtils.isNotEmpty(allAttrList)){
                List<TemplateAttrParam> list=new ArrayList<TemplateAttrParam>();
                for(CardTemplateAttr cardTemplateAttr:allAttrList){
                    list.add(BeanConverter.convertEntity2DTO(cardTemplateAttr,TemplateAttrParam.class));
                }
                templateParam.setAllAttrList(list);
            }
        }

        return templateParam;
    }
}
