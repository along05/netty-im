package com.along.netty.client;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.response.*;
import com.along.netty.session.Session;
import com.along.netty.utils.SessionUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


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

        //处理群创建
        if (packet instanceof CreateGroupRespPacket){
            CreateGroupRespPacket createGroupRespPacket = (CreateGroupRespPacket) packet ;
            System.out.println("【群创建】成功，id为【"
                    +createGroupRespPacket.getGroupId()+"】，群成员有"
                    +createGroupRespPacket.getUserNameList());
        }

        //处理群消息
        if (packet instanceof SendGroupMessageRespPacket){
            SendGroupMessageRespPacket sendGroupMessageRespPacket = (SendGroupMessageRespPacket) packet ;
            String groupId = sendGroupMessageRespPacket.getGroupId() ;
            String message = sendGroupMessageRespPacket.getMessage() ;
            System.out.println("来自群【"+groupId+"】消息："+message);
        }
    }

}
