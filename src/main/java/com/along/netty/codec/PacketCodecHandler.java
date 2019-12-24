package com.along.netty.codec;

import com.along.netty.protocol.Packet;
import com.along.netty.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author huanglong
 * @date 2019-12-25
 */
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf , Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet in, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer() ;
        PacketCodeC.INSTANCE.encode(byteBuf, in);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
