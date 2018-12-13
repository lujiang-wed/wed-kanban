package com.wednesday.kanban.web.Index.Controller;

import com.alibaba.fastjson.JSON;
import com.wednesday.kanban.biz.api.AuthAuditBiz;
import com.wednesday.kanban.biz.api.AuthorityContentBiz;
import com.wednesday.kanban.biz.api.CardAuditBiz;
import com.wednesday.kanban.biz.api.SpaceAuditBiz;
import com.wednesday.kanban.biz.cookies.CookieUtil;
import com.wednesday.kanban.common.SpaceTreeNode;
import com.wednesday.kanban.common.code.AuthEnum;
import com.wednesday.kanban.common.param.SpaceQueryParam;
import com.wednesday.kanban.common.result.SpaceResult;
import com.wednesday.kanban.domain.Auth;
import com.wednesday.kanban.domain.AuthorityContent;
import com.wednesday.kanban.domain.Space;
import com.wednesday.kanban.domain.UserInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.LOGIN_IDENTITY;
import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.getValue;
import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.parseToken;

@Controller
@RequestMapping("space")
public class SpaceController {
    private final Logger logger = LoggerFactory.getLogger(SpaceController.class);

    @Resource
    SpaceAuditBiz spaceAuditBiz;

    @Resource
    AuthAuditBiz authAuditBiz;
    @Resource
    private AuthorityContentBiz authorityContentBiz;

    @Resource
    CardAuditBiz cardAuditBiz;

    private static final String SPRINT_COOKIE_NAME = "Wed_KanBan_SPRINT_NAME_";

    /**
     * 初始化进入space页面时，加载space/main.vm
      * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value={""})
    public String init(HttpServletRequest request, HttpServletResponse response,Model model) {

        return "space/empty";
    }

    /**
     * 加载空间详情
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value={"showSpace.htm"})
    public String showSpace(HttpServletRequest request, HttpServletResponse response,Model model) {
        //加载空间详情
        Long sid = Long.parseLong(request.getParameter("spaceId"));
        SpaceResult space = spaceAuditBiz.findOne(sid);

        model.addAttribute("launchId",request.getParameter("launchId"));
        model.addAttribute("spaceId",space.getId());
        model.addAttribute("spaceName",space.getSpaceName());
        model.addAttribute("spacePin",space.getSpacePin());
        model.addAttribute("spaceDesc",space.getSpaceDesc());
        Long parentSpace = space.getParentSpace();
        if(parentSpace.intValue() == -1){
        model.addAttribute("parentSpace",-1);
        model.addAttribute("parentSpacePin","");
        }else{
        model.addAttribute("parentSpace",parentSpace);
        model.addAttribute("parentSpacePin",spaceAuditBiz.findOne(parentSpace).getSpacePin());
        }

        //加载权限详情
        List<Auth> auths = authAuditBiz.findBySid(sid);
        for(Auth auth : auths){
            if(auth.getPrivilege().equals("r")){
                auth.setPrivilege("只读");
            }else if(auth.getPrivilege().equals("m")){
                auth.setPrivilege("读写");
            }else if(auth.getPrivilege().equals("s")){
                auth.setPrivilege("空间管理");
            }
        }
        model.addAttribute("templateList",space.getCardTemplateList());

        model.addAttribute("auths",auths);
        return "space/main";
    }
    /**
     * 进入创建空间时，加载space/add.vm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value={"add"})
    public String add(HttpServletRequest request, HttpServletResponse response,Model model) {

        return "space/add";
    }

    @RequestMapping(value={"addAuth"})
    @ResponseBody
    public String addAuth(HttpServletRequest request, HttpServletResponse response,Model model) {
        Auth auth = new Auth();
        try{
            Long spaceId = Long.parseLong(request.getParameter("spaceId"));
            if(spaceId == null){
                return "spaceId不能为空";
            }
            SpaceResult spaceResult = spaceAuditBiz.findOne(spaceId);
            if(spaceResult == null){
                return "spaceId不存在";
            }
            auth.setSid(spaceId);
        }catch(Exception e){
            e.printStackTrace();
            return "spaceId错误";
        }
        if(StringUtils.isEmpty(request.getParameter("user"))){
            return "user不能为空";
        }
        auth.setUser(request.getParameter("user"));

        String privilege = request.getParameter("privilege");
        if((StringUtils.isEmpty(privilege))){
            return "添加的权限不存在";
        }
        switch (privilege) {
            case "r":
                auth.setPrivilege("r");
                break;
            case "m":
                auth.setPrivilege("m");
                break;
            case "s":
                auth.setPrivilege("s");
                break;
            default:
                return "添加的权限不存在";
        }
        try{
            if(authAuditBiz.addAuth(auth) != 0){
                return "success";
            }
            return "添加用户失败";
        }catch (Exception e){
            e.printStackTrace();
            return "添加用户失败";
        }
    }

    @RequestMapping(value={"modifyAuth"})
    @ResponseBody
    public String modifyAuth(HttpServletRequest request, HttpServletResponse response,Model model) {
        Auth auth = new Auth();
        try{
            Long spaceId = Long.parseLong(request.getParameter("spaceId"));
            auth.setSid(spaceId);
        }catch(Exception e){
            e.printStackTrace();
            return "spaceId错误";
        }
        if(StringUtils.isEmpty(request.getParameter("user"))){
            return "user不能为空";
        }
        auth.setUser(request.getParameter("user"));

        String privilege = request.getParameter("privilege");
        if((StringUtils.isEmpty(privilege))){
            return "修改的权限不存在";
        }
        switch (privilege) {
            case "r":
                auth.setPrivilege("r");
                break;
            case "m":
                auth.setPrivilege("m");
                break;
            case "s":
                auth.setPrivilege("s");
                break;
            default:
                return "待修改权限不存在";
        }
        try{
            if(authAuditBiz.modifyAuth(auth) != 0){
                return "success";
            }
            return "修改用户权限失败";
        }catch (Exception e){
            logger.error("修改用户权限失败");
            return "修改用户权限失败";
        }
    }

    @RequestMapping(value={"delAuth"})
    @ResponseBody
    public String delAuth(HttpServletRequest request, HttpServletResponse response,Model model) {
        Auth auth = new Auth();
        try{
            Long spaceId = Long.parseLong(request.getParameter("spaceId"));
            auth.setSid(spaceId);
        }catch(Exception e){
            logger.error("spaceId错误");
            return "spaceId错误";
        }
        if(StringUtils.isEmpty(request.getParameter("user"))){
            return "user不能为空";
        }
        auth.setUser(request.getParameter("user"));

        try{
            if(authAuditBiz.delAuth(auth) != 0){
                return "success";
            }
            return "删除用户失败";
        }catch (Exception e){
            e.printStackTrace();
            return "删除用户失败";
        }
    }
    /**
     * 创建空间
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "addNewSpace",method = RequestMethod.GET)
    @ResponseBody
    public String addNewSpace(HttpServletRequest request, HttpServletResponse response,Model model){
        Space space = new Space();

        space.setSpaceName(request.getParameter("spaceName"));
        space.setSpaceDesc(request.getParameter("spaceDesc"));
        space.setSpacePin(request.getParameter("spacePin"));
        String parentSpacePin = request.getParameter("parentSpacePin");
        String user = parseToken(getValue(request,LOGIN_IDENTITY)).getUserName();

        SpaceQueryParam space4query = new SpaceQueryParam();
        space4query.setSpacePin(request.getParameter("spacePin"));
        List<SpaceResult> responseData = spaceAuditBiz.findByPage(space4query).getData();
        if(responseData.size()!=0){
            return "Space Pin is already exist";
        }
        if(StringUtils.isEmpty(parentSpacePin)){
            space.setParentSpace(-1L);//无父节点的空间，父空间id统一为-1
        }else{

            space4query.setSpacePin(parentSpacePin);
             responseData = spaceAuditBiz.findByPage(space4query).getData();
            if(responseData.size()==0){
                return "No Such Parent Space";
            }else{
            Long parentSpaceId = spaceAuditBiz.findByPage(space4query).getData().get(0).getId();
            space.setParentSpace(parentSpaceId);
            }
        }

        return spaceAuditBiz.addSpace(space, user);
    }

    /**
     * 修改空间
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "alterSpace",method = RequestMethod.POST)
    @ResponseBody
    public String alterSpace(HttpServletRequest request, HttpServletResponse response,Model model){
        Space space = new Space();
        Long spaceId = Long.parseLong(request.getParameter("spaceId"));
        space.setId(spaceId);
        space.setSpaceName(request.getParameter("spaceName"));
        space.setSpaceDesc(request.getParameter("spaceDesc"));
        String spacePin = request.getParameter("spacePin");
        space.setSpacePin(spacePin);
        String parentSpacePin = request.getParameter("parentSpacePin");
        if(spacePin.equals(parentSpacePin)){
            return "SpacePin cannot equal with parent spacePin";
        }

        //拿到原spacePin,       可以改为前端传，来提高性能
        String beforeModifySpacePin = spaceAuditBiz.findOne(spaceId).getSpacePin();

        SpaceQueryParam space4query = new SpaceQueryParam();
        space4query.setSpacePin(request.getParameter("spacePin"));
        List<SpaceResult> responseData = spaceAuditBiz.findByPage(space4query).getData();
        //如果spacePin被修改了，且想要使用的新SpacePin已存在，直接返回"Space Pin is already exist"
        if(!spacePin.equals(beforeModifySpacePin) && responseData.size()!=0 ){
            return "SpacePin is already exist";
        }

        if(StringUtils.isEmpty(parentSpacePin)){
            space.setParentSpace(-1L);//无父节点的空间，父空间id统一为-1
        }else{

            space4query.setSpacePin(parentSpacePin);
            responseData = spaceAuditBiz.findByPage(space4query).getData();
            if(responseData.size()==0){
                return "No Such Parent Space";
            }else{
                Long parentSpaceId = spaceAuditBiz.findByPage(space4query).getData().get(0).getId();
                space.setParentSpace(parentSpaceId);
            }
        }

        return spaceAuditBiz.modifySpace(space);
    }

    @RequestMapping(value = "getTree.htm",method = {RequestMethod.POST})
    @ResponseBody
    public String createTreeInfo(HttpServletRequest request, HttpServletResponse response){

        UserInstance user = parseToken(getValue(request,LOGIN_IDENTITY));

        //获取用户JSON权限
        String authContent = authorityContentBiz.findContentByUser(user.getUserName());
        if (null != authContent && !"".equals(authContent)){
            return authContent;
        }
        List<Auth> auths = authAuditBiz.findByUser(user.getUserName());
        Map<Long,AuthEnum> authMap = new HashMap<Long, AuthEnum>();

        for (Auth auth : auths){
            authMap.put(auth.getSid(),toEnum(auth.getPrivilege()));
        }
        SpaceTreeNode spaceTreeNode = obtainAuthTree(authMap);
        //SpaceTreeNode spaceTreeNode = initSpaceTree(-1L,AuthEnum.SPACE_READ_CARD_READ);

        if (null == spaceTreeNode){
            return "no such spaceId";
        }
        try {
            String content = JSON.toJSONString(spaceTreeNode);
            //存储用户JSON串权限
            AuthorityContent authorityContent = new AuthorityContent(user.getUserName(),content);
            authorityContentBiz.addContent(authorityContent);
            return content;
        } catch (Exception e) {

        }
        return "create tree fail";
    }

    @RequestMapping(value = "cardList.htm", method = RequestMethod.GET)
    private String cardList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String spaceId = request.getParameter("spaceId");
        String cardType = request.getParameter("cardType");
        String templateId = request.getParameter("templateId");
        String sprint = request.getParameter("sprint");
        //String maxSprint = request.getParameter("maxSprintId");

        if (StringUtils.isBlank(sprint) || "-1".equals(sprint)){
            sprint = CookieUtil.getCookie(request, SPRINT_COOKIE_NAME + spaceId);
        }else {
            CookieUtil.createCookie(response,SPRINT_COOKIE_NAME+spaceId,sprint,Integer.MAX_VALUE+"");
        }

        if (cardType != null)
        {
            model.addAttribute("cardTypeShow", cardType);
        }
        if(templateId != null)
        {
            model.addAttribute("templateIdShow", templateId);
        }
        if(sprint != null)
        {
            model.addAttribute("sprintShow", sprint);
        }

        model.addAttribute("spaceId", spaceId);

        Long sid = Long.parseLong(spaceId);
        SpaceResult space = spaceAuditBiz.findOne(sid);
        model.addAttribute("spaceId", space.getId());
        model.addAttribute("spaceName", space.getSpaceName());
        model.addAttribute("maxSprintShow", cardAuditBiz.findSpaceMaxSprint(sid));
        model.addAttribute("templateList", space.getCardTemplateList());

        return "card/index1";
    }


    private AuthEnum toEnum(String privilege){
        if ("s".equals(privilege)){
            return AuthEnum.SPACE_WRITE_CARD_WRITE;
        }else if ("m".equals(privilege)){
            return AuthEnum.SPACE_READ_CARD_WRITE;
        }else {
            return AuthEnum.SPACE_READ_CARD_READ;
        }
    }


    class AuthNodeCount{
        private int count;
        private AuthEnum authEnum;

        AuthNodeCount(AuthEnum authEnum) {
            this.count = 1;
            this.authEnum = authEnum;
        }

        public void add(){
            this.count = this.count + 1;
        }

    }


    public SpaceTreeNode obtainAuthTree(Map<Long,AuthEnum> authMap){

        List<SpaceTreeNode> treeList = new LinkedList<SpaceTreeNode>();
        Map<Long,AuthNodeCount> authCountMap = new HashMap<Long, AuthNodeCount>();

        for (Long spaceId : authMap.keySet()){
            SpaceTreeNode spaceTreeNode = initSpaceTree(spaceId,authMap.get(spaceId));
            countNode(spaceTreeNode,authCountMap);
            treeList.add(spaceTreeNode);
        }

        SpaceTreeNode myRootNode = null;

        List<SpaceTreeNode> result = new LinkedList<SpaceTreeNode>();
        for (SpaceTreeNode spaceTreeNode : treeList){
            if (spaceTreeNode.getSpaceId() == -1){
                myRootNode = spaceTreeNode;
            }

            if (authCountMap.get(spaceTreeNode.getSpaceId()).count == 1){
                result.add(spaceTreeNode);
            }
        }



        if (null == myRootNode){
            myRootNode = new SpaceTreeNode(-1L,"",null);
            myRootNode.setChildList(result);

        }

        adjustAuth(myRootNode,authCountMap);
        return myRootNode;
    }

    private SpaceTreeNode initSpaceTree(Long sid , AuthEnum privilege) {
        //获取空间名
        String spaceName = spaceAuditBiz.findSpaceName(sid);

        if (null == spaceName){
            return null;
        }

        //通过当前空间，创建空间树的当前节点，包含权限
        SpaceTreeNode stn = new SpaceTreeNode(sid,spaceName,privilege);

        //如果有子节点，则循环所有子节点，递归初始化
        List<Long> childSpaceList = spaceAuditBiz.findSonSpace(sid);
        if(null != childSpaceList && childSpaceList.size()!=0){
            List<SpaceTreeNode> childList = new ArrayList<SpaceTreeNode>();
            for(Long childId : childSpaceList){
                childList.add(initSpaceTree(childId,privilege));
            }
            stn.setChildList(childList);
        }
        return stn;
    }

    /**
     * 调整树的读写权限
     * @param spaceTreeNode
     * @param authCountMap
     */
    public void adjustAuth(SpaceTreeNode spaceTreeNode , Map<Long,AuthNodeCount> authCountMap){

        if (null != spaceTreeNode && null != spaceTreeNode.getChildList() && !spaceTreeNode.getChildList().isEmpty()){

            for (SpaceTreeNode childNode : spaceTreeNode.getChildList()){
                if (authCountMap.get(childNode.getSpaceId()).authEnum.getCode() > childNode.getPrivilege().getCode()){
                    childNode.setPrivilege(authCountMap.get(childNode.getSpaceId()).authEnum);
                }

                adjustAuth(childNode , authCountMap);
            }

        }

    }



    /**
     * 遍历多叉树,当前节点id
     *
     * @param spaceTreeNode 多叉树节点
     * @return
     */
    public void countNode(SpaceTreeNode spaceTreeNode , Map<Long,AuthNodeCount> authCountMap)
    {
        if(spaceTreeNode != null && authCountMap != null)
        {
            Long spaceId = spaceTreeNode.getSpaceId();
            AuthNodeCount authNodeCount = authCountMap.get(spaceId);
            if(authNodeCount != null){
                authNodeCount.add();
                if (spaceTreeNode.getPrivilege().getCode() > authNodeCount.authEnum.getCode()){
                    authNodeCount.authEnum = spaceTreeNode.getPrivilege();
                }
            }else{
                AuthNodeCount temp = new AuthNodeCount(spaceTreeNode.getPrivilege());
                authCountMap.put(spaceId, temp);
            }

            if (spaceTreeNode.getChildList() != null && spaceTreeNode.getChildList().size() > 0 )
            {
                for(SpaceTreeNode childTreeNode: spaceTreeNode.getChildList()){
                    countNode(childTreeNode, authCountMap);
                }
            }
        }
    }
}
