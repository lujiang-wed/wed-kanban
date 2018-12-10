package com.wednesday.kanban.web.Index.Controller.permission.listener;

import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.web.Index.Controller.permission.session.SessionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalInterceptor extends HandlerInterceptorAdapter {
    private final static String userName = "userName";
    private final static String userId = "userId";
    private final static String flag = "onLine";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     //   处理Permission Annotation，实现方法级权限控制,暂时不管
//        if(handler.getClass().isAssignableFrom(HandlerMethod.class)) {
//            HandlerMethod method = (HandlerMethod) handler;
//            Permission permission = method.getMethodAnnotation(Permission.class);
//            //如果为空在表示该方法不需要进行权限验证
//            if (permission != null && !PermissionUtils.hasPower(request, permission.value())) {
//                response.sendRedirect(request.getContextPath() + "/static/nopermissions.html");
//                return false;
//            }
//        }
//        return true;
     //   HttpSession session = request.getSession();
      //  UserInstance userInstance = SessionUtils.getUser(request);

        //------
//        if (SessionUtils.isOnline(request))
//        {
//            UserInstance userInstance = SessionUtils.getUser(request);
//       //     System.out.println("现在登录用户："+userInstance.getUserName());
//           // SessionUtils.login(request,response,userInstance);//如果用户已经登录
//            return true; //去上次的地址
//        }
//        String url = request.getRequestURI();
//        if(url.equals("/login")||url.equals("/register")||url.equals("/checkUser"))
            return true;
//        System.out.println("拦截："+url);
//        response.sendRedirect("/login");
//        return false;
        //-------
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //put userId if Login
        if(SessionUtils.isOnline(request)) {
            setLoginInitMes(request, response);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 设置登录信息
     *
     * @param request
     * @param response
     */
    public static void setLoginInitMes(HttpServletRequest request,HttpServletResponse response){
        if(SessionUtils.isOnline(request)) {
            UserInstance userInstance = SessionUtils.getUser(request);
            if(userInstance != null) {
                request.setAttribute(userName,userInstance.getUserName());
                request.setAttribute(userId,userInstance.getUserId());
                request.setAttribute(flag,"true");
            }
        }
    }
}
