package com.along.netty.utils;

import com.along.netty.attribute.Attributes;
import com.along.netty.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class SessionUtils {

    //维护用户id和对应channel
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindLogin(Session session , Channel channel){
        channel.attr(Attributes.SESSION).set(session) ;
        userIdChannelMap.put(session.getUserId() , channel) ;
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION) ;
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }


}
