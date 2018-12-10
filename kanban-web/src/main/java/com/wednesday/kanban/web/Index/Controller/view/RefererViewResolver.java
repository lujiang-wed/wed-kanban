package com.wednesday.kanban.web.Index.Controller.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class RefererViewResolver implements ViewResolver,Ordered {//这里的跳转如何处理的
    protected static final Logger logger = LoggerFactory.getLogger(RefererViewResolver.class);
    //以referer:起始的viewName，将被此ViewResolver处理
    public static final String REFERER_PROFIX = "referer:";

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE-1;//这个ViewResolver排在UrlBasedViewResolver前一个的位置
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (!viewName.startsWith(REFERER_PROFIX)) {
            return null;
        }
        logger.debug("this is referer view.");
        return new RefererRedirectView();
    }
}
