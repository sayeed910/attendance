package com.tahsinsayeed.attendance;

import java.util.Hashtable;

/**
 * Created by sayeed on 7/8/17.
 */
public class Config {
    private static Hashtable<String, Object> config = new Hashtable<>();

    static {
        config.put("db_name", "attendance");
        config.put("db_version", 1);
    }

    public static Object get(String key){
        return config.get(key);
    }
}
