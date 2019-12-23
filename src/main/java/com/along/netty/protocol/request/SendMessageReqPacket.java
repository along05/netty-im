package com.along.netty.protocol.request;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-22
 */
@Data
public class SendMessageReqPacket extends Packet {

    private String toUserId;

    private String message ;

    @Override
    public byte getCommand() {
        return Command.SEND_MESSAGE_REQUEST;
    }
}
