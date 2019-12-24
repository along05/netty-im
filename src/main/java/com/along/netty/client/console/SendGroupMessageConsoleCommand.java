package com.along.netty.client.console;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.SendGroupMessageReqPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-24
 */
public class SendGroupMessageConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入群聊id");
        String groupId = scanner.nextLine();

        System.out.println("请输入要发送的群消息");
        String message = scanner.nextLine();

        SendGroupMessageReqPacket sendGroupMessageReqPacket = new SendGroupMessageReqPacket();
        sendGroupMessageReqPacket.setGroupId(groupId);
        sendGroupMessageReqPacket.setMessage(message);

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().ioBuffer(), sendGroupMessageReqPacket);
        channel.writeAndFlush(byteBuf);
    }
}
