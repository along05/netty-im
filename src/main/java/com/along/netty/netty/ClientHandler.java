package com.along.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 1000; i++) {
            //1.获取数据
            ByteBuf byteBuf = getByteBuf(ctx) ;

            //2.写数据
            ctx.channel().writeAndFlush(byteBuf) ;
        }
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        //获取二进制对象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        //准备数据，指定utf-8
        byte[] bytes = "这里是客户端，我说会有粘包的情况，他们不信~".getBytes(Charset.forName("utf-8"));

        //3.填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

}
