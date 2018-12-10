package com.wednesday.kanban.biz.cookies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    private final static Logger logger = LoggerFactory.getLogger(CookieUtil.class);
    private static final String COOKIES = "coach_cookie";

    public static Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> cookieMaps = null;
        if ((cookieMaps = (Map<String, String>) request.getAttribute(COOKIES)) != null) {
            return cookieMaps;
        }
        cookieMaps = new HashMap<String, String>();
        String cookies = request.getHeader("Cookie");
        try {
            if (cookies != null) {
                String[] strings = cookies.split("\\;");
                for (String s : strings) {
                    s = s.trim();
                    int i = s.indexOf("=");
                    String k = s.substring(0, i);
                    String v = s.substring(i + 1, s.length());
                    cookieMaps.put(k.trim(), v.trim());
                }
            }
        } catch (Exception e) {
            logger.error("analysis cookie fail，cookie={},msg:{}", cookies, e);
        }
        request.setAttribute(COOKIES, cookieMaps);
        return cookieMaps;
    }

    public static String getCookie(HttpServletRequest request, String key) {
        Map<String, String> cookies = getCookies(request);
        return cookies.get(key);
    }
    public static void createCookie(HttpServletResponse response, String key, String value, String expires) {
        StringBuilder stringBuilder = new StringBuilder(key);
        stringBuilder.append("=").append(value).append(";");
        if (expires != null) {
            stringBuilder.append("Expires=").append(expires).append(";");
        }
        stringBuilder.append("path=/;");
        stringBuilder.append("domain=.")
                .append("cbpmgt.com")
                .append(";HttpOnly ");

        response.addHeader("Set-Cookie", stringBuilder.toString());
    }

}
