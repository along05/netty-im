package com.along.netty.im.protocol.request;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author huanglong
 * @date 2019-12-21
 */
@Data
public class CreateGroupReqPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommandType() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
