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
        //1.获取数据
        ByteBuf byteBuf = getByteBuf(ctx) ;

        //2.写数据
        ctx.channel().writeAndFlush(byteBuf) ;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //read方法监听数据
        ByteBuf byteBuf = (ByteBuf) msg ;
        System.out.println("[来自服务端]:" + byteBuf.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        //获取二进制对象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        //准备数据，指定utf-8
        byte[] bytes = "你好，我是客户端".getBytes(Charset.forName("utf-8"));

        //3.填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

}
