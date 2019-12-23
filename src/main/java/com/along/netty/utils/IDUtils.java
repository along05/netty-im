package com.along.netty.utils;

import java.util.UUID;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public class IDUtils {

    /**
     * 获取id
     */
    public static String getUserId(){
        return UUID.randomUUID().toString().substring(0 , 4);
    }

}
