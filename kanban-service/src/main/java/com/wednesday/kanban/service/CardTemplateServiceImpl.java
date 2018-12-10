package com.wednesday.kanban.service;

import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.CardTemplate;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.domain.TemplateAttrIndex;
import com.wednesday.kanban.service.api.CardTemplateService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyzhangdong on 2014/12/10.
 */
@Component("cardTemplateService")
public class CardTemplateServiceImpl implements CardTemplateService {

    @Resource
    private SuperDAO superDAO;


    @Override
    public void addCardTemplate(CardTemplate cardTemplate) {
        superDAO.insert("CardTemplateMapper.insert",cardTemplate);
    }

    @Override
    public void addTemplateAttrIndex(TemplateAttrIndex templateAttrIndex){
        superDAO.insert("TemplateAttrIndexMapper.insert",templateAttrIndex);
    }

    @Override
    public List<Long> getChildTemplateId(Long parentTemplate) {
        return superDAO.getList("CardTemplateMapper.selectChildTemplate",parentTemplate);
    }

    @Override
    public Long getParentTemplateId(Long id) {
        return superDAO.getObject("CardTemplateMapper.selectParentTemplate", id);
    }

    @Override
    public void addCardTemplateAttr(CardTemplateAttr cardTemplateAttr) {
        superDAO.insert("CardTemplateAttrMapper.insert",cardTemplateAttr);
    }

    @Override
    public void updateCardTemplate(CardTemplate cardTemplate) {
        superDAO.insert("CardTemplateMapper.updateByPrimaryKeySelective",cardTemplate);
    }

    @Override
    public void updateCardTemplateAttr(CardTemplateAttr cardTemplateAttr) {
        superDAO.insert("CardTemplateAttrMapper.updateByPrimaryKeySelective",cardTemplateAttr);
    }

    @Override
    public void deleteCardTemplate(Long id) {
        superDAO.insert("CardTemplateMapper.deleteByPrimaryKey",id);
    }

    @Override
    public void deleteCardTemplateAttr(Long id) {
        superDAO.insert("CardTemplateAttrMapper.deleteByPrimaryKey",id);
    }

    @Override
    public void deleteTemplateAttrIndex(TemplateAttrIndex templateAttrIndex) {
        superDAO.delete("TemplateAttrIndexMapper.deleteByPrimaryKey", templateAttrIndex);
    }

    @Override
    public CardTemplateAttr getCardAttr( Long templateAttrId) {
        return superDAO.getObject("CardTemplateAttrMapper.selectByPrimaryKey",templateAttrId);
    }

}
