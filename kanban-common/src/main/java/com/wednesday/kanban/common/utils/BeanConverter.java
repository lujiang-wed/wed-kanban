package com.wednesday.kanban.common.utils;

import com.wednesday.kanban.common.BaseEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BeanConverter {

    private static Logger logger = LoggerFactory.getLogger(BeanConverter.class);

    private static final int CACHE_METHODLIST_SIZE = 100;
    private static final int CACHE_METHOD_SIZE = 1000;

    private static Map<String, Method> cacheMethod = new LinkedHashMap<String, Method>() {
        protected boolean removeEldestEntry(Map.Entry<String, Method> eldest) {
            return size() > CACHE_METHOD_SIZE;
        }
    };

    private static Map<Class, List<Method>> cacheMethods = new LinkedHashMap<Class, List<Method>>() {
        protected boolean removeEldestEntry(
                Map.Entry<Class, List<Method>> eldest) {
            return size() > CACHE_METHODLIST_SIZE;
        }
    };

    /**
     * 把对象转换为map
     *
     * @param bean
     * @return
     */
    public static Map<String, String> convertBean2Map(Object bean) {
        Class type = bean.getClass();
        Map<String, String> returnMap = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")
                        && !propertyName.equals("page")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        if (result instanceof String) {
                            if (!StringUtils.isEmpty(String.valueOf(result))) {
                                returnMap.put(propertyName,
                                        String.valueOf(result));
                            }
                        } else {
                            returnMap.put(propertyName, String.valueOf(result));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to convert object to map", e);
        }

        return returnMap;
    }

    /**
     * 把map转换为对象
     *
     * @param type
     * @param map
     * @return
     */
    public static Object convertMap2Bean(Class type, Map<String, String> map) {
        Object obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            obj = type.newInstance();
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    Class cla = descriptor.getPropertyType();
                    if (cla == Integer.class) {
                        args[0] = new Integer(value.toString());
                    } else if (cla == Long.class) {
                        args[0] = new Long(value.toString());
                    } else if (cla == BigDecimal.class) {
                        args[0] = new BigDecimal(value.toString());
                    } else if (cla == Date.class) {
                        args[0] = new Date(value.toString());
                    }
                    if (descriptor.getWriteMethod() != null) {
                        try {
                            descriptor.getWriteMethod().invoke(obj, args);
                        } catch (Exception e) {
                            logger.error(
                                    "Failed to get write method, propertyName:{}",
                                    propertyName, e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Failed to convert map to object", e);
        }

        return obj;
    }

    /**
     * 工具类，将实体转换成DTO对象
     *
     * @param entity
     *            待转换实体对象
     * @param classz
     *            目标类
     * @param <T>
     * @return
     */
    public static <T> T convertEntity2DTO(Object entity, Class<T> classz) {
        if (entity == null) {
            return null;
        }
        T result = null;
        try {
            // 新建实例
            result = classz.newInstance();
            // 复制属性
            copyProperties(result, entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 将DTO转换成实体对象
     *
     * @param entity
     *            待转换的DTO对象
     * @param classz
     *            目标类
     * @param <T>
     * @return
     */
    public static <T> T convertDTO2Entity(BaseEntity entity, Class<T> classz) {
        if (entity == null) {
            return null;
        }
        T result = null;
        try {
            // 新建实例
            result = classz.newInstance();
            // 复制属性
            copyProperties(result, entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * 将实体对象列表转换成DTO列表
     *
     * @param objects
     *            待转换实体
     * @param classz
     *            目标类
     * @param <T>
     * @return
     */
    public static <T, E> List<T> convertEntity2DTOList(List<E> objects,
                                                       Class<T> classz) {
        if (objects == null || objects.size() < 1) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (Object obj : objects) {
            list.add(convertEntity2DTO(obj, classz));
        }
        return list;
    }

    /**
     * 将源对象的属性拷贝到目标对象
     *
     * @param target
     *            目标对象
     * @param source
     *            源对象
     */
    public static void copyProperties(Object target, Object source) {
        tryCopyProperties(target, source);
    }

    /**
     * 将对象转为共有某些属性的其他类对象
     *
     * @param obj
     *            待转换的对象
     * @param classz
     *            目标类
     * @param <T>
     * @return
     */
    public static <T> T convertObj2Similar(Object obj, Class<T> classz) {
        if (obj == null) {
            return null;
        }
        T result = null;
        try {
            // 新建实例
            result = classz.newInstance();
            // 复制属性
            tryCopyProperties(result, obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 将目标对象有的属性从源拷贝到目标对象
     *
     * @param target
     *            目标对象
     * @param source
     *            源对象
     */
    private static void tryCopyProperties(Object target, Object source) {
        // 获取对象和父类所有的属性，判断是否有getter，如果有，进行下一步拷贝
        List<Method> methods = traversalSrcClazz(source);
        for (Method m : methods) {
            // 只遍历getter
//			if (!m.getName().startsWith("get")) {
//				continue;
//			}
            traversalDesClazz(m, source, target);
        }

    }

    /**
     * 获取对象的所有属性和父类的属性
     *
     * @param src
     * @return
     */
    private static List<Method> traversalSrcClazz(Object src) {
        Class clazz = src.getClass();
//		if(false){
        if (cacheMethods.containsKey(clazz)) {
//			System.out.println("Use cache");
            return cacheMethods.get(clazz);
        } else {
            List<Method> fieldList = new ArrayList<Method>();
            Method[] methods = clazz.getDeclaredMethods();
            fieldList.addAll(convertArray2List(methods));
            while (!Object.class.equals(clazz)) {
                clazz = clazz.getSuperclass();
                methods = clazz.getDeclaredMethods();
                fieldList.addAll(convertArray2List(methods));
            }
            cacheMethods.put(src.getClass(), fieldList);
            return fieldList;
        }
    }

    /**
     * 查询一个对象和其父类是否包含某个属性
     *
     * @param methodName
     * @param obj
     * @return
     */
    private static Method findMethod(Object obj, String methodName,
                                     Class<?>... params) {
        Class clazz = obj.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, params);
        } catch (NoSuchMethodException e) {
        }
        // 逐个查找父类，看是否有指定方法
        while (!Object.class.equals(clazz)) {
            clazz = clazz.getSuperclass();
            try {
                method = clazz.getDeclaredMethod(methodName, params);
            } catch (NoSuchMethodException e) {
            }
        }
        return method;
    }

    /**
     * 组个调用setter方法，
     *
     * @param srcGetter
     * @param src
     * @param target
     */
    private static void traversalDesClazz(Method srcGetter, Object src,
                                          Object target) {
        try {
            String setterName = "s" + srcGetter.getName().substring(1);
            invokeMethod(setterName, target, srcGetter, src);
        } catch (Exception ex) {
            logger.warn("属性拷贝发生错误， message: {} ", ex.getMessage());
        }
    }

    /**
     * 调用目标对象的setter，设值
     *
     * @param setterName
     * @param target
     * @param getter
     * @param src
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void invokeMethod(String setterName, Object target,
                                     Method getter, Object src) throws InvocationTargetException,
            IllegalAccessException {
        // 获取getter返回值
        Object value = getter.invoke(src);
        // 取出getter的返回类型和setter的参数类型（setter只支持一个参数），
        Class<?> paramType = getter.getReturnType();
        // getter返回值是枚举

        // TODO 不用针对枚举类型进行判断
        String key = makeKey(target, setterName, paramType);
        Method setter = null;
//		if(false){
        if (cacheMethod.containsKey(key)) {
//			System.out.println("Use cache 2");
            setter = cacheMethod.get(key);
            if (setter != null) {
                setter.invoke(target, value);
            }
        } else {
            setter = findMethod(target, setterName, paramType);
            cacheMethod.put(key, setter);
            if (setter != null) {
                setter.invoke(target, value);
            }
        }
    }

    private static String makeKey(Object target, String setterName,
                                  Class paramClz) {
        return target.getClass().getName() + "$" + setterName + "$"
                + paramClz.getName();
    }

    /**
     * 把Array转换成List
     *
     * @param array
     * @return
     */
    private static List<Method> convertArray2List(Method[] array) {
        List<Method> ls = new ArrayList<Method>();
        for (Method method : array) {
            Class[] clazz = method.getParameterTypes();
            if (method.getName().startsWith("get")//将所有前缀为get的方法加入list
                    && (clazz == null || clazz.length == 0)) {
                ls.add(method);
            }
        }
        return ls;
    }
}