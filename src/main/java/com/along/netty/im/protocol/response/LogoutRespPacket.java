package com.along.netty.im.protocol.response;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.command.Command;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-21
 */
@Data
public class LogoutRespPacket extends Packet {

    private boolean success ;

    private String reason ;

    @Override
    public Byte getCommandType() {
        return Command.LOGOUT_RESPONSE;
    }

}
