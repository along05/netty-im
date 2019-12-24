package com.along.netty.protocol.response;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author huanglong
 * @date 2019-12-24
 */
@Data
public class CreateGroupRespPacket extends Packet {

    private String groupId;
    private List<String> userNameList;

    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_RESP;
    }
}
