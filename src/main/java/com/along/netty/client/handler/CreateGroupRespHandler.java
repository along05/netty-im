package com.along.netty.client.handler;

import com.along.netty.protocol.response.CreateGroupRespPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class CreateGroupRespHandler extends SimpleChannelInboundHandler<CreateGroupRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRespPacket createGroupRespPacket) {
        System.out.println("【群创建】成功，id为【"
                +createGroupRespPacket.getGroupId()+"】，群成员有"
                +createGroupRespPacket.getUserNameList());
    }
}
