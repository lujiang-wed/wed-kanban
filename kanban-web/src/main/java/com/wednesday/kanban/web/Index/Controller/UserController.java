/**
 * Copyright (c) 2014, wylipengming@jd.com|shouli1990@gmail.com. All rights reserved.
 *
 */
package com.wednesday.kanban.web.Index.Controller;

import com.wednesday.kanban.biz.api.UserAuditBiz;
import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.domain.Perm;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.common.Result;
import com.wednesday.kanban.web.Index.Controller.permission.Permission;
import com.wednesday.kanban.web.Index.Controller.permission.session.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import util.MD5Encrypt;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserAuditBiz userAuditBiz;

    @Permission(value = Perm.ALL)
    @RequestMapping(value = "/listAuth",method = RequestMethod.GET)
    public String listAuth(HttpServletRequest request,HttpServletResponse response,Model model) {
        UserInstance user = SessionUtils.getUser(request);
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("auths",user.getPerms());
        return "auth/authList";
    }

    @Permission(value = Perm.LIST_USER)
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(HttpServletRequest request,HttpServletResponse response,Model model) {
        UserParam param = new UserParam();
        super.bindRequestParam(request,param);

        Page<UserInstance> page = userAuditBiz.findByPage(param);

        model.addAttribute("pageNo",page.getPageNo());
        model.addAttribute("pageSize",page.getPageSize());
        model.addAttribute("datas",page.getData());

        return "auth/userList";
    }

    @Permission(value = Perm.ADD_USER)
    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String create(HttpServletRequest request,HttpServletResponse response) {

        return "auth/adduser";
    }

    @Permission(value = Perm.ADD_USER)
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public String doCreate(HttpServletRequest request,HttpServletResponse response) {

        return "auth/adduser";
    }

    @Permission(value = Perm.MODIFY_USER)
    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(HttpServletRequest request,HttpServletResponse response) {

        return "auth/modifyUser";
    }

    @Permission(value = Perm.MODIFY_USER)
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request,HttpServletResponse response, Model model,
                           @RequestParam(value = "oldPwd" ,required = true) String oldPwd,
                           @RequestParam(value = "newPwd",required = true) String newPwd) {
        UserInstance userInstance = SessionUtils.getUser(request);
        UserParam param = new UserParam();
        param.setUserPwd(MD5Encrypt.getMD5(newPwd));
        param.setOldPwd(MD5Encrypt.getMD5(oldPwd));
        param.setId(userInstance.getId());

        model.addAttribute("result", userAuditBiz.updatePwd(param));

        return "auth/modifyUser";
    }

    @Permission(value = Perm.DEL_USER)
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    public String del(HttpServletRequest request,HttpServletResponse response,
                      @RequestParam(value = "userId",required = true)Long userId) {
        if(!userAuditBiz.delUser(userId).equals("SUCCESS")){
            return new Result(false).toString();
        }
        return new Result(true).toString();
    }
}
