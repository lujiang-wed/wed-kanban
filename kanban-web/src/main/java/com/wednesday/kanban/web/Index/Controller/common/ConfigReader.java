package com.wednesday.kanban.web.Index.Controller.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ConfigReader {
    private final static String attributeKey = "attribute";
    private final static String headerKey = "header";
    private final static String titleKey = "title";
    private final static String staticHeaderKey = "staticHeader";
    private final static String staticAttributeKey = "staticAttribute";

    private final static String itemSeparator = ",";
    private final static String listSeparator = "#";



    private static ThreadLocal<ConfigReader> threadLocal = new ThreadLocal<ConfigReader>();
    private ConfigReader(){}

    public static ConfigReader getInstall(){
        ConfigReader reader = threadLocal.get();
        if(reader == null){
            reader = new ConfigReader();
            threadLocal.set(reader);
        }
        return reader;
    }

    ////////////////////////////
    ////服务于业务逻辑-start/////
    ////////////////////////////

    public String[] getTitles(String configPath) throws Exception{
        return readProperty(configPath,titleKey,itemSeparator);
    }

    public List<String[]> getAttributes(String configPath) throws Exception{
        return readProperty(configPath,attributeKey);
    }

    public List<String[]> getHeaders(String configPath) throws Exception{
        return readProperty(configPath,headerKey);
    }

    public List<String[]> getStaticHeaders(String configPath) throws Exception{
        return readProperty(configPath,staticHeaderKey);
    }

    public List<String[]> getStaticAttributes(String configPath) throws Exception{
        return readProperty(configPath,staticAttributeKey);
    }

    ////////////////////////////
    ////服务于业务逻辑-end///////
    ////////////////////////////

    /**
     * 按照父单元读取规则读取
     *
     * @param configPath
     * @param key
     * @return
     * @throws Exception
     */
    public List<String[]> readProperty(String configPath,String key) throws Exception{
        List<String[]> list = new ArrayList<String[]>();
        //按照组合方式,切分成只有itemSeparator的项
        String[] items = readProperty(configPath, key,listSeparator);
        if(items == null || items.length < 1){
            return null;
        }
        for(String item : items){
            String[] dim = resolveStr(item, itemSeparator);
            if(dim != null && dim.length > 0){
                list.add(dim);
            }
        }
        if(list == null || items.length < 1){
            return null;
        }
        return list;
    }

    /**
     * 子单元读取
     * @param configPath
     * @param key
     * @param separator
     * @return
     * @throws Exception
     */
    private String[] readProperty(String configPath,String key,String separator) throws Exception{
        String attrStr = PropertyUtil.getInstall().readProperty(configPath,key);
        //编码
        //String attrStr = PropertyUtil.getInstall().readPropertyWithByte(configPath,key);
        return resolveStr(attrStr,separator);
    }

    /**
     * 字串解析
     * @param target
     * @param separator
     * @return
     */
    private String[] resolveStr(String target,String separator){
        if(target == null || "".equals(target)){
            return null;
        }

        String[] attributes = target.split(separator);
        List<String> list = Arrays.asList(attributes);

        if(list != null && !list.isEmpty()){
            Iterator<String> iter = list.iterator();

            while(iter.hasNext()){
                String str = iter.next();
                if(str == null || "".equals(str) || "".equals(str.trim()) ){
                    iter.remove();
                }
            }
            if(list.isEmpty()){
                return null;
            }else{
                return (String[]) list.toArray();
            }
        }
        return null;
    }
}

