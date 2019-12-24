package com.along.netty.server.Handler;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.SendGroupMessageReqPacket;
import com.along.netty.protocol.response.SendGroupMessageRespPacket;
import com.along.netty.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class SendGroupMessageReqHandler extends SimpleChannelInboundHandler<SendGroupMessageReqPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendGroupMessageReqPacket sendGroupMessageReqPacket) {

        SendGroupMessageRespPacket sendGroupMessageRespPacket = new SendGroupMessageRespPacket();
        sendGroupMessageRespPacket.setGroupId(sendGroupMessageReqPacket.getGroupId());
        sendGroupMessageRespPacket.setMessage(sendGroupMessageReqPacket.getMessage());

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(sendGroupMessageReqPacket.getGroupId());

        channelGroup.writeAndFlush(sendGroupMessageRespPacket);
    }
}
