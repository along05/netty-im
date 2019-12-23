package com.along.netty.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.along.netty.serialize.SerializeAlgorithm;
import com.along.netty.serialize.Serializer;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getAlgorithmType() {
        return SerializeAlgorithm.JSON ;
    }

    @Override
    public byte[] serializer(Object o) {
        return JSON.toJSONBytes(o) ;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz) ;
    }
}
