package com.along.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class NettyServer {

    private static final Integer PORT = 8000;

    public static void main(String[] args) {

        NioEventLoopGroup acceptGroup = new NioEventLoopGroup();
        NioEventLoopGroup dealGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(acceptGroup, dealGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });
        bind(serverBootstrap ,PORT) ;
    }

    private static void bind(ServerBootstrap serverBootstrap, Integer port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("绑定端口【" + port + "】成功");
            } else {
                System.out.println("绑定端口【" + port + "】失败");
            }
        });
    }


}
