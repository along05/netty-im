package com.along.netty.client.handler;

import com.along.netty.protocol.response.SendGroupMessageRespPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class SendGroupMessageRespHandler extends SimpleChannelInboundHandler<SendGroupMessageRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageRespPacket sendGroupMessageRespPacket) {
        String groupId = sendGroupMessageRespPacket.getGroupId() ;
        String message = sendGroupMessageRespPacket.getMessage() ;
        System.out.println("来自群【"+groupId+"】消息："+message);
    }
}
