package com.wednesday.kanban.web.Index.Controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.wednesday.kanban.biz.api.CardAuditBiz;
import com.wednesday.kanban.biz.api.CardTemplateBiz;
import com.wednesday.kanban.biz.api.SpaceAuditBiz;
import com.wednesday.kanban.biz.cookies.CookieUtil;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardAttrParam;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.common.param.TemplateAttrParam;
import com.wednesday.kanban.common.param.TemplateParam;
import com.wednesday.kanban.common.result.CardAttrResult;
import com.wednesday.kanban.common.result.CardResult;
import com.wednesday.kanban.common.result.SpaceResult;
import com.wednesday.kanban.common.template.*;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.common.utils.CommonUtil;
import com.wednesday.kanban.domain.CardInstance;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.AjaxResultCodeEnum;
import util.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("card")
public class CardController {
    @Resource
    private CardAuditBiz cardAuditBiz;
    @Resource
    private CardTemplateBiz cardTemplateBiz;
    @Resource
    private SpaceAuditBiz spaceAuditBiz;

    private static final String SPRINT_COOKIE_NAME = "COACH_SPRINT_NAME_";


    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    //redis
//    @Resource
//    private CacheClusterClient redisDao;
//   s
    @RequestMapping("index.htm")
    @ResponseBody
    private String index(HttpServletRequest request,HttpServletResponse response){
        String spaceId = request.getParameter("spaceId");
        String templateIdStr = request.getParameter("templateId");
        String cardTypeStr = request.getParameter("cardType");
        String sprintStr = request.getParameter("sprint");
        String pageSizeStr = request.getParameter("pageSize");
        String pageNoStr = request.getParameter("pageNo");
        if (null == spaceId || !StringUtils.isNumeric(spaceId)){
            return null;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(sprintStr) || "-1".equals(sprintStr)){
            sprintStr = CookieUtil.getCookie(request , SPRINT_COOKIE_NAME + spaceId);
        }else {
            CookieUtil.createCookie(response,SPRINT_COOKIE_NAME+spaceId,sprintStr,Integer.MAX_VALUE+"");
        }

        Page<CardResult> cardPage = null;

//        if(cardTypeIdStr == null || "-1".equals(cardTypeIdStr) || "-1".equals(cardTypeStr)) {
//            CardParam cardParam = new CardParam();
//            cardParam.setSpaceId(Long.parseLong(spaceId));
//            if (StringUtils.isNumeric(templateIdStr) && !"-1".equals(templateIdStr)) {
//                cardParam.setTemplateId(Long.parseLong(templateIdStr));
//            }
//            if (StringUtils.isNumeric(sprintStr) && !"-1".equals(sprintStr)) {
//                cardParam.setSprint(Integer.parseInt(sprintStr));
//            }
//            if (StringUtils.isNumeric(pageNoStr)) {
//                cardParam.setPageNo(Integer.parseInt(pageNoStr));
//            }
//            cardParam.setPageSize(30);
//            cardPage = cardAuditBiz.findAll(cardParam);
//        } else {
//            CardAttrQueryParam cardAttrQueryParam = new CardAttrQueryParam();
//            cardAttrQueryParam.setSpaceId(Long.parseLong(spaceId));
//            cardAttrQueryParam.setTemplateId(Long.parseLong(templateIdStr));
//            cardAttrQueryParam.setPageSize(30);
//            if (StringUtils.isNumeric(pageNoStr)) {
//                cardAttrQueryParam.setPageNo(Integer.parseInt(pageNoStr));
//            }
//
//            List<CardAttrParam> cardAttrList = new ArrayList<CardAttrParam>();
//            CardAttrParam cap = new CardAttrParam();
//            cap.setAttrId(Long.parseLong(cardTypeIdStr));
//            cap.setAttrValue(cardTypeStr);
//            cardAttrList.add(cap);
//            cardAttrQueryParam.setCardAttrList(cardAttrList);
//
//            cardPage = cardAuditBiz.findByCondition(cardAttrQueryParam);
//        }

        CardParam cardParam = new CardParam();
        cardParam.setSpaceId(Long.parseLong(spaceId));
        if (StringUtils.isNumeric(templateIdStr) && !"-1".equals(templateIdStr)) {
            cardParam.setTemplateId(Long.parseLong(templateIdStr));
        }
        if (StringUtils.isNumeric(sprintStr) && !"-1".equals(sprintStr)) {
            cardParam.setSprint(Integer.parseInt(sprintStr));
        }
        if (StringUtils.isNumeric(cardTypeStr) && !"-1".equals(cardTypeStr)) {
            cardParam.setCardType(Integer.parseInt(cardTypeStr));
        }
        if (StringUtils.isNumeric(pageNoStr)) {
            cardParam.setPageNo(Integer.parseInt(pageNoStr));
        }
        if (StringUtils.isNumeric(pageSizeStr)) {
            cardParam.setPageSize(Integer.parseInt(pageSizeStr));
        } else {
            cardParam.setPageSize(30);
        }
        cardPage = cardAuditBiz.findAll(cardParam);

        return JSON.toJSONString(cardPage);
    }

    @RequestMapping("showLane.htm")
    private String showLane(HttpServletRequest request, HttpServletResponse response, Model model) {
        String spaceId = request.getParameter("spaceId");
        String maxSprintId = request.getParameter("maxSprintId");
        model.addAttribute("spaceId", spaceId);
        model.addAttribute("maxSprintId", maxSprintId);

        return "card/lane";
    }

    @RequestMapping("lane.htm")//查找卡片信息
    @ResponseBody
    private String lane(HttpServletRequest request){
        String spaceId = request.getParameter("spaceId");
        String sprint = request.getParameter("sprint");

        if (null == spaceId || !org.apache.commons.lang3.StringUtils.isNumeric(spaceId)){
            return null;
        }

        if (null == sprint || !org.apache.commons.lang3.StringUtils.isNumeric(sprint)){
            return null;
        }

        CardParam cardParam = new CardParam();
        cardParam.setSpaceId(Long.parseLong(spaceId));//空间
        cardParam.setSprint(Integer.parseInt(sprint));//迭代
        cardParam.setPageSize(Integer.MAX_VALUE);//分页查询的大小

        Page<CardResult> cardPage =  cardAuditBiz.findAll(cardParam);

        return JSON.toJSONString(cardPage);//将对象化为jason
    }

    @RequestMapping(value = "submitCopyAttr.htm",method = RequestMethod.POST)
    private String submitCopyAttr(HttpServletRequest request){

        LOGGER.info("复制卡片,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        String cardIdStr = request.getParameter("cardId");
        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        Long templateId = Long.parseLong(request.getParameter("templateId"));
        Integer sprint = Integer.parseInt(request.getParameter("sprint"));
        Integer cardType = Integer.parseInt(request.getParameter("cardType"));
        String cardTitle = request.getParameter("cardTitle");
        Map paramMap = request.getParameterMap();
        Long cardId = Long.parseLong(cardIdStr);

        List<CardAttrResult> cardAttrResultList = cardAuditBiz.findAttrByCardId(cardId);//返回卡片所有属性值

        Map<Long,CardAttrResult> cardAttrMap = new HashMap<Long, CardAttrResult>();
        for (CardAttrResult attr : cardAttrResultList){
            cardAttrMap.put(attr.getAttrId(),attr);//将属性和属性ID对应
        }

//        List<CardAttrParam> cardAttrList = new LinkedList<CardAttrParam>();
        for (Object key : paramMap.keySet()) {
            Object obj = paramMap.get(key);

            String keyStr = (String)key;
            if ("cardId".equals(keyStr) || "spaceId".equals(keyStr) || "templateId".equals(keyStr)
                    || "sprint".equals(keyStr) || "cardTitle".equals(keyStr) || "cardType".equals(keyStr)){
                continue;
            }

            String value = "";
            List valueList = Arrays.asList((String[])obj);
            for (Object o : valueList){
                value = value + String.valueOf(o)+",";
            }
            if (StringUtils.isNotEmpty(value)){
                value = value.substring(0,value.length()-1);
            }

            CardAttrParam cardAttrParam = new CardAttrParam();
            cardAttrParam.setCardId(cardId);
            cardAttrParam.setSpaceId(spaceId);
            cardAttrParam.setTemplateId(templateId);
            cardAttrParam.setAttrId(Long.parseLong(keyStr));
            cardAttrParam.setAttrValue(value);
//            cardAttrList.add(cardAttrParam);

            Long attrId = cardAttrParam.getAttrId();

            if(cardAttrMap.containsKey(attrId)){
                cardAttrParam.setId(cardAttrMap.get(attrId).getId());
            }

            cardAuditBiz.modifyCardAttr(cardAttrParam);;
        }

//        cardAuditBiz.fillCardAttr(cardAttrList);

        CardParam cardParam = new CardParam();
        cardParam.setId(cardId);
        cardParam.setCardTitle(cardTitle);
        cardParam.setSprint(sprint);
        cardParam.setCardType(cardType);

        cardAuditBiz.modifyCard(cardParam);

        return "redirect:/space/cardList.htm?spaceId="+spaceId;
    }

    @RequestMapping(value = "submitAttr.htm",method = RequestMethod.POST)
    private String submitAttr(HttpServletRequest request){

        LOGGER.info("新建卡片自定义属性,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        String cardIdStr = request.getParameter("cardId");
        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        Long templateId = Long.parseLong(request.getParameter("templateId"));
        Integer sprint = Integer.parseInt(request.getParameter("sprint"));
        Integer cardType = Integer.parseInt(request.getParameter("cardType"));
        String cardTitle = request.getParameter("cardTitle");
        Map paramMap = request.getParameterMap();
        Long cardId = Long.parseLong(cardIdStr);

        List<CardAttrParam> cardAttrList = new LinkedList<CardAttrParam>();
        for (Object key : paramMap.keySet()) {
            Object obj = paramMap.get(key);

            String keyStr = (String)key;
            if ("cardId".equals(keyStr) || "spaceId".equals(keyStr) || "templateId".equals(keyStr)
                    || "sprint".equals(keyStr) || "cardTitle".equals(keyStr) || "cardType".equals(keyStr)){
                continue;
            }

            String value = "";
            List valueList = Arrays.asList((String[])obj);
            for (Object o : valueList){
                value = value + String.valueOf(o)+",";
            }
            if (StringUtils.isNotEmpty(value)){
                value = value.substring(0,value.length()-1);
            }

            CardAttrParam cardAttrParam = new CardAttrParam();
            cardAttrParam.setCardId(cardId);
            cardAttrParam.setSpaceId(spaceId);
            cardAttrParam.setTemplateId(templateId);
            cardAttrParam.setAttrId(Long.parseLong(keyStr));
            cardAttrParam.setAttrValue(value);
            cardAttrList.add(cardAttrParam);
        }

        cardAuditBiz.fillCardAttr(cardAttrList);

        CardParam cardParam = new CardParam();
        cardParam.setId(cardId);
        cardParam.setCardTitle(cardTitle);
        cardParam.setSprint(sprint);
        cardParam.setCardType(cardType);

        cardAuditBiz.modifyCard(cardParam);

        return "redirect:/space/cardList.htm?spaceId="+spaceId;
    }

    @RequestMapping(value = "createCard.htm",method = RequestMethod.POST)
    private String createCard(HttpServletRequest request, HttpServletResponse response,Model model){

        LOGGER.info("新建卡片基本属性,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        String title = request.getParameter("title");
        //String lifeStatus = request.getParameter("currentStatus");
        String lifeStatus = "需求池";
        Long templateId = Long.parseLong(request.getParameter("selectTemplate"));
        //String cardDesc = request.getParameter("cardDesc");

        CardParam cardParam = new CardParam();
        cardParam.setSpaceId(spaceId);
        cardParam.setCardTitle(title);
        cardParam.setLifeStatus(lifeStatus);
        cardParam.setTemplateId(templateId);
        //cardParam.setCardDesc(cardDesc);
        cardParam.setCardType(1);
        cardParam.setSprint(0);
        cardParam.setCreator("");
        cardParam.setCreateTime(new Date());

        Long cardId = cardAuditBiz.createCard(cardParam);

        model.addAttribute("cardId",cardId);
        model.addAttribute("cardTitle",title);
        model.addAttribute("templateId", templateId);
        model.addAttribute("spaceId",spaceId);
        model.addAttribute("sprint",0);
        model.addAttribute("cardType",1);

        TemplateParam template = cardTemplateBiz.getTemplateParam(templateId);

        model.addAttribute("templateName",template.getName());
        List<AbstractTemplate> templateAttrList = new LinkedList<AbstractTemplate>();
        if(template.getParentAttrList()!=null) {
            for (TemplateAttrParam templateAttrParam : template.getParentAttrList()) {
                templateAttrList.add(convertTemplateAttrParam2AbstractTemplate(templateAttrParam));
            }
        }

        if(template.getAttrList()!=null){
            for (TemplateAttrParam templateAttrParam : template.getAttrList()){
                templateAttrList.add(convertTemplateAttrParam2AbstractTemplate(templateAttrParam));
            }
        }
            model.addAttribute("templateAttrList",templateAttrList);

            return "card/fillAttr";
        }

    @RequestMapping(value = "copyCard.htm", method = RequestMethod.GET)
    private String copyCard(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        Long cardId = Long.parseLong(request.getParameter("cardId"));
        CardResult cardResult = cardAuditBiz.findById(cardId);

        CardParam cardParam = new CardParam();
        cardParam.setSpaceId(cardResult.getSpaceId());
        cardParam.setCardType(cardResult.getCardType());
        cardParam.setCardTitle(cardResult.getCardTitle());
        cardParam.setTemplateId(cardResult.getTemplateId());
        cardParam.setCardDesc(cardResult.getCardDesc());
        cardParam.setSprint(cardResult.getSprint() + 1);
        cardParam.setLifeStatus(cardResult.getLifeStatus());
        cardParam.setEndLife(cardResult.getEndLife());
        cardParam.setAttrValues(cardResult.getAttrValues());
        cardParam.setCreator("");
        cardParam.setCreateTime(new Date());

        Long newCardId = cardAuditBiz.createCard(cardParam);

        List<CardAttrParam> cardAttrList = new LinkedList<CardAttrParam>();

        for(CardAttrResult cardAttrResult: cardResult.getCardAttrList())
        {
            CardAttrParam cardAttrParam = new CardAttrParam();
            cardAttrParam.setCardId(newCardId);
            cardAttrParam.setSpaceId(cardResult.getSpaceId());
            cardAttrParam.setTemplateId(cardResult.getTemplateId());
            cardAttrParam.setCardType(cardResult.getCardType());
            cardAttrParam.setAttrId(cardAttrResult.getAttrId());
            cardAttrParam.setAttrValue(cardAttrResult.getAttrValue());

            cardAttrList.add(cardAttrParam);
        }
        cardAuditBiz.fillCardAttr(cardAttrList);

        model.addAttribute("cardId", newCardId);
        model.addAttribute("cardTitle", cardParam.getCardTitle());
        model.addAttribute("templateId", cardParam.getTemplateId());
        model.addAttribute("spaceId", cardParam.getSpaceId());
        model.addAttribute("sprint", cardParam.getSprint());
        model.addAttribute("cardType", cardParam.getCardType());

        TemplateParam template = cardTemplateBiz.getTemplateParam(cardParam.getTemplateId());
        model.addAttribute("templateName",template.getName());

        List<AbstractTemplate> templateAttrList = new LinkedList<AbstractTemplate>();

        if(template.getParentAttrList()!=null) {
            for (TemplateAttrParam templateAttrParam : template.getParentAttrList()) {
                templateAttrList.add(convertTemplateAttrParam2AbstractTemplate(templateAttrParam));
            }
        }

        if(template.getAttrList()!=null){
            for (TemplateAttrParam templateAttrParam : template.getAttrList()){
                templateAttrList.add(convertTemplateAttrParam2AbstractTemplate(templateAttrParam));
            }
        }

        for(CardAttrResult cardAttrResult: cardResult.getCardAttrList())
        {
            Long attrId = cardAttrResult.getAttrId();
            for(AbstractTemplate abstractTemplate: templateAttrList)
            {
                if (abstractTemplate.getId() == attrId)
                {
                    abstractTemplate.setInputValue(cardAttrResult.getAttrValue());
                }
            }
        }

        model.addAttribute("templateAttrList", templateAttrList);

        return "card/copyFillAttr";
    }

    @RequestMapping(value = {"editAttr.htm","viewCard.htm"},method = RequestMethod.GET)
    private String editAttr(HttpServletRequest request, HttpServletResponse response,Model model){

        String path = request.getRequestURI();
        if ("/card/viewCard.htm".equals(path)){
            model.addAttribute("operateType","view");
        }else {
            model.addAttribute("operateType","edit");
        }

        Long cardId = Long.parseLong(request.getParameter("cardId"));
        CardResult cardResult = cardAuditBiz.findById(cardId);
        TemplateParam templateParam = cardTemplateBiz.getTemplateParam(cardResult.getTemplateId());

        Map<Long,String> inputValueMap = new HashMap<Long, String>();

        for (CardAttrResult attr : cardResult.getCardAttrList()){
            inputValueMap.put(attr.getAttrId(),attr.getAttrValue());
        }

        List<AbstractTemplate> templateAttrList = new LinkedList<AbstractTemplate>();
        if(templateParam.getAttrList()!=null||templateParam.getParentAttrList()!=null) {
            if(CollectionUtils.isNotEmpty(templateParam.getParentAttrList())) {
                for (TemplateAttrParam templateAttrParam : templateParam.getParentAttrList()) {
                    AbstractTemplate abstractTemplate = convertTemplateAttrParam2AbstractTemplate(templateAttrParam);
                    if (null == abstractTemplate) {
                        continue;
                    }
                    abstractTemplate.setInputValue(inputValueMap.get(templateAttrParam.getId()));
                    templateAttrList.add(abstractTemplate);
                }
            }
            if(CollectionUtils.isNotEmpty(templateParam.getAttrList())) {
                for (TemplateAttrParam templateAttrParam : templateParam.getAttrList()) {
                    AbstractTemplate abstractTemplate = convertTemplateAttrParam2AbstractTemplate(templateAttrParam);
                    if (null == abstractTemplate) {
                        continue;
                    }
                    abstractTemplate.setInputValue(inputValueMap.get(templateAttrParam.getId()));
                    templateAttrList.add(abstractTemplate);
                }
            }
        }
        model.addAttribute("spaceId",cardResult.getSpaceId());
        model.addAttribute("templateId",cardResult.getTemplateId());
        model.addAttribute("cardId",cardResult.getId());
        model.addAttribute("cardTitle",cardResult.getCardTitle());
        model.addAttribute("cardDesc",cardResult.getCardDesc());
        model.addAttribute("sprint",cardResult.getSprint());
        model.addAttribute("endLife",cardResult.getEndLife());  //新增endLife为上线时间
        model.addAttribute("templateAttrList",templateAttrList);

        if (cardResult.getCardType() == null) {
            model.addAttribute("cardType",1);
        } else {
            model.addAttribute("cardType",cardResult.getCardType());
        }

        SpaceResult space = spaceAuditBiz.findOne(cardResult.getSpaceId());
        model.addAttribute("spaceName",space.getSpaceName());

        return "card/editAttr";
    }
    @RequestMapping(value = "editBasicAttr.htm",method = RequestMethod.POST)
    private String editBasicAttr(HttpServletRequest request) {
        LOGGER.info("编辑基本属性,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        Long cardId = Long.parseLong(request.getParameter("cardId"));

        String cardTitle = request.getParameter("cardTitle");
        String lifeStatus = request.getParameter("lifeStatus");
        cardAuditBiz.modifyCardTitleOrLifeStatus(cardId,cardTitle,lifeStatus);
        return "";
    }
    @RequestMapping(value = "modifyCardLifeStatus.htm",method = RequestMethod.POST)
    @ResponseBody
    private String modifyCardLifeStatus(HttpServletRequest request) {

        LOGGER.info("编辑卡片状态,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));
        Long cardId = Long.parseLong(request.getParameter("cardId"));
        String lifeStatus = request.getParameter("lifeStatus");
        cardAuditBiz.modifyCardTitleOrLifeStatus(cardId, null, lifeStatus);
        return "";
    }
    @RequestMapping(value = "queryByCondition.htm",method = RequestMethod.POST)
    @ResponseBody
    private String queryByCondition(HttpServletRequest request) {
        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        Long templateId = Long.parseLong(request.getParameter("templateId"));
        //此处需要加上对自定义属性的判断
        if(spaceId ==null && templateId==null){
            return "所有条件不能同时为空";
        }
        Map<Long,String> attrId2attrValue = new HashMap<Long, String>();
        //此处需要增加：从前端拿到自定义属性的id，以及查询的值的map

        Page<CardResult> result = cardAuditBiz.findByCondition(spaceId, templateId, attrId2attrValue, 30, 1);
        //此处的分页可能导致问题，需要注意
        return JSON.toJSONString(result);
    }
    @RequestMapping(value = "editAttr.htm",method = RequestMethod.POST)
    private String editAttr(HttpServletRequest request){
        LOGGER.info("编辑卡片,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        Long cardId = Long.parseLong(request.getParameter("cardId"));
        Long templateId = Long.parseLong(request.getParameter("templateId"));
        Integer sprint = Integer.parseInt(request.getParameter("sprint"));
        Integer cardType = Integer.parseInt(request.getParameter("cardType"));
        String cardTitle = request.getParameter("cardTitle");
        String endLife = request.getParameter("endLife");    //上线时间
        //处理可能由于模板新增自定义属性，需要新增卡片自定义属性值
        List<CardAttrParam> creatAttrList  = new LinkedList<CardAttrParam>();
        List<CardAttrResult> cardAttrResultList = cardAuditBiz.findAttrByCardId(cardId);

        if (CollectionUtils.isEmpty(cardAttrResultList)){
            return "forward:/card/submitAttr.htm";
        }

        Map<Long,CardAttrResult> cardAttrMap = new HashMap<Long, CardAttrResult>();
        for (CardAttrResult attr : cardAttrResultList){
            cardAttrMap.put(attr.getAttrId(),attr);
        }

        Map paramMap = request.getParameterMap();

        for (Object key : paramMap.keySet()) {

            String keyStr = (String)key;
            if ("spaceId".equals(keyStr) || "cardId".equals(keyStr) || "templateId".equals(keyStr) || "endLife".equals(keyStr)
                    || "sprint".equals(keyStr) || "cardTitle".equals(keyStr) || "cardType".equals(keyStr)){
                continue;
            }

            String value = "";
            Long attrId = Long.parseLong(keyStr);
            List valueList = Arrays.asList((String[])paramMap.get(key));
            for (Object o : valueList){
                value = value + String.valueOf(o)+",";
            }
            if (StringUtils.isNotEmpty(value)){
                value = value.substring(0,value.length()-1);
            }
            //卡片尚未创建该自定义属性，应属于模板新增了自定义属性，新增
            if (null == cardAttrMap.get(attrId)){
                CardAttrParam cardAttrParam = new CardAttrParam();
                cardAttrParam.setSpaceId(spaceId);
                cardAttrParam.setCardId(cardId);
                cardAttrParam.setTemplateId(templateId);
                cardAttrParam.setAttrId(Long.parseLong(keyStr));
                cardAttrParam.setAttrValue(value);
                creatAttrList.add(cardAttrParam);
                continue;
            }

            if (value.equals(cardAttrMap.get(attrId).getAttrValue())){
                continue;
            }
            CardAttrParam cardAttrParam = new CardAttrParam();
            cardAttrParam.setId(cardAttrMap.get(attrId).getId());
            cardAttrParam.setAttrValue(value);
            cardAttrParam.setCardId(cardId);
            cardAttrParam.setSpaceId(spaceId);
            cardAttrParam.setTemplateId(templateId);
            cardAuditBiz.modifyCardAttr(cardAttrParam);
        }
        //添加模板新增的自定义属性
        if (CollectionUtils.isNotEmpty(creatAttrList)){
            cardAuditBiz.fillCardAttr(creatAttrList);
        }

        CardParam cardParam = new CardParam();
        cardParam.setId(cardId);
        cardParam.setCardTitle(cardTitle);
        cardParam.setSprint(sprint);
        cardParam.setCardType(cardType);
        cardParam.setEndLife(endLife);
        cardAuditBiz.modifyCard(cardParam);

        return "redirect:/space/cardList.htm?spaceId="+spaceId;
    }

    @RequestMapping(value = "modifyCardSingleAttr.htm",method = RequestMethod.POST)
    @ResponseBody
    private String modifyCardSingleAttr(HttpServletRequest request) {
        LOGGER.info("修改卡片单个属性,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        Long cardId = Long.parseLong(request.getParameter("cardId"));
        Long templateId = Long.parseLong(request.getParameter("templateId"));
        Long attrId = Long.parseLong(request.getParameter("attrId"));
        String attrValue = request.getParameter("attrValue");

        List<CardAttrParam> createAttrList  = new LinkedList<CardAttrParam>();//卡片具有哪些属性
        List<CardAttrResult> cardAttrResultList = cardAuditBiz.findAttrByCardId(cardId);//返回卡片所有属性值

        Map<Long,CardAttrResult> cardAttrMap = new HashMap<Long, CardAttrResult>();
        for (CardAttrResult attr : cardAttrResultList){
            cardAttrMap.put(attr.getAttrId(),attr);//将属性和属性ID对应
        }

        if(cardAttrMap.containsKey(attrId)) {
            CardAttrParam cardAttrParam = new CardAttrParam();
            cardAttrParam.setId(cardAttrMap.get(attrId).getId());
            cardAttrParam.setAttrValue(attrValue);
            cardAttrParam.setCardId(cardId);
            cardAttrParam.setSpaceId(spaceId);
            cardAttrParam.setTemplateId(templateId);

            cardAuditBiz.modifyCardAttr(cardAttrParam);
        } else {
            CardAttrParam cardAttrParam = new CardAttrParam();
            cardAttrParam.setSpaceId(spaceId);
            cardAttrParam.setCardId(cardId);
            cardAttrParam.setTemplateId(templateId);
            cardAttrParam.setAttrId(attrId);
            cardAttrParam.setAttrValue(attrValue);
            createAttrList.add(cardAttrParam);

            cardAuditBiz.fillCardAttr(createAttrList);
        }

        return "";
    }

    @RequestMapping(value = "modifyCardBasic.htm",method = RequestMethod.POST)
    @ResponseBody
    private String modifyCardBasic(HttpServletRequest request){

        LOGGER.info("修改基本属性,IP:{}, requestMap:{}", CommonUtil.getRemoteHost(request), JSON.toJSONString(request.getParameterMap()));

        Long cardId = Long.parseLong(request.getParameter("cardId"));
        String sprintStr = request.getParameter("sprint");
        String cardTitle = request.getParameter("cardTitle");

        CardParam cardParam = new CardParam();
        cardParam.setId(cardId);

        if(sprintStr != null) {
            cardParam.setSprint(Integer.parseInt(sprintStr));
        }

        if(cardTitle != null) {
            cardParam.setCardTitle(cardTitle);
        }

        cardAuditBiz.modifyCard(cardParam);

        return "";
    }

    private AbstractTemplate convertTemplateAttrParam2AbstractTemplate(TemplateAttrParam templateAttrParam){

        List<String> valueList = null;
        if (StringUtils.isNotEmpty(templateAttrParam.getInputOption())){
            String []optionList = templateAttrParam.getInputOption().split(",");
            valueList = Arrays.asList(optionList);
        }

        if ("singleText".equals(templateAttrParam.getInputType())){
            return BeanConverter.convertDTO2Entity(templateAttrParam,SingleTextTemplate.class);
        }else if ("checkBox".equals(templateAttrParam.getInputType())){
            CheckBoxTemplate checkBoxTemplate = BeanConverter.convertDTO2Entity(templateAttrParam,CheckBoxTemplate.class);
            checkBoxTemplate.setValueList(valueList);
            return checkBoxTemplate;
        }else if ("radio".equals(templateAttrParam.getInputType())){
            RadioTemplate radioTemplate = BeanConverter.convertDTO2Entity(templateAttrParam,RadioTemplate.class);
            radioTemplate.setValueList(valueList);
            return radioTemplate;
        }else if ("select".equals(templateAttrParam.getInputType())){
            SelectTemplate selectTemplate = BeanConverter.convertDTO2Entity(templateAttrParam,SelectTemplate.class);
            selectTemplate.setValueList(valueList);
            return selectTemplate;
        }else if ("number".equals(templateAttrParam.getInputType())){
            return BeanConverter.convertDTO2Entity(templateAttrParam,NumberTemplate.class);
        }else {
            return BeanConverter.convertDTO2Entity(templateAttrParam,RichTextTemplate.class);
        }
    }

    @RequestMapping(value = "queryCard.htm",method = RequestMethod.GET)
    @ResponseBody
    private String queryCard(HttpServletRequest request){
        String cardIdStr = request.getParameter("cardId");
        if (!StringUtils.isNumeric(cardIdStr)){
            return null;
        }

        Long cardId = Long.parseLong(cardIdStr);

        CardResult cardResult = cardAuditBiz.findById(cardId);
        return JSON.toJSONString(cardResult);
    }

    @RequestMapping(value = "deleteCard.htm",method = RequestMethod.POST)
    @ResponseBody
    private String deleteCard(HttpServletRequest request){
        String cardIdStr = request.getParameter("cardId");
        if (!StringUtils.isNumeric(cardIdStr)){
            return null;
        }

        Long cardId = Long.parseLong(cardIdStr);

        Boolean flag =  cardAuditBiz.deleteCard(cardId);
        if (flag){
            return "SUCCESS";
        }else {
            return "FAIL";
        }
    }


    @RequestMapping(value = "autoCopyToNextSprint.htm",method = RequestMethod.POST)
    @ResponseBody
    private String autoCopyToNextSprint( @RequestParam("spaceId")Long spaceId, @RequestParam("sprint")int sprint,  HttpServletRequest request, HttpServletResponse response){
        if (sprint == -1){
            return WebUtil.getAjaxResult(AjaxResultCodeEnum.PARAMETER_ERROR);
        }
        CardParam cardParam = new CardParam();
        cardParam.setSpaceId(spaceId);
        cardParam.setSprint(sprint);
        cardParam.setPageSize(Integer.MAX_VALUE);

        Page<CardInstance> cardInstancePage = cardAuditBiz.findByPage(cardParam);

        cardParam.setSprint(sprint+1);
        List<CardInstance> nextSprintCardList = cardAuditBiz.findByPage(cardParam).getData();  //下迭代卡片，防止重复复制

        List<Long> pendCopyCardList = new LinkedList<Long>();    //待复制列表
        for (CardInstance cardInstance : cardInstancePage.getData()){
            for (CardInstance nextSprintCard : nextSprintCardList){
                if (cardInstance.getCardTitle().equals(nextSprintCard.getCardTitle())){
                    return WebUtil.getAjaxResult(AjaxResultCodeEnum.BUSINESS_CHECK_ERROR,"下迭代已存在卡片："+cardInstance.getCardTitle());
                }
            }
            if (!"已上线".equals(cardInstance.getLifeStatus())){
                pendCopyCardList.add(cardInstance.getId());
            }
        }
        try{
            cardAuditBiz.copyToTheSprint(pendCopyCardList,sprint+1);
        }catch (Exception ex){
            ex.printStackTrace();
            return WebUtil.getAjaxResult(AjaxResultCodeEnum.SYSTEM_ERROR);
        }
        return WebUtil.getAjaxResult(AjaxResultCodeEnum.SUCCESS);
    }

}
