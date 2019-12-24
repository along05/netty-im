package com.along.netty.protocol.response;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-24
 */
@Data
public class SendGroupMessageRespPacket extends Packet {

    private String groupId ;

    private String message ;

    @Override
    public byte getCommand() {
        return Command.SEND_GROUP_MESSAGE_RESP;
    }
}
