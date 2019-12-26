package com.along.netty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public class NettyServer {

    /**
     * .group()
     * 一个用于接收连接， 一个用于处理已经接收的连接
     *
     * .channel()
     * 指定了io和nio
     *
     * option()
     * 指定服务端tcp连接的一些属性
     *
     * .childOption() 方法
     * 可以给每条连接设置一些TCP底层相关的属性
     * ChannelOption.SO_KEEPALIVE表示是否开启TCP底层心跳机制，true为开启
     * ChannelOption.TCP_NODELAY表示是否开启Nagle算法，true表示关闭，false表示开启，通俗地说，
     * 如果要求高实时性，有数据发送时就马上发送，就关闭，如果需要减少发送次数减少网络交互，就开启。
     *
     * childHandler()
     * 具体处理连接上的业务逻辑
     *
     */


    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup acceptGroup = new NioEventLoopGroup();
        NioEventLoopGroup delaGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(acceptGroup, delaGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new ServerHandler()) ;
                    }
                });
        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }

}
