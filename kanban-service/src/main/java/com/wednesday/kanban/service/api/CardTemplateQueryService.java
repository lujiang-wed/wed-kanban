package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.param.TemplateAttrParam;
import com.wednesday.kanban.domain.CardTemplate;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.domain.TemplateAttrIndex;

import java.util.List;

/**
 * Created by wyzhangdong on 2014/12/10.
 * 卡片模板查询操作的service
 */
public interface CardTemplateQueryService {

    /**
     * 根据卡面属性id，获取卡片模板基本属性。
     * 不包括其自定义属性
     * 包括已删除的
     * @param templateId
     * @return
     */
    public CardTemplate getCardTemplateById(Long templateId);


    /**
     * 根据卡片模板自定义属性id,获取卡片自自定义属性
     * 包括已删除的
     * @param templateAttrId
     * @return
     */
    public CardTemplateAttr getCardTemplateAttrById(long templateAttrId);


    /**
     * 根据空间id,获取此空间内的所以卡片模版
     * 暂时不支持分页
     * @param spaceId
     * @return
     */
    public List<CardTemplate> getCardTemplatesBySpaceId(long spaceId);


    /**
     * 根据父模版id,获取模版
     * 暂时不支持分页
     * @param templateId
     * @return
     */
    public List<CardTemplate> getCardTemplatesByParentId(long templateId);

    /**
     * 根据模版id,获取该模板的自定义属性
     * @param templateId
     * @return
     */
    public List<CardTemplateAttr> getCardTemplateAttrsByTemplateId(long templateId);

    /**
     * 在template_attr_for_index表中更新排序数据
     * @param templateId
     * @param templateParam
     * @return
     */
    public int updateTemplateAttrIndexByTemplateId(Long templateId, TemplateAttrParam templateParam);
    /**
     * 在template_attr_for_index表中更新过滤数据
     * @param templateId
     * @param templateParam
     * @return
     */
    public int updateTemplateAttrShowByTemplateId(Long templateId, TemplateAttrParam templateParam);

    /**
     * 从template_attr_for_index表中得到数据
     * @param templateId
     * @return
     */
    public List<TemplateAttrIndex> getTemplateAttrIndexService(long templateId);

}
