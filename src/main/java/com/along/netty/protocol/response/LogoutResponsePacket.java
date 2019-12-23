package com.along.netty.protocol.response;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public byte getCommand() {

        return Command.LOGOUT_RESPONSE;
    }
}
