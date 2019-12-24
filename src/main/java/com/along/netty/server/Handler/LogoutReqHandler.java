package com.along.netty.server.Handler;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.LogoutReqPacket;
import com.along.netty.protocol.response.LogoutResponsePacket;
import com.along.netty.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class LogoutReqHandler extends SimpleChannelInboundHandler<LogoutReqPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutReqPacket logoutReqPacket) {
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(bindLogin(logoutReqPacket));
        logoutResponsePacket.setReason("成功");

        SessionUtils.unBindSession(ctx.channel());
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }


    private boolean bindLogin(LogoutReqPacket logoutReqPacket) {
        return true;
    }
}
