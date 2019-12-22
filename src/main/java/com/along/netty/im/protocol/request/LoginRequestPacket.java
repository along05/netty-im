package com.along.netty.im.protocol.request;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-15
 */
@Data
public class LoginRequestPacket extends Packet {

    private String userId ;

    private String userName ;

    private String password ;

    @Override
    public Byte getCommandType() {
        return Command.LOGIN_REQUEST;
    }
}
