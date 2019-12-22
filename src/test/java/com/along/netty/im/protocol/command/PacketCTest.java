package com.along.netty.im.protocol.command;

import com.along.netty.im.protocol.Packet;
import com.along.netty.im.protocol.PacketCodeC;
import com.along.netty.im.protocol.request.LoginRequestPacket;
import com.along.netty.im.serialize.Serializer;
import com.along.netty.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author huanglong
 * @date 2019-12-15
 */
public class PacketCTest {


    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUserName("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        //编码
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT.ioBuffer(), loginRequestPacket);
        //解码
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }
}
