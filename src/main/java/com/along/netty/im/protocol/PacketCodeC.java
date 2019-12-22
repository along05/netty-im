package com.along.netty.im.protocol;

import com.along.netty.im.protocol.command.Command;
import com.along.netty.im.protocol.request.*;
import com.along.netty.im.protocol.response.*;
import com.along.netty.im.serialize.Serializer;
import com.along.netty.im.serialize.SerializerAlgorithm;
import com.along.netty.im.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huanglong
 * @date 2019-12-15
 */
public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();


    public static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Serializer> serializerAlgorithmMap;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    static {

        serializerAlgorithmMap = new HashMap<>();
        serializerAlgorithmMap.put(SerializerAlgorithm.JSON, new JSONSerializer());

        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageReqPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageRespPackage.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_RESPONSE, LogoutRespPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupReqPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupRespPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
    }

    public ByteBuf encode( ByteBuf byteBuf, Packet packet) {

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommandType());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializerAlgorithm = byteBuf.readByte();
        byte commandType = byteBuf.readByte();

        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Serializer serializer = getSerializer(serializerAlgorithm);
        Class<? extends Packet> packetClazz = getPacketType(commandType);

        if (serializer != null && packetClazz != null) {
            return serializer.dSerialize(packetClazz, bytes);
        }
        return null;

    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return serializerAlgorithmMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getPacketType(byte packetType) {
        return packetTypeMap.get(packetType);
    }


}
