package com.along.netty.im.client.console;

import com.along.netty.im.protocol.request.MessageReqPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Channel channel,Scanner scanner) {
        System.out.print("发送消息给某个某个用户：");
        String toUserId = scanner.next();
        System.out.println("请输入要发送的内容");
        String message = scanner.next();
        channel.writeAndFlush(new MessageReqPacket(toUserId, message));
    }
}
