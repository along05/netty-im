package com.along.netty.server;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.*;
import com.along.netty.protocol.response.*;
import com.along.netty.session.Session;
import com.along.netty.utils.IDUtils;
import com.along.netty.utils.SessionUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

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
            LoginRespPacket loginRespPacket = new LoginRespPacket();
            loginRespPacket.setSuccess(vailLogin((LoginReqPacket) packet));
            loginRespPacket.setUsername(((LoginReqPacket) packet).getUserName());
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
        if (packet instanceof SendMessageReqPacket) {
            SendMessageReqPacket sendMessageReqPacket = (SendMessageReqPacket) packet;

            SendMessageRespPacket sendMessageRespPacket = new SendMessageRespPacket();
            Session sendUserSession = SessionUtils.getSession(ctx.channel());
            if (sendUserSession != null) {
                sendMessageRespPacket.setFromUserId(sendUserSession.getUserId());
                sendMessageRespPacket.setFromUsername(sendUserSession.getUsername());
                sendMessageRespPacket.setMessage(sendMessageReqPacket.getMessage());
                //细节，给接受人的channel发送信息
                Channel sendChannel = SessionUtils.getChannel(sendMessageReqPacket.getToUserId());
                ByteBuf sendMessageBuf = PacketCodeC.INSTANCE.encode(sendChannel.alloc().ioBuffer()
                        , sendMessageRespPacket);
                sendChannel.writeAndFlush(sendMessageBuf);
                System.out.println("发送消息成功");
            } else {
                System.out.println("要发送消息的人不在线");
            }
        }

        //处理群创建
        if (packet instanceof CreateGroupReqPacket) {
            CreateGroupReqPacket createGroupReqPacket = (CreateGroupReqPacket) packet;
            List<String> ids = createGroupReqPacket.getUserIdList();
            List<String> usernameList = new ArrayList<>();
            // 1. 创建一个 channel 分组
            ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

            for (String userId : ids) {
                Channel channel = SessionUtils.getChannel(userId);
                if (channel != null) {
                    Session session = SessionUtils.getSession(channel);
                    usernameList.add(session.getUsername());
                    channelGroup.add(channel);
                }
            }

            String groupId = IDUtils.getGroupId();
            CreateGroupRespPacket createGroupRespPacket = new CreateGroupRespPacket();
            createGroupRespPacket.setUserNameList(usernameList);
            createGroupRespPacket.setGroupId(groupId);

            ByteBuf createGroupBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), createGroupRespPacket);
            channelGroup.writeAndFlush(createGroupBuf);

            //保存群组信息
            System.out.println("【创建群成功】，群id为"+groupId);
            SessionUtils.bindChannelGroup(groupId, channelGroup);
        }

        //群消息发送
        if (packet instanceof SendGroupMessageReqPacket) {

            SendGroupMessageReqPacket sendGroupMessageReqPacket = (SendGroupMessageReqPacket) packet;

            SendGroupMessageRespPacket sendGroupMessageRespPacket = new SendGroupMessageRespPacket();
            sendGroupMessageRespPacket.setGroupId(sendGroupMessageReqPacket.getGroupId());
            sendGroupMessageRespPacket.setMessage(sendGroupMessageReqPacket.getMessage());

            ChannelGroup channelGroup = SessionUtils.getChannelGroup(sendGroupMessageReqPacket.getGroupId());

            ByteBuf sendMessageRespByte = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), sendGroupMessageRespPacket);
            channelGroup.writeAndFlush(sendMessageRespByte);
        }

    }

    private boolean vailLogin(LoginReqPacket loginReqPacket) {
        return true;
    }

    private boolean bindLogin(LogoutReqPacket logoutReqPacket) {
        return true;
    }
}
