package com.along.netty.protocol.response;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-22
 */
@Data
public class SendMessageRespPacket extends Packet {

    private String fromUserId ;

    private String fromUsername;

    private String message ;

    @Override
    public byte getCommand() {
        return Command.SEND_MESSAGE_RESPONSE;
    }
}
