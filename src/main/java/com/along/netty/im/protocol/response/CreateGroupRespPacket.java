package com.along.netty.im.protocol.response;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author huanglong
 * @date 2019-12-21
 */
@Data
public class CreateGroupRespPacket extends Packet {

    private boolean success;

    private String groupId;

    private List<String> userNameList;

    @Override
    public Byte getCommandType() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
