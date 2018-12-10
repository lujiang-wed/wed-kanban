package com.wednesday.kanban.web.Index.Controller.common;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wylipengming
 * Date: 14-3-11
 * Time: 下午3:25
 *
 * 【web场景】excel导出
 *
 * 配置文件说明：其中：title，header,attribute为必填项
 */
public abstract class ExcelHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

    /**
     * list 数据模型excel导出
     * @param objsMap
     * @param configPath
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    public boolean exportExcel(LinkedHashMap<String,Collection> objsMap, String configPath,String fileName, HttpServletRequest request, HttpServletResponse response) {
        return exportExcel(objsMap,null,configPath, fileName, request, response);
    }

    /**
     * 带有统计信息的excel导出模型
     *
     * @param objsMap
     * @param objects
     * @param configPath
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    public boolean exportExcel(LinkedHashMap<String,Collection> objsMap,Object[] objects, String configPath,String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type","application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            fileName = encodeFileName(request, fileName+".xls");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            OutputStream out = response.getOutputStream();

            try{
                export(objsMap,objects,configPath,out);
            }catch (Exception e){
                logger.error("excel 导出异常：{}",e.getMessage());
                return false;
            }finally {
                out.close();
            }

            return true;

        } catch (Exception e) {
            logger.error("response写出异常：{}", e.getMessage());
        }
        return false;
    }




    /**
     *
     * @param objsMap
     * @param configPath
     * @param outputStream
     * @throws Exception
     */
    private void export(LinkedHashMap<String,Collection> objsMap,Object[] objects, String configPath,OutputStream outputStream) throws Exception {

        ExcelUtils.ExportSetInfo setInfo = new ExcelUtils.ExportSetInfo();

        //装填字段属性
        setInfo.setFieldNames(adapteeAttributes(configPath));

        //装填header栏数据
        setInfo.setHeadNames(adapteeHeaders(configPath));

        //装填标题数据
        setInfo.setTitles(adapteeTitles(configPath));

        //数据装填
        setInfo.setObjsMap(objsMap);

        if(objects != null && objects.length > 0){
            //装填统计信息
            setInfo.setObjs(objects);
            //装填统计属性列表
            setInfo.setStaticFields(adapteeStaticFields(configPath));
            //装填统计属性描述列表
            setInfo.setStaticHeaders(adapteeStaticHeaders(configPath));
        }

        //设置输入流
        setInfo.setOut(outputStream);

        ExcelUtils.export2Excel(setInfo);
    }

    /**
     * head数据装配
     * @param configPath
     * @return
     * @throws Exception
     */
    public abstract List<String[]> adapteeHeaders(String configPath) throws Exception ;

    /**
     * 属性数据装配
     * @param configPath
     * @return
     * @throws Exception
     */
    public abstract List<String[]> adapteeAttributes(String configPath) throws Exception;

    /**
     * 标题数据装配
     * @param configPath
     * @return
     * @throws Exception
     */
    public abstract String[] adapteeTitles(String configPath) throws Exception;

    /**
     * 统计属性装配
     * @param configPath
     * @return
     * @throws Exception
     */
    public abstract List<String[]> adapteeStaticFields(String configPath) throws Exception;

    /**
     * 统计字段描述
     * @param configPath
     * @return
     * @throws Exception
     */
    public abstract List<String[]> adapteeStaticHeaders(String configPath) throws Exception;

    /**
     * 文件名编码
     * @param request
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public  String encodeFileName(HttpServletRequest request,String fileName) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            return "=?UTF-8?B?"+ (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        } else {
            return fileName;
        }
    }

}
