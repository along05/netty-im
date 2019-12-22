package com.along.netty.im.client.handler;

import com.along.netty.im.protocol.response.MessageRespPackage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @author huanglong
 * @date 2019-12-17
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageRespPackage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRespPackage messageResponsePacket) {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + messageResponsePacket
                .getMessage());
    }
}