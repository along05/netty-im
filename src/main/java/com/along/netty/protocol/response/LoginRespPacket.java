package com.along.netty.protocol.response;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-22
 */
@Data
public class LoginRespPacket extends Packet {

    private String userId ;

    private String username ;

    private boolean success ;

    private String reason ;

    @Override
    public byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
