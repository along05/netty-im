package com.along.netty.client.console;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.LoginReqPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginReqPacket loginReqPacket = new LoginReqPacket();

        System.out.println("请输入用户名:");
        loginReqPacket.setUserName(scanner.nextLine());
        loginReqPacket.setPassword("123456");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().ioBuffer() , loginReqPacket);
        channel.writeAndFlush(byteBuf) ;
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

}
