package com.wednesday.kanban.service.api;

import com.wednesday.kanban.domain.CardTemplate;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.domain.TemplateAttrIndex;

import java.util.List;

public interface CardTemplateService {

    /**
     * 添加卡片模版
     * @param cardTemplate
     *    spaceId 不能为空
     */
    public void addCardTemplate(CardTemplate cardTemplate);

    /**
     * 添加卡片模版自定义属性
     * @param cardTemplateAttr
     *      templateId 不允许为空
     *      attrLabel 不允许为空
     *      inputType 不允许为空
     *
     */
    public void addCardTemplateAttr(CardTemplateAttr cardTemplateAttr);


    /**
     * 修改卡片模版
     * @param cardTemplate
     *          id不能为空
     */
    public void updateCardTemplate(CardTemplate cardTemplate);

    /**
     * 修改卡片模板的自定义属性
     *      inputType修改无效
     * @param cardTemplateAttr
     */
    public void updateCardTemplateAttr(CardTemplateAttr cardTemplateAttr);

    /**
     *  删除卡片模板
     * @param templateId
     */
    public void deleteCardTemplate(Long templateId);

    /**
     * 删除卡片模板的自定义属性
     * @param cardTemplateAttrId
     */
    public void deleteCardTemplateAttr(Long cardTemplateAttrId);

    /**
     * 删除表template_attr_for_index中的属性记录
     * @param templateAttrIndex
     */
   public void deleteTemplateAttrIndex(TemplateAttrIndex templateAttrIndex);
    /**
     * 根据模板ID和属性ID找到属性label

     * @param templateAttrId
     * @return
     */
    public CardTemplateAttr getCardAttr(Long templateAttrId);
   /**
    * 在表template_attr_for_index中添加模板属性记录
    * @param templateAttrIndex
     */
    public void addTemplateAttrIndex(TemplateAttrIndex templateAttrIndex);

    /**
     * 根据模板ID找他的子模板
     * @param parentTemplate
     * @return
     */
    public List<Long> getChildTemplateId(Long parentTemplate);

    /**
     * 根据模板ID获得他的父模板
     * @param id
     * @return
     */
    public Long getParentTemplateId(Long id);
}
