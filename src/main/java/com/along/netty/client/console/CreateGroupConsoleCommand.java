package com.along.netty.client.console;

import com.along.netty.protocol.PacketCodeC;
import com.along.netty.protocol.request.CreateGroupReqPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-24
 */
public class CreateGroupConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("【建群】：请输入群成员的userId，用英文逗号隔开：" );
        String userIds = scanner.nextLine() ;

        CreateGroupReqPacket createGroupReqPacket = new CreateGroupReqPacket();
        createGroupReqPacket.setUserIdList(Arrays.asList(userIds.split(",")));

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().ioBuffer() , createGroupReqPacket);
        channel.writeAndFlush(byteBuf) ;
    }
}
