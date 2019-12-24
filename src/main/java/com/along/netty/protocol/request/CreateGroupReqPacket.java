package com.along.netty.protocol.request;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author huanglong
 * @date 2019-12-24
 */
@Data
public class CreateGroupReqPacket extends Packet {

    private List<String> userIdList ;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_REQ;
    }
}
