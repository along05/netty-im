package com.along.netty.server;

import com.along.netty.codec.PacketCodecHandler;
import com.along.netty.codec.PacketDecoder;
import com.along.netty.codec.PacketEncoder;
import com.along.netty.codec.Spliter;
import com.along.netty.server.Handler.*;
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
                        ch.pipeline().addLast(new Spliter());
                        //解码+编码
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        //登陆
                        ch.pipeline().addLast(LoginReqHandler.INSTANCE);
                        //鉴权
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        //退出登陆
                        ch.pipeline().addLast(LogoutReqHandler.INSTANCE);
                        //消息处理
                        ch.pipeline().addLast(SendMessageReqHandler.INSTANCE);
                        //创建群
                        ch.pipeline().addLast(CreateGroupReqHandler.INSTANCE);
                        //发群消息
                        ch.pipeline().addLast(SendGroupMessageReqHandler.INSTANCE);
                        System.out.println("111");
                    }
                });
        bind(serverBootstrap, PORT);
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
