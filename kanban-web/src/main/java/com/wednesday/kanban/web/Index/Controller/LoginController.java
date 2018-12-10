package com.wednesday.kanban.web.Index.Controller;

import com.wednesday.kanban.biz.api.UserAuditBiz;
import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.common.Result;
import com.wednesday.kanban.web.Index.Controller.permission.session.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.MD5Encrypt;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wyyangyang1 on 2014/12/24.
 */
@Controller
public class LoginController extends BaseController {

    private static Logger logger= LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserAuditBiz userAuditBiz;

    @RequestMapping(value={"/","login"},method= RequestMethod.GET)
    public String init(HttpServletRequest request, HttpServletResponse response,Model model) {
        //获取当前登陆的用户
        if(SessionUtils.isOnline(request)){//第一次登录为false
            //用户已登陆，直接跳首页
            return "space/empty";
        }
        return "login/init";
    }

    @RequestMapping(value={"/login"},method= RequestMethod.POST)
     public String doLogin(HttpServletRequest request, HttpServletResponse response,Model model) {
        UserParam userParam = new UserParam();
        try{
            super.bindRequestParam(request,userParam);
            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
            UserInstance userInstance = userAuditBiz.findByUser(userParam);
            if (userInstance != null) {
                SessionUtils.login(request,response,userInstance);//设置用户cookie，以及session
                return "space/empty";//去上次的地址
            }
        }catch (Exception e) {
            logger.info("login exception happened !",e);
        }
        return "redirect:/login";
    }

    @Deprecated
    @RequestMapping(value={"login.htm"},method= RequestMethod.POST)
    @ResponseBody
    public String doLogin(HttpServletRequest request, HttpServletResponse response) {
        UserParam userParam = new UserParam();

        try{
            super.bindRequestParam(request,userParam);
            UserInstance userInstance = userAuditBiz.findByUser(userParam);
            if (userInstance == null) {
                SessionUtils.login(request,response,userInstance);//
                return new Result(true).toString();
            }
        }catch (Exception e) {
            logger.info("login exception happened !",e);
        }
        return new Result(false).toString();
    }

    @RequestMapping(value={"logout"})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SessionUtils.logOut(request, response);
        return "referer:";//去上次的地址
    }

    @RequestMapping(value={"register"},method= RequestMethod.GET)
    public String Register(HttpServletRequest request, HttpServletResponse response,Model model) {
//        UserParam userParam = new UserParam();
//        try{
//            super.bindRequestParam(request,userParam);
//            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
//            UserInstance userInstance = userAuditBiz.findByUser(userParam);
//            if (userInstance != null) {
//                SessionUtils.login(request,response,userInstance);//如果用户已经登录
//                return "referer:";//去上次的地址
//            }
//        }catch (Exception e) {
//            logger.info("login exception happened !",e);
//        }
       // return "register/init";
     //   System.out.println("in");
        return "register/init";
    }

    @RequestMapping(value={"register"},method= RequestMethod.POST)
    public String doRegister(HttpServletRequest request, HttpServletResponse response,Model model) {
        UserParam userParam = new UserParam();
        try{
            super.bindRequestParam(request,userParam);
            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
            userParam.setUserType(0);
            userParam.setModifier("SYSTEM");
            userParam.setCreator("SYSTEM");
//            UserInstance userInstance = userAuditBiz.findByUser(userParam);
//            if (userInstance != null) {
//                return "error";
//            }
            if(userAuditBiz.addUser(userParam).equals("SUCCESS"))
            {
                return "login/init";//return "SUCCESS";
            }
//            else
//                return "FAILED";
        }catch (Exception e) {
            logger.info("login exception happened !",e);
        }
        System.out.println("in");
        return "register/init";
    }

    @RequestMapping(value={"checkUser"},method= RequestMethod.POST)
    @ResponseBody
    public String checkUserExist(HttpServletRequest request, HttpServletResponse response,Model model) {
        UserParam userParam = new UserParam();
        try{
            super.bindRequestParam(request,userParam);
            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
            userParam.setUserType(0);
          //  userParam.setModifier("SYSTEM");
           // userParam.setCreator("SYSTEM");
            UserInstance userInstance = userAuditBiz.findByUser(userParam);
            if (userInstance != null) {
                return "EXIST";
            }
        }catch (Exception e) {
            logger.info("login exception happened !",e);
        }
        return "register/init";
    }
}
