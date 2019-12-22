package com.along.netty.im.serialize;

import com.along.netty.im.serialize.impl.JSONSerializer;

/**
 * @author huanglong
 * @date 2019-12-15
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer() ;

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T dSerialize(Class<T> clazz, byte[] bytes);
}
