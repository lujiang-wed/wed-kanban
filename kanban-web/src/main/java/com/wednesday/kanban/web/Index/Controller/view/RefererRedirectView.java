package com.wednesday.kanban.web.Index.Controller.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class RefererRedirectView implements View {

    protected static final Logger logger = LoggerFactory.getLogger(RefererRedirectView.class);

    public String getContentType() {
        return null;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String returnUrl = request.getHeader("referer");
        if(returnUrl==null || returnUrl.trim().equals("")){
            returnUrl = "/";
        }
        logger.debug("referer url["+returnUrl+"]");
        response.sendRedirect(returnUrl);
    }
}