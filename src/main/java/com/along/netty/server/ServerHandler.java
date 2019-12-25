package com.along.netty.server;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.LoginReqPacket;
import com.along.netty.protocol.request.LogoutReqPacket;
import com.along.netty.protocol.request.SendMessageReqPacket;
import com.along.netty.protocol.response.LoginRespPacket;
import com.along.netty.protocol.response.LogoutResponsePacket;
import com.along.netty.protocol.response.SendMessageRespPacket;
import com.along.netty.session.Session;
import com.along.netty.utils.IDUtils;
import com.along.netty.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        //处理登陆
        if (packet instanceof LoginReqPacket) {

            LoginReqPacket loginReqPacket = (LoginReqPacket) packet;

            LoginRespPacket loginRespPacket = new LoginRespPacket();
            loginRespPacket.setSuccess(vailLogin(loginReqPacket));
            loginRespPacket.setUsername(loginReqPacket.getUserName());
            loginRespPacket.setUserId(IDUtils.getUserId());
            loginRespPacket.setReason("登陆成功");

            ByteBuf loginRespBytebuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), loginRespPacket);
            System.out.println("【用户登陆】用户名 - 用户id：" + loginRespPacket.getUsername() + "-" + loginRespPacket.getUserId());
            SessionUtils.bindLogin(
                    new Session(loginRespPacket.getUserId(), loginRespPacket.getUsername()),
                    ctx.channel()
            );
            ctx.channel().writeAndFlush(loginRespBytebuf);
        }

        //处理登出
        if (packet instanceof LogoutReqPacket) {
            LogoutReqPacket logoutReqPacket = (LogoutReqPacket) packet;

            LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
            logoutResponsePacket.setSuccess(bindLogin(logoutReqPacket));
            logoutResponsePacket.setReason("成功");

            SessionUtils.unBindSession(ctx.channel());
            ByteBuf logoutRespByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), logoutResponsePacket);
            ctx.channel().writeAndFlush(logoutRespByteBuf);
        }

        //处理消息发送
        if (packet instanceof SendMessageReqPacket){
            SendMessageReqPacket sendMessageReqPacket = (SendMessageReqPacket) packet ;

            SendMessageRespPacket sendMessageRespPacket = new SendMessageRespPacket() ;
            Session sendUserSession = SessionUtils.getSession(ctx.channel()) ;
            if (sendUserSession!=null){
                sendMessageRespPacket.setFromUserId(sendUserSession.getUserId());
                sendMessageRespPacket.setFromUsername(sendUserSession.getUsername());
                sendMessageRespPacket.setMessage(sendMessageReqPacket.getMessage());
                //细节，给接受人的channel发送信息
                Channel sendChannel = SessionUtils.getChannel(sendMessageReqPacket.getToUserId());
                ByteBuf sendMessageBuf = PacketCodeC.INSTANCE.encode(sendChannel.alloc().ioBuffer()
                        , sendMessageRespPacket);
                sendChannel.writeAndFlush(sendMessageBuf) ;
                System.out.println("发送消息成功");
            } else {
                System.out.println("要发送消息的人不在线");
            }
        }

    }

    private boolean vailLogin(LoginReqPacket loginReqPacket) {
        return true;
    }

    private boolean bindLogin(LogoutReqPacket logoutReqPacket) {
        return true;
    }
}
