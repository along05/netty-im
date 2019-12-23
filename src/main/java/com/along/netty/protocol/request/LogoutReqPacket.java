package com.along.netty.protocol.request;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;

/**
 * @author huanglong
 * @date 2019-12-24
 */
public class LogoutReqPacket extends Packet {

    @Override
    public byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
