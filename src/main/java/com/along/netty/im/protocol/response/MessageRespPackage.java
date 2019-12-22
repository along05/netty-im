package com.along.netty.im.protocol.response;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-16
 */
@Data
public class MessageRespPackage extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommandType() {
        return Command.MESSAGE_RESPONSE;
    }

}
