package com.along.netty.im.protocol.request;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommandType() {

        return Command.JOIN_GROUP_REQUEST;
    }
}
