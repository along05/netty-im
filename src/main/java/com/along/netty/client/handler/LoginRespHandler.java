package com.along.netty.client.handler;

import com.along.netty.protocol.response.LoginRespPacket;
import com.along.netty.session.Session;
import com.along.netty.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class LoginRespHandler extends SimpleChannelInboundHandler<LoginRespPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRespPacket loginRespPacket) {
        if (loginRespPacket.isSuccess()) {
            System.out.println("【登陆成功】你的id为：" + loginRespPacket.getUserId());
            SessionUtils.bindLogin(
                    new Session(loginRespPacket.getUserId(), loginRespPacket.getUsername()),
                    ctx.channel());
        } else {
            System.out.println("【登陆失败】错误信息：" + loginRespPacket.getReason());
        }
    }
}
