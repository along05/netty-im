package com.along.netty.im.protocol.request;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-21
 */
@Data
public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommandType() {
        return Command.LOGOUT_REQUEST;
    }


}
