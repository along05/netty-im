package com.along.netty.im.client.console;

import com.along.netty.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-21
 */
public class LoginConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Channel channel, Scanner scanner) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket() ;

        System.out.println("请输入用户名登录");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("123456");

        //发送数据
        channel.writeAndFlush(loginRequestPacket) ;
        waitForLoginResponse();
    }


    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
