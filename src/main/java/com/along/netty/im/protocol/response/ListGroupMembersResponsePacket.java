package com.along.netty.im.protocol.response;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import com.along.netty.im.session.Session;
import lombok.Data;
import java.util.List;


@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommandType() {

        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
