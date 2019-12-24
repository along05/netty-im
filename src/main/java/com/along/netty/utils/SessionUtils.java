package com.along.netty.utils;

import com.along.netty.attribute.Attributes;
import com.along.netty.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class SessionUtils {

    //维护群id和channel的关系
    private static final Map<String , ChannelGroup> groupIdChannelMap = new ConcurrentHashMap<>() ;

    //维护用户id和对应channel
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindLogin(Session session , Channel channel){
        userIdChannelMap.put(session.getUserId() , channel) ;
        channel.attr(Attributes.SESSION).set(session) ;
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

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelMap.get(groupId);
    }

}
