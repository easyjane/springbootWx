package com.test.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;

import java.util.Map;

public class JsonUtils {

    public static String obj2String(Object obj) {
        return JSONObject.fromObject(obj).toString();
    }

    public static Object str2Obj(String json,Class clazz) {
        JSONObject jo = JSONObject.fromObject(json);
        return JSONObject.toBean(jo,clazz);
    }

    public static Object str2Obj(String json,Class clazz,Map map) {
        JSONObject jo = JSONObject.fromObject(json);
        return JSONObject.toBean(jo,clazz,map);
    }
}
