package com.wednesday.kanban.web.Index.Controller.vo;

import com.wednesday.kanban.common.result.CardAttrResult;
import com.wednesday.kanban.common.result.CardResult;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.web.Index.Controller.common.ReflectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.*;

public class CardInfo extends CardResult implements Serializable {
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String attr5;
    private String attr6;
    private String attr7;
    private String attr8;
    private String attr9;
    private String attr0;

    private final static Map<String,Integer> mapping = new HashMap<String, Integer>();

    static {
        mapping.put("优先级",3);
        mapping.put("实用点数",5);
        mapping.put("RD估点",7);
        mapping.put("QA估点",6);
        mapping.put("估点",1);
        mapping.put("开发负责人",0);
        mapping.put("测试负责人",2);
        mapping.put("详细描述",4);
    }

    public CardInfo() {
    }

    public static List<CardInfo> convertList(List<CardResult> list) {
        List<CardInfo> arr = new ArrayList<CardInfo>(list.size());
        for (CardResult cardResult : list) {
            arr.add(convert(cardResult));
        }
        return arr;
    }

    public static CardInfo convert(CardResult result) {
        CardInfo info = new CardInfo();
        BeanConverter.copyProperties(info, result);
        for(CardAttrResult arr : result.getCardAttrList()){
            try {
                int i = mapping.get(arr.getAttrName());
                ReflectionUtils.invokeSetterMethod(info, "attr" + i, arr.getAttrValue());
            }catch (Exception e) {
                continue;
            }
        }
        return info;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    public String getAttr6() {
        return attr6;
    }

    public void setAttr6(String attr6) {
        this.attr6 = attr6;
    }

    public String getAttr7() {
        return attr7;
    }

    public void setAttr7(String attr7) {
        this.attr7 = attr7;
    }

    public String getAttr8() {
        return attr8;
    }

    public void setAttr8(String attr8) {
        this.attr8 = attr8;
    }

    public String getAttr9() {
        return attr9;
    }

    public void setAttr9(String attr9) {
        this.attr9 = attr9;
    }

    public String getAttr0() {
        return attr0;
    }

    public void setAttr0(String attr0) {
        this.attr0 = attr0;
    }
}
