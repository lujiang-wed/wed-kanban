package com.wednesday.kanban.web.Index.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UeditorController {

    @RequestMapping(value = "/ueditor.htm",method = RequestMethod.GET)
    public String index(){
        return "ueditor";
    }

    @RequestMapping(value = "/ueditor.htm",method = RequestMethod.POST)
    @ResponseBody
    public String todo(HttpServletRequest request, HttpServletResponse response,Model model){
        Map map = request.getParameterMap();
        String data = request.getParameter("data");
        System.out.println("I am in");
        return "It is my answer";
    }

}
