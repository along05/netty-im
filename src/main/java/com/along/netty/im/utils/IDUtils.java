package com.along.netty.im.utils;

import java.util.UUID;

/**
 * @author huanglong
 * @date 2019-12-21
 */
public class IDUtils {

    public static String randomId(){
        return UUID.randomUUID().toString().split("0")[0] ;
    }

}
