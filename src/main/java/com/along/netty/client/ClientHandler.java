package com.along.netty.client;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.LoginReqPacket;
import com.along.netty.protocol.response.LoginRespPacket;
import com.along.netty.protocol.response.LogoutResponsePacket;
import com.along.netty.protocol.response.SendMessageRespPacket;
import com.along.netty.session.Session;
import com.along.netty.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        //处理登陆
        if (packet instanceof LoginRespPacket) {
            LoginRespPacket loginRespPacket = (LoginRespPacket) packet;
            if (loginRespPacket.isSuccess()) {
                System.out.println("【登陆成功】你的id为：" + loginRespPacket.getUserId());
                SessionUtils.bindLogin(
                        new Session(loginRespPacket.getUserId(), loginRespPacket.getUsername()),
                        ctx.channel());
            } else {
                System.out.println("【登陆失败】错误信息：" + loginRespPacket.getReason());
            }
        }

        //处理退出登陆
        if (packet instanceof LogoutResponsePacket) {
            LogoutResponsePacket logoutResponsePacket = (LogoutResponsePacket) packet;
            if (logoutResponsePacket.isSuccess()) {
                System.out.println("退出登陆成功");
                SessionUtils.unBindSession(ctx.channel());
            }
        }

        //处理消息
        if (packet instanceof SendMessageRespPacket) {
            SendMessageRespPacket sendMessageRespPacket = (SendMessageRespPacket) packet;
            System.out.println("【消息】：来自" + sendMessageRespPacket.getFromUsername() + ",内容："
                    + sendMessageRespPacket.getMessage());
        }


    }


}
