package com.wednesday.kanban.biz;

import com.wednesday.kanban.biz.api.AuthAuditBiz;
import com.wednesday.kanban.biz.api.AuthorityContentBiz;
import com.wednesday.kanban.biz.api.CardTemplateBiz;
import com.wednesday.kanban.biz.api.SpaceAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.TemplateParam;
import com.wednesday.kanban.common.utils.BeanConverter;
import com.wednesday.kanban.domain.Auth;
import com.wednesday.kanban.domain.Space;
import com.wednesday.kanban.common.param.SpaceQueryParam;
import com.wednesday.kanban.common.result.SpaceResult;
import com.wednesday.kanban.service.api.SpaceQueryService;
import com.wednesday.kanban.service.api.SpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/9 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
@Component("SpaceAuditBiz")
public class SpaceAuditBizImpl implements SpaceAuditBiz {

    private static final Logger logger = LoggerFactory.getLogger(SpaceAuditBizImpl.class);

    @Resource
    private SpaceService        spaceService;
    @Resource
    private SpaceQueryService   spaceQueryService;
    @Resource
    private CardTemplateBiz     cardTemplateBiz;
    @Resource
    private AuthorityContentBiz authorityContentBiz;
    @Resource
    AuthAuditBiz authAuditBiz;
    @Override
    public String addSpace(Space space,String user) {
        if (null == space || null == space.getSpaceName() || null == space.getSpacePin() || null == space.getParentSpace()|| null == user|| "" == user.trim()){
            logger.error("参数异常:{}",space);
            return "FAIL";
        }
        Space parentSpace = spaceQueryService.findOne(space.getParentSpace());
        if (null == parentSpace){
            logger.error("父空间不存在！");
            return "FAIL";
        }

        Long spaceId = spaceService.addSpace(space);
        if (null == spaceId){
            logger.error("插入空间失败！");
            return "FAIL";
        }

        //获取父空间模板,并新建子空间对应模板
        List<TemplateParam> parentTemplateList = cardTemplateBiz.getTemplateParamBySpaceId(parentSpace.getId());
        if(parentTemplateList != null){
            for (TemplateParam parentTemplate : parentTemplateList){
                TemplateParam templateParam = new TemplateParam();
                templateParam.setSpaceId(spaceId);
                templateParam.setParentTemplate(parentTemplate.getId());
                templateParam.setCardType(parentTemplate.getCardType());
                templateParam.setName(parentTemplate.getName());
                cardTemplateBiz.addTemplateParam(templateParam);
            }
        }

        //插入权限表，给新建者管理员权限
        Auth auth = new Auth();
        auth.setPrivilege("s");
        auth.setUser(user);
        auth.setSid(spaceId);
        try {
            authAuditBiz.addAuth(auth);

        }catch (Exception e){
            e.printStackTrace();
            return "该用户和空间对应的权限已存在，请联系管理员：wyyangyang1";
        }

        return "SUCCESS";
    }

    @Override
    public String modifySpace(Space space) {
        if (null == space || null == space.getId()){
            logger.error("参数异常:{}",space);
            return "FAIL";
        }

        //修改空间名字时，需要删除权限JSON串中，所有包含此空间的字段，当其再次读取权限列表时，会重新填充权限JSON表
        if (null != space.getSpaceName()){
            authorityContentBiz.deleteContentBySpaceId(space.getId());
        }

        //仅修改基础属性
        spaceService.modifySpace(space);
        return "SUCCESS";
    }

    @Override
    public String deleteSpace(Long id) {
        if (null == id){
            logger.error("参数异常");
            return "FAIL";
        }
        //检索是否包含子空间
        List<Long> sonSpaceList = findSonSpace(id);
        if (null != sonSpaceList && sonSpaceList.size()>0){
            logger.error("含有{}个子空间",sonSpaceList.size());
            //TODO：修改子空间（如何修改）
            return "FAIL";
        }

        //删除包含此空间用的权限JSON串
        authorityContentBiz.deleteContentBySpaceId(id);
        spaceService.deleteSpace(id);
        return "SUCCESS";
    }

    @Override
    public SpaceResult findOne(Long id) {
        if (null == id){
            logger.error("参数异常");
            return null;
        }
        Space space = spaceQueryService.findOne(id);
        if (null == space){
            logger.error("无此ID:{}对应的数据",id);
            return null;
        }
        SpaceResult spaceResult = obtainInformation(space);
        return spaceResult;
    }

    @Override
    public List<Long> findSonSpace(Long parentSpace) {
        if (null == parentSpace){
            logger.error("参数异常");
            return null;
        }
        List<Long> list = spaceQueryService.findSonSpace(parentSpace);
        return list;
    }

    @Override
    public Page<SpaceResult> findByPage(SpaceQueryParam spaceQueryParam) {
        Page<Space> spacePage = spaceQueryService.findByPage(spaceQueryParam);

        Page<SpaceResult> resultPage = new Page<SpaceResult>();
        resultPage.setPageSize(spacePage.getPageSize());
        resultPage.setPageNo(spacePage.getPageNo());
        resultPage.setTotalCount(spacePage.getTotalCount());
        List<SpaceResult> resultList = new LinkedList<SpaceResult>();
        //转换数据为Result
        for (Space space : spacePage.getData()){
            SpaceResult spaceResult = obtainInformation(space);
            resultList.add(spaceResult);
        }
        resultPage.setData(resultList);
        return resultPage;
    }

    //将space转换为spaceResult 并获取子空间信息和模板信息
    private SpaceResult obtainInformation(Space space){
        if (null == space){
            logger.error("待转换的数据为空！");
            return null;
        }
        SpaceResult spaceResult = BeanConverter.convertEntity2DTO(space,SpaceResult.class);
        //添加子空间信息
        List<Long> sonSpaceList = findSonSpace(spaceResult.getId());
        spaceResult.setSonSpaceList(sonSpaceList);
        //添加模板信息
        List<TemplateParam> templateList = cardTemplateBiz.getTemplateParamBySpaceId(space.getId());
        spaceResult.setCardTemplateList(templateList);
        return spaceResult;
    }

    @Override
    public String findSpaceName(Long spaceId) {
        if (null == spaceId){
            logger.error("参数异常");
            return null;
        }
        Space space = spaceQueryService.findOne(spaceId);
        if (null == space){
            logger.error("无此ID:{}对应的数据",spaceId);
            return null;
        }
        return space.getSpaceName();
    }
}
