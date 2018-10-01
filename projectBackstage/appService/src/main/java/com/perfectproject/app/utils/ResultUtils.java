package com.perfectproject.app.utils;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {
    public static Map<String,Object> success(Object object){
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("data",object);
        return map;
    }
    public static Map<String,Object> error(int errorCode){
        Map<String,Object> map=new HashMap<>();
        map.put("code",errorCode);
        map.put("message","");
        return map;
    }


}
