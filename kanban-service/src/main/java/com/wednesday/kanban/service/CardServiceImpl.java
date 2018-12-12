package com.wednesday.kanban.service;

import com.wednesday.kanban.common.param.CopyCardParam;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.CardAttrInstance;
import com.wednesday.kanban.domain.CardInstance;
import com.wednesday.kanban.service.api.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("cardService")
public class CardServiceImpl implements CardService {

    @Autowired
    private SuperDAO superDAO;

    @Override
    public Long addCard(CardInstance cardInstance) {
        superDAO.insert("CardInstanceMapper.addCardInstance",cardInstance);
        return cardInstance.getId();
    }

    @Override
    public void modifyCard(CardInstance cardInstance) {
        superDAO.update("CardInstanceMapper.modifyCardInstance",cardInstance);
    }

    @Override
    public void deleteCard(Long id) {
        superDAO.update("CardInstanceMapper.deleteCardInstance",id);
    }

    @Override
    public void addCardAttr(CardAttrInstance cardAttrInstance) {
        superDAO.insert("CardAttrInstanceMapper.addCardAttrInstance",cardAttrInstance);
    }

    @Override
    public void batchAddCardAttr(List<CardAttrInstance> cardAttrList) {
        superDAO.insert("CardAttrInstanceMapper.batchAddCardAttr",cardAttrList);
    }

    @Override
    public void modifyCardAttr(CardAttrInstance cardAttrInstance) {
        superDAO.update("CardAttrInstanceMapper.modifyCardAttrInstance",cardAttrInstance);
    }

    @Override
    public Long copyCard(CopyCardParam copyCardParam) {
        superDAO.insert("CardInstanceMapper.copyCardInstance",copyCardParam);
        return copyCardParam.getId();
    }
}
