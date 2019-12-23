package com.along.netty.protocol.request;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-22
 */
@Data
public class LoginReqPacket extends Packet {


    private String userName;

    private String password;

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
