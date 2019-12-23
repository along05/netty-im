package com.along.netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author huanglong
 * @date 2019-12-22
 */
@Data
public abstract class Packet {

    @JSONField(deserialize = false , serialize = false)
    private byte version = 1 ;

    @JSONField(serialize = false)
    public abstract byte getCommand() ;
}
