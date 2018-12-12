package util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class WebUtil {


    /**
     * 获取传输到页面的Json对象
     *
     * @param codeEnum        功能模块编号
     * @param data      返回Http代码
     * @return              Json对象
     */
    public static String getAjaxResult(AjaxResultCodeEnum codeEnum, Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", codeEnum.getCode());
        map.put("msg", codeEnum.getMessage());
        map.put("data", data);
        String result = "";
        try {
            result = JSON.toJSONString(map);
        } catch (Exception e) {
            result = "{'code':" + codeEnum.getCode() + ",'msg':"+codeEnum.getMessage()+", 'data':{}}";
        }
        return result;
    }


    public static String getAjaxResult(AjaxResultCodeEnum codeEnum) {
        return getAjaxResult(codeEnum,null);
    }
}
