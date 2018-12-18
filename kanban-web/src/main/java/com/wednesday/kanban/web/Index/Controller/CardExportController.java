package com.wednesday.kanban.web.Index.Controller;

import com.wednesday.kanban.biz.api.CardAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.common.result.CardAttrResult;
import com.wednesday.kanban.common.result.CardResult;
import com.wednesday.kanban.domain.Perm;
import com.wednesday.kanban.web.Index.Controller.permission.Permission;
import com.wednesday.kanban.web.Index.Controller.vo.CardInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CardExportController extends ExcelController<CardInfo, Void, CardParam> {

    @Resource
    CardAuditBiz cardAuditBiz;

    public CardExportController() {
        super("card.properties", "卡片墙信息汇总");
    }

    @Override
    protected Void[] getStaticResult(HttpServletRequest request, CardParam param) {
        return new Void[0];
    }

    @Override
    protected List<CardInfo> getResults(HttpServletRequest request, CardParam param) {
        param.setPageSize(Integer.MAX_VALUE);
        Page<CardResult> list = cardAuditBiz.findAll(param);
        String cardDesc = "";
        for (CardResult item : list.getData()) {
            for (CardAttrResult tmp : item.getCardAttrList()) {
                if ("需求概要".equals(tmp.getAttrName())) {
                    cardDesc = tmp.getAttrValue();
                }
            }
            item.setCardDesc(cardDesc);
        }
        return CardInfo.convertList(list.getData());
    }

    @RequestMapping(value = "/card/export", method = RequestMethod.GET)
    @Permission(Perm.ALL)
    public void excel(HttpServletRequest request, HttpServletResponse response) {
        CardParam param = new CardParam();
        param.setTemplateId(Long.valueOf(request.getParameter("templateId")));
        param.setSprint(Integer.valueOf(request.getParameter("sprintId")));
        param.setSpaceId(Long.valueOf(request.getParameter("spaceId")));
        if (Integer.valueOf(request.getParameter("cardType")) != -1) {
            param.setCardType(Integer.valueOf(request.getParameter("cardType")));
        }
        outputExcel(request, response, false, param);
    }
}
