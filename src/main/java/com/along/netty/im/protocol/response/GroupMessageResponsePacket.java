package com.along.netty.im.protocol.response;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import com.along.netty.im.session.Session;
import lombok.Data;


@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;

    private Session fromUser;

    private String message;

    @Override
    public Byte getCommandType() {

        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
