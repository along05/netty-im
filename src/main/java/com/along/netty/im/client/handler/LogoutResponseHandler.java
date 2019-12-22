package com.along.netty.im.client.handler;

import com.along.netty.im.protocol.response.LogoutRespPacket;
import com.along.netty.im.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutRespPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRespPacket logoutResponsePacket) {
        SessionUtil.unBindSession(ctx.channel());
        System.out.println("退出登录成功");
    }
}
