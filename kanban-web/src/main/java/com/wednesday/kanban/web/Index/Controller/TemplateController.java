package com.wednesday.kanban.web.Index.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wednesday.kanban.biz.api.CardAuditBiz;
import com.wednesday.kanban.biz.api.CardTemplateBiz;
import com.wednesday.kanban.biz.api.SpaceAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.common.param.TemplateAttrParam;

import com.wednesday.kanban.common.param.TemplateParam;
import com.wednesday.kanban.common.result.SpaceResult;
import com.wednesday.kanban.domain.CardInstance;
import com.wednesday.kanban.domain.TemplateAttrIndex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("template")
public class TemplateController extends BaseController{

    @Resource
    SpaceAuditBiz spaceAuditBiz;

    @Resource
    CardTemplateBiz cardTemplateBiz;

    @Resource
    CardAuditBiz cardAuditBiz;

    @Resource
    SpaceAuditBiz spaceBiz;

    @RequestMapping(value = "getTemplateNameList",method = RequestMethod.GET)
    @ResponseBody
    public String getTemplateNameList(HttpServletRequest request){
        String spaceIdStr=request.getParameter("spaceId");
        if (!StringUtils.isNumeric(spaceIdStr)){
            return null;
        }
        List<TemplateParam> templateList = cardTemplateBiz.getTemplateParamBySpaceId(Long.parseLong(spaceIdStr));

        return JSON.toJSONString(templateList);
    }

    @RequestMapping(value={"add"},method= RequestMethod.GET)
    public String add(HttpServletRequest request, HttpServletResponse response,Model model){
        String spaceIdStr=request.getParameter("spaceId");
        SpaceResult space=null;
        if(StringUtils.isNumeric(spaceIdStr)){
            space= spaceBiz.findOne(Long.valueOf(spaceIdStr));
        }
        if(space==null){
            return "redirect:/space";
        }
        model.addAttribute("spaceId",space.getId());
        if(space.getParentSpace()>0){
            List<TemplateParam> parentTemplateList=cardTemplateBiz.getTemplateParamBySpaceId(space.getParentSpace());
            model.addAttribute("parentTemplateList",parentTemplateList);
        }
        return "template/add";
    }

    @RequestMapping(value={"add"},method= RequestMethod.POST)
    public String addTemplate(HttpServletRequest request, HttpServletResponse response,Model model){
        TemplateParam param=new TemplateParam();
        super.bindRequestParam(request,param);
        cardTemplateBiz.addTemplateParam(param);
        return "redirect:/space/showSpace.htm?spaceId="+param.getSpaceId();
    }

    @ResponseBody
    @RequestMapping(value={"del"},method = RequestMethod.POST)
    public String del(HttpServletRequest request, HttpServletResponse response,Model model){
        String templateIdStr=request.getParameter("id");
        if(!StringUtils.isNumeric(templateIdStr)){
            return "param_error";
        }
        Long templateId = Long.parseLong(templateIdStr);

        CardParam cardParam = new CardParam();
        cardParam.setTemplateId(templateId);
        Page<CardInstance> result =  cardAuditBiz.findByPage(cardParam);
        if (null == result){
            return "SYSTEM_ERROR";
        }
        //无卡片使用该模板，可以删除
        if (CollectionUtils.isEmpty(result.getData())){
            cardTemplateBiz.delTemplate(templateId);
            return "SUCCESS";
        }
        //有使用，无法删除
        return "EXIST";
    }

    @RequestMapping(value={"attr"})
    public String attr(HttpServletRequest request, HttpServletResponse response,Model model){
        String templateIdStr=request.getParameter("templateId");
        model.addAttribute("templateIdStr",templateIdStr);

        if(StringUtils.isNumeric(templateIdStr)){
            TemplateParam templateParam = cardTemplateBiz.getTemplateParam(Long.valueOf(templateIdStr));
            List<TemplateAttrParam> parentAttrList = templateParam.getParentAttrList();
            List<TemplateAttrParam> attrList = templateParam.getAttrList();
            List<TemplateAttrIndex> templateAttrIndexList = cardTemplateBiz.getTemplateAttrIndex(Long.valueOf(templateIdStr));//数据库里面获取排序值
            // 父模板放入index和show
            if(parentAttrList != null) {
                for (TemplateAttrParam param : parentAttrList) {
                    for (TemplateAttrIndex indexParam : templateAttrIndexList) {
                        if (indexParam.getAttrLabel().equals(param.getAttrLabel())) {
                            param.setAttrLabelIndex(indexParam.getIndex());
                            param.setIsShow(indexParam.getShow());
                            break;
                        }
                    }
                }
            }
            //子模板放入index和show
            if(attrList != null) {
                for (TemplateAttrParam param : attrList) {
                    for (TemplateAttrIndex indexParam : templateAttrIndexList) {
                        if (indexParam.getAttrLabel().equals(param.getAttrLabel())) {
                            param.setAttrLabelIndex(indexParam.getIndex());
                            param.setIsShow(indexParam.getShow());
                            break;
                        }
                    }
                }
            }
            templateParam.setAttrList(attrList);
            templateParam.setParentAttrList(parentAttrList);

            if(templateParam != null){
                model.addAttribute("template",templateParam);
            }
            return "template/attr";
        }
        // cardTemplateBiz.addTemplateParam(param);
        return "redirect:/space/showSpace.htm?spaceId=";
    }

    @RequestMapping(value={"queryAttrList"})
    @ResponseBody
    public String queryAttrList(HttpServletRequest request, HttpServletResponse response,Model model){
        String templateIdStr=request.getParameter("templateId");
        Long templateId = Long.valueOf(templateIdStr);
        if (null == templateIdStr || !StringUtils.isNumeric(templateIdStr)){
            return null;
        }
//        TemplateParam templateParam = cardTemplateBiz.getTemplateParam(templateId);
        TemplateParam templateParam = cardTemplateBiz.filterTemplateAttrList(templateId);

        return JSON.toJSONString(templateParam);
    }


    @RequestMapping(value={"sortAttrList"})
    public String sortAttrList(HttpServletRequest request, HttpServletResponse response,Model model){
        String templateIdStr=request.getParameter("templateId");
        if (null == templateIdStr || !StringUtils.isNumeric(templateIdStr)){
            return null;
        }
        if(StringUtils.isNumeric(templateIdStr)) {
            TemplateParam templateParam = cardTemplateBiz.getTemplateParam(Long.valueOf(templateIdStr));
            List<TemplateAttrParam> parentAttrList = templateParam.getParentAttrList();
            List<TemplateAttrParam> attrList = templateParam.getAttrList();
            if(parentAttrList != null)
            {
                for(TemplateAttrParam param : parentAttrList) {
                    String name = param.getAttrLabel();
                    String index = request.getParameter(name);
                    if(!cardTemplateBiz.isNumeric(index)){
                        return "template/attrError";
                    };
                    param.setAttrLabelIndex(Long.parseLong(index));;
                }
            }
            if(attrList != null)
            {
                for(TemplateAttrParam param : attrList) {
                    String name = param.getAttrLabel();
                    String index = request.getParameter(name);
                    if(!cardTemplateBiz.isNumeric(index)){
                        return "template/attrError";
                    };
                    param.setAttrLabelIndex(Long.parseLong(index));
                }
            }
            cardTemplateBiz.updateTemplateAttrIndex(Long.valueOf(templateIdStr),templateParam);
            Long spaceId = templateParam.getSpaceId();
            model.addAttribute("spaceId", spaceId);
            SpaceResult space = spaceAuditBiz.findOne(spaceId);
            model.addAttribute("spaceId", space.getId());
            model.addAttribute("spaceName", space.getSpaceName());
            model.addAttribute("templateList", space.getCardTemplateList());
        }
        return "card/index1";
    }
    @RequestMapping(value={"filterAttrList"})
    public String filterAttrList(HttpServletRequest request, HttpServletResponse response,Model model){
        String templateIdStr=request.getParameter("templateId");
        if (null == templateIdStr || !StringUtils.isNumeric(templateIdStr)){
            return null;
        }
        if(StringUtils.isNumeric(templateIdStr)) {
            TemplateParam templateParam = cardTemplateBiz.getTemplateParam(Long.valueOf(templateIdStr));
            List<TemplateAttrParam> parentAttrList = templateParam.getParentAttrList();
            List<TemplateAttrParam> attrList = templateParam.getAttrList();
            if(parentAttrList != null)
            {
                //首先把所有属性默认为不显示，设置show为0
                for(TemplateAttrParam param : parentAttrList){
                    param.setIsShow(0);
                }
                for(TemplateAttrParam param : parentAttrList) {
                    Long name = param.getId();
                    String stringName = String.valueOf(name);
                    String isShow = request.getParameter(stringName);
                    if(isShow == null){
                        continue;
                    }
                    else{
                        param.setIsShow(1);
                    }
                }
                templateParam.setParentAttrList(parentAttrList);
            }
            if(attrList != null) {
                //首先把所有属性默认为不显示，设置show为0
                for(TemplateAttrParam param : attrList){
                    param.setIsShow(0);
                }
                for(TemplateAttrParam param : attrList) {
                    Long name = param.getId();
                    String stringName = String.valueOf(name);
                    String isShow = request.getParameter(stringName);
                    if(isShow == null){
                        continue;
                    }
                    else{
                        param.setIsShow(1);
                    }
                }
                templateParam.setAttrList(attrList);
            }

            cardTemplateBiz.updateTemplateAttrShow(Long.valueOf(templateIdStr),templateParam);
            Long spaceId = templateParam.getSpaceId();
            model.addAttribute("spaceId", spaceId);
            SpaceResult space = spaceAuditBiz.findOne(spaceId);
            model.addAttribute("spaceId", space.getId());
            model.addAttribute("spaceName", space.getSpaceName());
            model.addAttribute("templateList", space.getCardTemplateList());
        }
        return "card/index1";
    }


    @RequestMapping(value={"attr/add"})
    @ResponseBody
    public String addAttr(HttpServletRequest request, HttpServletResponse response,Model model){
        TemplateAttrParam param =new TemplateAttrParam();
        super.bindRequestParam(request,param);
        TemplateAttrParam result=cardTemplateBiz.addTemplateAttrParam(param);

        JSONObject js=new JSONObject();
        if(result!=null){
            js.put("code",1);
            js.put("data",result);
            return js.toString();
        }
        js.put("code",-1);
        // cardTemplateBiz.addTemplateParam(param);
        return js.toString();
    }

    @RequestMapping(value={"attr/update"})
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response,Model model){
        TemplateAttrParam param =new TemplateAttrParam();
        super.bindRequestParam(request,param);
        Boolean result=cardTemplateBiz.updateTemplateAttr(param);
        JSONObject js=new JSONObject();
        if(result!=null){
            js.put("code",1);
            return js.toString();
        }
        js.put("code",-1);
        // cardTemplateBiz.addTemplateParam(param);
        return js.toString();
    }

    @RequestMapping(value={"attr/del"})
    @ResponseBody
    public String delAttr(HttpServletRequest request, HttpServletResponse response,Model model){
        String templateAttrIdStr=request.getParameter("id");
        String templateIdStr=request.getParameter("templateId");
//        System.out.println(templateIdStr);
        JSONObject js=new JSONObject();
        js.put("code",-1);
        if(StringUtils.isNumeric(templateAttrIdStr) && StringUtils.isNumeric(templateIdStr)){
            Boolean result= cardTemplateBiz.delTemplateAttrParam(Long.valueOf(templateAttrIdStr));
            cardTemplateBiz.delTemplateAttrIndex(Long.valueOf(templateIdStr),Long.valueOf(templateAttrIdStr));
            if(result!=null&&result){
                js.put("code",1);
            }
        }
        return js.toString();
    }






    @RequestMapping(value = "initTemplateList",method = RequestMethod.GET)
    @ResponseBody
    public String initTemplateList(HttpServletRequest request, HttpServletResponse response,Model model){
        String spaceId=request.getParameter("spaceId");
        model.addAttribute("spaceId",spaceId);
        return "template/add";
    }


    public static void main(String [] args){
        TemplateAttrParam result=new TemplateAttrParam();
        result.setId(11l);
        result.setTemplateId(12l);
        JSONObject js=new JSONObject();
        js.put("code",1);
        js.put("data",result) ;
        System.out.println(js.toString());

    }

}
