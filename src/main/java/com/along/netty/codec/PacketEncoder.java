package com.along.netty.codec;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
         PacketCodeC.INSTANCE.encode(out,packet);
    }
}
