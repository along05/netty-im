package com.along.netty.im.client.handler;

import com.along.netty.im.protocol.response.CreateGroupRespPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,  CreateGroupRespPacket createGroupRespPacket) {
        System.out.print("群创建成功，id 为[" + createGroupRespPacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupRespPacket.getUserNameList());
    }
}
