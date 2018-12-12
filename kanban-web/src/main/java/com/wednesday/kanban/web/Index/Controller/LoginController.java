package com.wednesday.kanban.web.Index.Controller;

import com.wednesday.kanban.biz.api.UserAuditBiz;
import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.common.Result;
import com.wednesday.kanban.web.Index.Controller.permission.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import util.MD5Encrypt;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.LOGIN_IDENTITY;
import static com.wednesday.kanban.web.Index.Controller.permission.CookieUtil.parseToken;

@Controller
public class LoginController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserAuditBiz userAuditBiz;

    @RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
    public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
        //获取当前登陆的用户
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY);
        if (cookieToken != null) {
            UserInstance cookieUser = parseToken(cookieToken);
            if (cookieUser != null) {
                UserInstance dbUser = userAuditBiz.findById(cookieUser.getId());
                if (dbUser != null) {
                    if (cookieUser.getUserPwd().equals(dbUser.getUserPwd())) {
                        return "space/empty";
                    }
                }
            }
        }
        return "login/init";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserParam userParam = new UserParam();
        try {
            super.bindRequestParam(request, userParam);
            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
            UserInstance userInstance = userAuditBiz.findByUser(userParam);
            if (userInstance != null) {
                CookieUtil.set(response, LOGIN_IDENTITY, CookieUtil.makeToken(userInstance), false);
                return new ModelAndView("space/empty", "result", "SUCCESS");
            } else {
                return new ModelAndView("login/init", "result", "ERROR");
            }
        } catch (Exception e) {
            logger.info("login exception happened !", e);
        }
        return new ModelAndView("login/init", "result", "ERROR");
    }

    @Deprecated
    @RequestMapping(value = {"login.htm"}, method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(HttpServletRequest request, HttpServletResponse response) {
        UserParam userParam = new UserParam();

        try {
            super.bindRequestParam(request, userParam);
            UserInstance userInstance = userAuditBiz.findByUser(userParam);
            if (userInstance == null) {
                CookieUtil.set(response, LOGIN_IDENTITY, CookieUtil.makeToken(userInstance), false);
                return new Result(true).toString();
            }
        } catch (Exception e) {
            logger.info("login exception happened !", e);
        }
        return new Result(false).toString();
    }

    @RequestMapping(value = {"logout"})
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request,response,LOGIN_IDENTITY);
        return "referer:";//去上次的地址
    }

    @RequestMapping(value = {"register"}, method = RequestMethod.GET)
    public String Register(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "register/init";
    }

    @RequestMapping(value = {"register"}, method = RequestMethod.POST)
    public String doRegister(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserParam userParam = new UserParam();
        try {
            super.bindRequestParam(request, userParam);
            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
            userParam.setUserType(0);
            userParam.setModifier("SYSTEM");
            userParam.setCreator("SYSTEM");
            if (userAuditBiz.addUser(userParam).equals("SUCCESS")) {
                return "login/init";//return "SUCCESS";
            }
        } catch (Exception e) {
            logger.info("login exception happened !", e);
        }
        System.out.println("in");
        return "register/init";
    }

    @RequestMapping(value = {"checkUser"}, method = RequestMethod.POST)
    @ResponseBody
    public String checkUserExist(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserParam userParam = new UserParam();
        try {
            super.bindRequestParam(request, userParam);
            userParam.setUserPwd(MD5Encrypt.getMD5(userParam.getUserPwd()));
            userParam.setUserType(0);
            UserInstance userInstance = userAuditBiz.findByUser(userParam);
            if (userInstance != null) {
                return "EXIST";
            }
        } catch (Exception e) {
            logger.info("login exception happened !", e);
        }
        return "register/init";
    }
}
