package com.wednesday.kanban.service;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.CardAttrQueryParam;
import com.wednesday.kanban.common.param.CardParam;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;
import com.wednesday.kanban.service.api.CardQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("cardQueryService")
public class CardQueryServiceImpl implements CardQueryService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public CardInstance findById(Long id) {
        return superDAO.getObject("CardInstanceMapper.findById",id);
    }

    @Override
    public Page<CardInstance> findByPage(CardParam cardParam) {
        return superDAO.queryPagination("CardInstanceMapper.findByPage",cardParam);
    }

    @Override
    public CardAttrInstance findAttrById(Long id) {
        return superDAO.getObject("CardAttrInstanceMapper.findAttrById",id);
    }

    @Override
    public List<CardAttrInstance> findAttrByCardId(Long cardId) {
        return superDAO.getList("CardAttrInstanceMapper.findAttrByCardId",cardId);
    }

    @Override
    public Map<Long,List<CardAttrInstance>> batchFindAttrByCardId(List<Long> cardIdList) {
        List<CardAttrInstance> cardAttrInstanceList = superDAO.getList("CardAttrInstanceMapper.batchFindAttrByCardId",cardIdList);
        Map<Long,List<CardAttrInstance>> cardAttrMap = new HashMap<Long, List<CardAttrInstance>>();
        for (CardAttrInstance cardAttrInstance : cardAttrInstanceList){
            if (cardAttrMap.containsKey(cardAttrInstance.getCardId())){
                cardAttrMap.get(cardAttrInstance.getCardId()).add(cardAttrInstance);
            }else {
                List<CardAttrInstance> attrList = new LinkedList<CardAttrInstance>();
                attrList.add(cardAttrInstance);
                cardAttrMap.put(cardAttrInstance.getCardId(),attrList);
            }
        }
        return cardAttrMap;
    }

    @Override
    public Page<Long> findAttrByPage(CardAttrQueryParam cardAttrQueryParam) {
        return superDAO.queryPagination("CardAttrInstanceMapper.findAttrByPage",cardAttrQueryParam);
    }

    @Override
    public List<CardAttrInstance> findSpaceAllValue(CardAttrQueryParam cardAttrQueryParam) {
        return superDAO.getList("CardAttrInstanceMapper.findSpaceAllValue",cardAttrQueryParam);
    }

    @Override
    public Page<CardInstance> statisticsCompleteCard(CardParam cardParam){
        return superDAO.queryPagination("CardInstanceMapper.statisticsCompleteCard",cardParam);
    }

    @Override
    public Integer findSpaceMaxSprint(Long spaceId) {
        return superDAO.getObject("CardInstanceMapper.findSpaceMaxSprint",spaceId);
    }
}
