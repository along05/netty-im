package com.along.netty.client.handler;

import com.along.netty.protocol.response.SendMessageRespPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class SendMessageRespHandler extends SimpleChannelInboundHandler<SendMessageRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessageRespPacket sendMessageRespPacket) {
        System.out.println("【消息】：来自" + sendMessageRespPacket.getFromUsername() + ",内容："
                + sendMessageRespPacket.getMessage());
    }
}
