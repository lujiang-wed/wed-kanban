package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.common.param.TemplateAttrParam;
import com.wednesday.kanban.common.param.TemplateParam;
import com.wednesday.kanban.domain.CardTemplateAttr;
import com.wednesday.kanban.domain.TemplateAttrIndex;

import java.util.List;

public interface CardTemplateBiz {

    /**
     * 获取模板
     *
     * @param templateId
     * @return
     * TemplateParam 模板的全部信息，包括模板属性和自定义的属性
     * 无：返回 null
     */
    public TemplateParam  getTemplateParam(long templateId);




    /**
     * 获取空间内的所有模属性
     * @param spaceId
     * @return List<TemplateParam>
     */
    public List<TemplateParam> getTemplateParamBySpaceId(long spaceId);

    /**
     * 根据属性ID获取属性相关信息
     * @param id
     * @return
     */
    public CardTemplateAttr getCardTemplateAttrById(long id);

    /**
     * 根据模版id获取模版的子模板
     * @param templageId
     * @return
     */
    public List<TemplateParam> getTemplateParamByParent(long templageId);


    /**
     * 修改模版属性
     * @param templateParam
     * @return
     */
    public boolean updateTemplate(TemplateParam templateParam);

    /**
     * 修改模板的自定义属性
     * @param templateAttrParam
     * @return
     */
    public boolean updateTemplateAttr(TemplateAttrParam templateAttrParam);


    /**
     * 添加卡片模版
     * 传入参数：
     *  spaceId 空间id 不能为空
     *  cardType 卡片类型
     *  parentTemplate 父模版id
     *  attrList  自定义属性
     * @return
     */
    public TemplateParam addTemplateParam(TemplateParam templateParam);

    /**
     * 添加卡片模版的自定义属性
     * 传入参数
     *
     templateId 所属模板ID  非空
     attrLabel 属性label    非空
     inputType 空间类型     非空
     inputOption 空间选项
     defaultValue  默认值
     * @param templateAttrParam
     * @return
     */

    public TemplateAttrParam addTemplateAttrParam (TemplateAttrParam templateAttrParam);


    /**
     * 删除模版属性
     * @param templateAttrId
     * @return
     */
    public Boolean delTemplateAttrParam(Long templateAttrId);

    /**
     * 删除template_attr_for_index的记录
     * @param templateId
     * @param templateAttrId
     */

    public void delTemplateAttrIndex(Long templateId, Long templateAttrId);

    /**
     * 删除模板及其自定义属性
     * @param templateId
     * @return
     */
    public Boolean delTemplate(Long templateId);


    /**
     * 判断输入的字符是否为数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str);

    /**
     * 更新排序值
     * @param templateId
     * @return
     */
    public int updateTemplateAttrIndex(Long templateId,TemplateParam templateParam) ;
    /**
     * 更新过滤值
     * @param templateId
     * @return
     */
    public int updateTemplateAttrShow(Long templateId,TemplateParam templateParam) ;

    /**
     * 从表template_attr_for_index里面得到数据的index和show信息
     * @param templateId
     * @return
     */
    public  List<TemplateAttrIndex> getTemplateAttrIndex(Long templateId);

    /**
     * 展示时对属性列表进行排序和过滤
     * @param templateId
     * @return
     */
    public TemplateParam filterTemplateAttrList(Long templateId);

}
