package com.along.netty.serialize;

import com.along.netty.protocol.Packet;
import com.along.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer() ;

    /**
     * 获取序列化算法
     * @return
     */
    byte getAlgorithmType() ;

    /**
     * java对象转为二进制
     */
    byte[] serializer(Object o) ;

    /**
     * 把二进制对象转为java对象
     */
    <T> T deserialize(Class<T> clazz , byte[] bytes);

}
