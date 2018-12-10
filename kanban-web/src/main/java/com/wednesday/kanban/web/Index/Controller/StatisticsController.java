package com.wednesday.kanban.web.Index.Controller;

import com.alibaba.fastjson.JSON;
import com.wednesday.kanban.biz.api.CardAuditBiz;
import com.wednesday.kanban.biz.api.CardTemplateBiz;
import com.wednesday.kanban.biz.api.SpaceAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.*;
import com.wednesday.kanban.common.result.StatisticsResult;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@RequestMapping("statistics")
public class StatisticsController {

    @Resource
    private CardAuditBiz cardAuditBiz;
    @Resource
    private SpaceAuditBiz spaceAuditBiz;
    @Resource
    private CardTemplateBiz cardTemplateBiz;

    private static final String NO_END_LIFE = "未标识上线时间";

    @RequestMapping("index.htm")
    private String index( Model model, Long spaceId, Integer sprint){
        model.addAttribute("spaceId",spaceId);
        Integer sprintMax = cardAuditBiz.findSpaceMaxSprint(spaceId);
        model.addAttribute("sprintMax",sprintMax);
        if (null == sprint){
            model.addAttribute("sprint",sprintMax);
        }else {
            model.addAttribute("sprint",sprint);
        }

        model.addAttribute("spaceName",spaceAuditBiz.findSpaceName(spaceId));
        return "statistics/index";
    }


    @ResponseBody
    @RequestMapping(value = "statisticalData.htm"/*,method = RequestMethod.POST*/)
    private String statisticalData(Long spaceId, Integer sprint, String bench){
        CardParam cardParam = new CardParam();
        cardParam.setPageSize(Integer.MAX_VALUE);
        cardParam.setSpaceId(spaceId);
        cardParam.setSprint(sprint);
        Page<CardInstance> cardInstancePage = cardAuditBiz.findByPage(cardParam);

        Map<Long, CardInstance> cardIdMap = new HashMap<Long, CardInstance>();
        Map<String, BigDecimal> dataMap = new HashMap<String, BigDecimal>();
        Long templateId = null;
        for (CardInstance cardInstance : cardInstancePage.getData()){
            String name = null;
            if ("卡片状态".equals(bench)){
                name = cardInstance.getLifeStatus().trim();
            }else if ("卡片类型".equals(bench)){
                if (1 == cardInstance.getCardType()){
                    name = "Story";
                }else if (2 == cardInstance.getCardType()){
                    name = "Task";
                }else {
                    name = "Bug";
                }
            }else {
                templateId = cardInstance.getTemplateId();
                cardIdMap.put(cardInstance.getId(),cardInstance);
                continue;
            }
            BigDecimal augend = new BigDecimal(1);
            if (dataMap.containsKey(name)){
                dataMap.put(name, dataMap.get(name).add(augend));
            }else {
                dataMap.put(name,augend);
            }
        }

        if (!cardIdMap.isEmpty() && null != templateId){
            Long benchId  = null;
            Long relateId = null;
            TemplateParam templateParam = cardTemplateBiz.getTemplateParam(templateId);
            for (TemplateAttrParam attrParam : templateParam.getAllAttrList()){
                if (bench.equals(attrParam.getAttrLabel())){
                    benchId = attrParam.getId();
                }else if (bench.contains("RD") && ("开发负责人".equals(attrParam.getAttrLabel()) || "开发人员".equals(attrParam.getAttrLabel()) || "负责人".equals(attrParam.getAttrLabel()))){
                    relateId = attrParam.getId();
                }else if (bench.contains("QA") && ("测试负责人".equals(attrParam.getAttrLabel()) || "测试人员".equals(attrParam.getAttrLabel()))){
                    relateId = attrParam.getId();
                }
            }

            CardAttrQueryParam queryParam = new CardAttrQueryParam();
            queryParam.setSpaceId(spaceId);
            //queryParam.setTemplateId(templateId);    由于页面上直接修改属性值未置入模板号，会导致统计的时候加上模板无法获取到数据，故去掉模板这个属性，
            List<CardAttrParam> cardAttrList = new LinkedList<CardAttrParam>();
            CardAttrParam benchParam = new CardAttrParam();
            benchParam.setAttrId(benchId);
            cardAttrList.add(benchParam);
            if (null != relateId){
                CardAttrParam relateParam = new CardAttrParam();
                relateParam.setAttrId(relateId);
                cardAttrList.add(relateParam);
            }

            queryParam.setCardAttrList(cardAttrList);
            List<CardAttrInstance> cardAttrInstanceList = cardAuditBiz.findSpaceAllValue(queryParam);

            Map<Long,String> benchValueMap = new HashMap<Long, String>();
            Map<Long,String> relateValueMap = new HashMap<Long, String>();
            for (CardAttrInstance cardAttrInstance : cardAttrInstanceList){
                if (!cardIdMap.containsKey(cardAttrInstance.getCardId())){
                    continue;
                }
                if (null != benchId && benchId.equals(cardAttrInstance.getAttrId())){
                    benchValueMap.put(cardAttrInstance.getCardId(),cardAttrInstance.getAttrValue());
                }
                if (null != relateId && relateId.equals(cardAttrInstance.getAttrId())){
                    relateValueMap.put(cardAttrInstance.getCardId(),cardAttrInstance.getAttrValue());
                }
            }

            for (Long cardId : benchValueMap.keySet()){
                String name = null;
                String value = null;
                if (!relateValueMap.isEmpty()){
                    name = relateValueMap.get(cardId);
                    value = benchValueMap.get(cardId);
                    if (StringUtils.isBlank(value)){
                        value = "0";
                    }
                }else {
                    name = benchValueMap.get(cardId);
                    value = "1";
                }
                if (StringUtils.isEmpty(name)){
                    name = "未指定";
                }
                name = name.trim();
                String[] nameArray = name.split(",");
                if (nameArray.length == 1){
                    nameArray = name.split("，");
                }
                if (nameArray.length == 1){
                    nameArray = name.split("、");
                }
                BigDecimal size = new BigDecimal(nameArray.length);
                for (String str : nameArray){
                    if (StringUtils.isBlank(str)){
                        continue;
                    }
                    if (!dataMap.containsKey(str)){
                        dataMap.put(str.trim(), new BigDecimal(value).divide(size,2,RoundingMode.HALF_UP));
                    }else {
                        dataMap.put(str.trim(), dataMap.get(str).add(new BigDecimal(value).divide(size,2, RoundingMode.HALF_UP)));
                    }
                }
            }
        }
        List<StatisticsResult> resultList = new LinkedList<StatisticsResult>();
        for (String key : dataMap.keySet()){
            StatisticsResult result = new StatisticsResult();
            result.setName(key);
            result.setValue(dataMap.get(key));
            resultList.add(result);
        }
        return JSON.toJSONString(resultList);
    }

    //时间轴展示
    @RequestMapping("timeAxis.htm")
    private String timeAxis(Model model, Long spaceId, Integer sprint) {
        CardParam cardParam = new CardParam();
        cardParam.setSpaceId(spaceId);
        cardParam.setPageSize(Integer.MAX_VALUE);
        if (null != sprint){
            cardParam.setSprint(sprint);
        }

        Page<CardInstance> cardPage = cardAuditBiz.statisticsCompleteCard(cardParam);
        //先按照 迭代分组，再按照上线时间分组
        Map<Integer,Map<String,List<CardInstance>>> cardListMap = new LinkedHashMap<Integer, Map<String, List<CardInstance>>>();
        for (CardInstance cardInstance : cardPage.getData()){
            if (!cardListMap.containsKey(cardInstance.getSprint())){
                Map<String,List<CardInstance>> endLifeMap = new LinkedHashMap<String, List<CardInstance>>();
                List<CardInstance> cardList = new LinkedList<CardInstance>();
                cardList.add(cardInstance);
                if (StringUtils.isBlank(cardInstance.getEndLife())){
                    endLifeMap.put(NO_END_LIFE,cardList);
                }else {
                    endLifeMap.put(cardInstance.getEndLife(),cardList);
                }
                cardListMap.put(cardInstance.getSprint(),endLifeMap);
            }else {
                String endLife = cardInstance.getEndLife();
                if (StringUtils.isBlank(endLife)){
                    endLife = NO_END_LIFE;
                }
                if (cardListMap.get(cardInstance.getSprint()).containsKey(endLife)){
                    cardListMap.get(cardInstance.getSprint()).get(endLife).add(cardInstance);
                }else {
                    List<CardInstance> cardList = new LinkedList<CardInstance>();
                    cardList.add(cardInstance);
                    cardListMap.get(cardInstance.getSprint()).put(endLife,cardList);
                }
            }
        }

        model.addAttribute("cardListMap",cardListMap);
        model.addAttribute("spaceName",spaceAuditBiz.findSpaceName(spaceId));
        return "statistics/timeAxis";
    }



}
