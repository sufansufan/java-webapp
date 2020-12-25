package com.ics.utils;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description：Map工具类
 * @name：im
 * @author：yuanky
 * @date：2018/1/20 17:08
 */
public class MapUtils {

    public static final String SPLIC_STR = "@";

    /**
     * @Description: 根据需要合并的的属性合并为用@拼接的key值，value为key对应的map对象
     * list为每一个属性组成的listMap
     * keys为需要合并的属性
     * @author: yuanky
     * @date: 2018/1/20 17:12
     * LinkedHashMap
     */
    public static Map<String, Map<String, Object>> CombineMapByAttr(List<Map<String, Object>> list, String valKey, String... keys) {
        String key;
        Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
        for (Map<String, Object> map : list) {
            key = null;
            for (String s : keys) {
                key += map.get(s) + "@";
            }
            if (key == null) {
                continue;
            } else {
                key = key.substring(0, key.length() - 1);
            }
            if (!maps.containsKey(key)) {
                maps.put(key, new HashMap<String, Object>());
            }
            maps.get(key).put(key, map.get(valKey));
        }
        return maps;
    }


    /*******************************listToMapByKey 将list转为map ********************************************/


    /**
     * @Description: 将list转为map，key为T中字段属性作为map的key值 value为T对象
     * list 需要转化的结果集
     * clazz
     * splicStr 当存在多个属性时，指定的拼接符
     * keyProp 需要拼接的属性名称
     * @author: yuanky
     * @date: 2019/6/1 15:10
     */
    public static <T> Map<String, T> listToMapByKey(List<T> list, Class<? extends T> clazz, String splicStr, String... keyProp) {
        if (list == null || list.size() == 0) {
            return new HashMap<>();
        }
        Map<String, T> maps = new HashMap<>();
        for (T t : list) {
            String key = getKey(clazz, splicStr, t, keyProp);
            maps.put(key, t);
        }
        return maps;
    }

    /**
     * @Description: 默认拼接符为@
     * @author: yuanky
     * @date: 2019/6/1 15:22
     */
    public static <T> Map<String, T> listToMapByKey(List<T> list, Class<? extends T> clazz, String... keyProp) {
        return listToMapByKey(list, clazz, SPLIC_STR, keyProp);
    }

    /**
     * @Description: 当只有一个属性作为keyProp时候
     * @author: yuanky
     * @date: 2019/6/1 15:30
     */
    public static <T> Map<String, T> listToMapByKey(List<T> list, Class<? extends T> clazz, String key) {
        return listToMapByKey(list, clazz,SPLIC_STR, new String[]{key});
    }

    /**
     * @Description: 根据参数获取key
     * @author: yuanky
     * @date: 2019/7/12 9:57
     */
    private static <T> String getKey(Class<? extends T> clazz, String splicStr, T t,  String[] keyProp) {
        String key = new String();
        for (int i = 0, len = keyProp.length; i < len; i++) {
            String prop = keyProp[i];
            String propValue = null;
            //如果是map类型的 否则用反射的方式获取key值
            if(clazz.equals(Map.class)){
                Map mapObj = (Map) t;
                propValue = (String) mapObj.get(prop);
            }else{
                try{
                    PropertyDescriptor pd = new PropertyDescriptor(prop, clazz);
                    Method rM = pd.getReadMethod();//获得读方法
                    Object v = rM.invoke(t);
                    //如果是日期类型的 需要获取注解上的格式并解析为字符串
                    if(v instanceof Date){
                        JsonFormat jsonFormat = rM.getAnnotation(JsonFormat.class);
                        String formatStr = jsonFormat.pattern();
                        SimpleDateFormat df = new SimpleDateFormat(formatStr);
                        propValue = df.format(rM.invoke(t));
                    }else{
                        propValue = (String) rM.invoke(t);
                    }
                }catch (Exception e){
                    propValue = "";
                    e.printStackTrace();
                }
            }
            key = i == 0 ? key + propValue : key + splicStr + propValue;
        }
        return key;
    }

    /*******************************listToMapByKey 将list转为map list ********************************************/

    public static <T> Map<String, List<T>> listToMapListByKey(List<T> list, Class<? extends T> clazz, String splicStr, String... keyProp) {
        if (list == null || list.size() == 0) {
            return new HashMap<>();
        }
        Map<String, List<T>> maps = new HashMap<>();
        for (T t : list) {
            String key = getKey(clazz, splicStr, t, keyProp);
            if(maps.containsKey(key)){
                maps.get(key).add(t);
            }else{
                List<T> groupList = new ArrayList<>();
                groupList.add(t);
                maps.put(key, groupList);
            }

        }
        return maps;
    }



    /**
     * @Description: 默认拼接符为@
     * @author: yuanky
     * @date: 2019/7/12 9:54
     */
    public static <T> Map<String, List<T>> listToMapListByKey(List<T> list, Class<? extends T> clazz, String... keyProp) {
        return listToMapListByKey(list, clazz, SPLIC_STR, keyProp);
    }

    /**
     * @Description: 当只有一个属性作为keyProp时候
     * @author: yuanky
     * @date: 2019/7/12 9:54
     */
    public static <T> Map<String, List<T>> listToMapListByKey(List<T> list, Class<? extends T> clazz, String key) {
        return listToMapListByKey(list, clazz,SPLIC_STR, new String[]{key});
    }

}
