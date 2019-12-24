package com.along.netty.server.Handler;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.LoginReqPacket;
import com.along.netty.protocol.response.LoginRespPacket;
import com.along.netty.session.Session;
import com.along.netty.utils.IDUtils;
import com.along.netty.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class LoginReqHandler extends SimpleChannelInboundHandler<LoginReqPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginReqPacket loginReqPacket) throws Exception {
        LoginRespPacket loginRespPacket = new LoginRespPacket();
        loginRespPacket.setSuccess(vailLogin(loginReqPacket));
        loginRespPacket.setUsername(loginReqPacket.getUserName());
        loginRespPacket.setUserId(IDUtils.getUserId());
        loginRespPacket.setReason("登陆成功");

        System.out.println("【用户登陆】用户名 - 用户id：" + loginRespPacket.getUsername() + "-" + loginRespPacket.getUserId());
        SessionUtils.bindLogin(
                new Session(loginRespPacket.getUserId(), loginRespPacket.getUsername()),
                ctx.channel()
        );
        ctx.channel().writeAndFlush(loginRespPacket);
    }

    private boolean vailLogin(LoginReqPacket loginReqPacket) {
        return true;
    }
}
