package com.along.netty.im.server.handler;

import com.along.netty.im.protocol.request.LogoutRequestPacket;
import com.along.netty.im.protocol.response.LogoutRespPacket;
import com.along.netty.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-21
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) {
        SessionUtil.unBindSession(ctx.channel());
        LogoutRespPacket logoutRespPacket = new LogoutRespPacket() ;
        logoutRespPacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutRespPacket) ;
    }
}
