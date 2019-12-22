package com.along.netty.im.protocol.request;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;


@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommandType() {

        return Command.QUIT_GROUP_REQUEST;
    }
}
