package com.along.netty.client.handler;

import com.along.netty.protocol.response.LogoutResponsePacket;
import com.along.netty.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class LogoutRespHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) throws Exception {
        if (logoutResponsePacket.isSuccess()) {
            System.out.println("退出登陆成功");
            SessionUtils.unBindSession(ctx.channel());
        }
    }
}
