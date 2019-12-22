package com.along.netty.im.protocol.request;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huanglong
 * @date 2019-12-16
 */
@Data
@NoArgsConstructor
public class MessageReqPacket extends Packet {

    private String toUserId;
    private String message;


    public MessageReqPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommandType() {
        return Command.MESSAGE_REQUEST;
    }

}
