package com.along.netty.protocol;

import com.along.netty.protocol.command.Command;
import com.along.netty.protocol.request.*;
import com.along.netty.protocol.response.*;
import com.along.netty.serialize.SerializeAlgorithm;
import com.along.netty.serialize.Serializer;
import com.along.netty.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * 协议组成：4位魔术值 + 1位版本 + 1位指令 + 数据长度 + 请求包
 *
 * @author huanglong
 * @date 2019-12-22
 */
public class PacketCodeC {

    //每8位二进制占一个字节，所以8位16进制=32位2进制，所以是4个字节
    public static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Serializer> serializerMap;
    private final Map<Byte, Class<? extends Packet>> packetMap;

    private PacketCodeC() {
        serializerMap = new HashMap<>();
        packetMap = new HashMap<>();

        Serializer jsonSerializer = new JSONSerializer();
        serializerMap.put(SerializeAlgorithm.JSON, jsonSerializer);

        packetMap.put(Command.LOGIN_REQUEST, LoginReqPacket.class);
        packetMap.put(Command.LOGIN_RESPONSE, LoginRespPacket.class);
        packetMap.put(Command.SEND_MESSAGE_REQUEST, SendMessageReqPacket.class);
        packetMap.put(Command.SEND_MESSAGE_RESPONSE, SendMessageRespPacket.class);
        packetMap.put(Command.LOGOUT_REQUEST, LogoutReqPacket.class);
        packetMap.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetMap.put(Command.CREATE_GROUP_REQ, CreateGroupReqPacket.class);
        packetMap.put(Command.CREATE_GROUP_RESP, CreateGroupRespPacket.class);
        packetMap.put(Command.SEND_GROUP_MESSAGE_REQ, SendGroupMessageReqPacket.class);
        packetMap.put(Command.SEND_GROUP_MESSAGE_RESP, SendGroupMessageRespPacket.class);
    }

    /**
     * 按照协议编码
     */
    public ByteBuf encode(ByteBuf buf, Packet packet) {

        //反序列化packet对象
        byte[] bytes = Serializer.DEFAULT.serializer(packet);

        //实际编码
        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getAlgorithmType());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);

        return buf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过魔数值
        byteBuf.skipBytes(4) ;
        //跳过版本
        byteBuf.skipBytes(1) ;
        //序列化方式
        byte serialize = byteBuf.readByte() ;
        //指令
        byte command = byteBuf.readByte() ;
        //数据长度
        int length = byteBuf.readInt() ;

        //packet数据
        byte[] packetBytes = new byte[length] ;
        byteBuf.readBytes(packetBytes) ;

        Class<? extends Packet> packetClazz = packetMap.get(command) ;
        Serializer serializer = serializerMap.get(serialize) ;

        if (packetBytes!=null && serializer!=null){
            return serializer.deserialize(packetClazz , packetBytes);
        } else {
            System.out.println("解析数据失败");
        }
        return null;
    }

}
