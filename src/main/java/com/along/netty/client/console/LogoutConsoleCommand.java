package com.along.netty.client.console;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.LogoutReqPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-24
 */
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutReqPacket logoutReqPacket = new LogoutReqPacket();
        channel.writeAndFlush(logoutReqPacket);
    }
}
