package com.along.netty.im.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.along.netty.im.serialize.Serializer;
import com.along.netty.im.serialize.SerializerAlgorithm;

/**
 * @author huanglong
 * @date 2019-12-15
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T dSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
