package com.wednesday.kanban.web.Index.Controller;

import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    private static DateFormat normalDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static DateFormat otherDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 将Request中的请求参数绑定到对象上
     *
     * @param request
     * @param object
     */
    protected void bindRequestParam(HttpServletRequest request, Object object) {
        ServletRequestDataBinder binder = new ServletRequestDataBinder(object);
        // 将所有传递进来的String进行HTML编码，防止XSS攻击（@lpm：打开这段代码，需要对帮助中心进行修改）
        // binder.registerCustomEditor(String.class, new PropertyEditorSupport()
        // {
        // @Override
        // public void setAsText(String text) {
        // setValue(text == null ? null :
        // StringEscapeUtils.escapeHtml(text.trim()));
        // }
        // @Override
        // public String getAsText() {
        // Object value = getValue();
        // return value != null ? value.toString() : "";
        // }
        // });
        binder.registerCustomEditor(String.class, "offset", new PropertyEditorSupport() {
            public void setAsIntger(String value) {
                try {
                    setValue(Integer.parseInt(value));
                } catch (NumberFormatException ex) {
                    setValue(null);
                }
            }

            public Integer getAsInteger() {
                return (Integer) getValue();
            }
        });

        binder.registerCustomEditor(String.class, "limit", new PropertyEditorSupport() {
            public void setAsIntger(String value) {
                try {
                    setValue(Integer.parseInt(value));
                } catch (NumberFormatException ex) {
                    setValue(null);
                }
            }

            public Integer getAsInteger() {
                return (Integer) getValue();
            }
        });

        // 日期过滤
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            public void setAsText(String value) {
                try {
                    setValue(normalDateFormat.parse(value));
                } catch (ParseException e) {
                    try {
                        setValue(otherDateFormat.parse(value));
                    } catch (ParseException e1) {
                        setValue(null);
                    }
                }
            }

            public String getAsText() {
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) getValue());
            }

        });

        binder.bind(request);
    }
}
