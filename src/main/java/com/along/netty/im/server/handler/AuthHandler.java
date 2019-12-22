package com.along.netty.im.server.handler;

import com.along.netty.im.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author huanglong
 * @date 2019-12-18
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        if (SessionUtil.hasLogin(ctx.channel())) {
            System.out.println("当前连接登陆验证完毕，无需再次验证，AuthHandler被移除");
        } else {
            System.out.println("无登陆验证，强行关闭连接！");
        }
    }
}
