package com.along.netty.im.protocol.response;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-15
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommandType() {
        return Command.LOGIN_RESPONSE;
    }
}
