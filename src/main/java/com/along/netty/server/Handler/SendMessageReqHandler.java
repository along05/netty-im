package com.along.netty.server.Handler;

import com.along.netty.protocol.request.SendMessageReqPacket;
import com.along.netty.protocol.response.SendMessageRespPacket;
import com.along.netty.session.Session;
import com.along.netty.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author huanglong
 * @date 2019-12-25
 */
@ChannelHandler.Sharable
public class SendMessageReqHandler extends SimpleChannelInboundHandler<SendMessageReqPacket> {

    public static final SendMessageReqHandler INSTANCE = new SendMessageReqHandler();

    private SendMessageReqHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SendMessageReqPacket sendMessageReqPacket) {
        SendMessageRespPacket sendMessageRespPacket = new SendMessageRespPacket();
        Session sendUserSession = SessionUtils.getSession(ctx.channel());
        if (sendUserSession != null) {
            sendMessageRespPacket.setFromUserId(sendUserSession.getUserId());
            sendMessageRespPacket.setFromUsername(sendUserSession.getUsername());
            sendMessageRespPacket.setMessage(sendMessageReqPacket.getMessage());
            //细节，给接受人的channel发送信息
            Channel sendChannel = SessionUtils.getChannel(sendMessageReqPacket.getToUserId());
            sendChannel.writeAndFlush(sendMessageRespPacket);
            System.out.println("发送消息成功");
        } else {
            System.out.println("要发送消息的人不在线");
        }
    }
}
