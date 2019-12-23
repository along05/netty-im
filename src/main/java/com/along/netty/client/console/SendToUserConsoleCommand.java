package com.along.netty.client.console;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.SendMessageReqPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-24
 */
public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要发送用户的id：");
        String toUserId = scanner.nextLine();
        System.out.println("请输入要发送的消息");
        String message = scanner.nextLine();

        SendMessageReqPacket sendMessageReqPacket = new SendMessageReqPacket();
        sendMessageReqPacket.setToUserId(toUserId);
        sendMessageReqPacket.setMessage(message);

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().ioBuffer(), sendMessageReqPacket);
        channel.writeAndFlush(byteBuf);
    }
}
