package com.wednesday.kanban.service;

import com.wednesday.kanban.common.param.TemplateAttrParam;
import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.CardTemplate;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.domain.TemplateAttrIndex;
import com.wednesday.kanban.service.api.CardTemplateQueryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("cardTemplateQueryService")
public class CardTemplateQueryServiceImpl implements CardTemplateQueryService {

    @Resource
    private SuperDAO superDAO;

    @Override
    public CardTemplate getCardTemplateById(Long id) {
        return  superDAO.getObject("CardTemplateMapper.selectByPrimaryKey", id);
    }

    @Override
    public CardTemplateAttr getCardTemplateAttrById(long id) {
        return  superDAO.getObject("CardTemplateAttrMapper.selectByPrimaryKey",id);
    }

    @Override
    public List<CardTemplate> getCardTemplatesBySpaceId(long spaceId) {
        return  superDAO.getList("CardTemplateMapper.selectBySpace", spaceId);
    }

    @Override
    public List<CardTemplate> getCardTemplatesByParentId(long templateId) {
        return  superDAO.getList("CardTemplateMapper.selectByParent", templateId);
    }

    @Override
    public List<CardTemplateAttr> getCardTemplateAttrsByTemplateId(long templateId) {
        return  superDAO.getList("CardTemplateAttrMapper.selectByTempleteId", templateId);
    }


    @Override
    public int updateTemplateAttrIndexByTemplateId(Long templateId, TemplateAttrParam templateParam) {
        TemplateAttrIndex templateAttrIndex = new TemplateAttrIndex();
        templateAttrIndex.setTemplateId(templateId);
        templateAttrIndex.setAttrLabel(templateParam.getAttrLabel());
        templateAttrIndex.setIndex(templateParam.getAttrLabelIndex());
        return  superDAO.update("TemplateAttrIndexMapper.updateIndexById",templateAttrIndex);
    }

    @Override
    public int updateTemplateAttrShowByTemplateId(Long templateId, TemplateAttrParam templateParam) {
        TemplateAttrIndex templateAttrIndex = new TemplateAttrIndex();
        templateAttrIndex.setTemplateId(templateId);
        templateAttrIndex.setAttrLabel(templateParam.getAttrLabel());
        templateAttrIndex.setShow(templateParam.getIsShow());
        return  superDAO.update("TemplateAttrIndexMapper.updateShowById",templateAttrIndex);
    }

    @Override
    public List<TemplateAttrIndex> getTemplateAttrIndexService(long templateId) {
        return superDAO.getList("TemplateAttrIndexMapper.selectById", templateId) ;
    }


}
