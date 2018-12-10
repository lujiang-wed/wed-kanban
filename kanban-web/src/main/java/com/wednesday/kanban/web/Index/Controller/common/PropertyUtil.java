package com.wednesday.kanban.web.Index.Controller.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    //property
    private static Properties p;
    //配置文件路径
    private static String configPath = "";
    private static String basePath = File.separator + "excel" + File.separator;
    private PropertyUtil(){}

    private Properties getProperty(){
        if(p == null){
            p = new Properties();
        }
        return p;
    }

    private Properties getProperty(String configPath){
        //如果不是当前路径。则强制修改配置文件路径
        if(!PropertyUtil.configPath.equals(configPath)){
            PropertyUtil.configPath = configPath;
            p = null;
        }

        if(p == null){
            p = getProperty();
            InputStream i = getInstall().getClass().getClassLoader().getResourceAsStream(basePath.concat(configPath));
            try {
                p.load(i);
            } catch (IOException e) {
                System.out.println("配置文件路径不正确" + e.getMessage());
            }
        }
        return p;
    }

    public static PropertyUtil getInstall(){
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder{
        public static PropertyUtil singleton = new PropertyUtil();
    }

    /**
     * 读取键值数据
     * @param key
     * @return
     */
    public String readProperty(String configPath,String key){
        return getProperty(configPath).getProperty(key);
    }

    /**
     * 【编码转换】  读取键值数据
     * @param configPath
     * @param key
     * @return
     */
    public String readPropertyWithByte(String configPath,String key){
        String pro = getProperty(configPath).getProperty(key);
        try {
            return new String(pro.getBytes("ISO-8859-1"), "utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 读取键值数据
     * @param key
     * @return
     */
    public Object readProperty(String configPath,Object key){
        return getProperty(configPath).get(key);
    }
}

