package com.along.netty.client;


import com.along.netty.client.console.ConsoleCommandManager;
import com.along.netty.client.console.LoginConsoleCommand;
import com.along.netty.client.handler.*;
import com.along.netty.codec.PacketDecoder;
import com.along.netty.codec.PacketEncoder;
import com.along.netty.codec.Spliter;
import com.along.netty.utils.SessionUtils;
import com.sun.org.apache.regexp.internal.RE;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 8000;
    private static final Integer DELAY = 5000;
    private static final Integer MAX_RETRY = 5;

    public static void main(String[] args) {

        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DELAY)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        //ch.pipeline().addLast(new ClientHandler());
                        //拆包
                        ch.pipeline().addLast(new Spliter());
                        //解码
                        ch.pipeline().addLast(new PacketDecoder());
                        //登陆响应
                        ch.pipeline().addLast(new LoginRespHandler());
                        //退出登陆响应
                        ch.pipeline().addLast(new LogoutRespHandler());
                        //收消息响应
                        ch.pipeline().addLast(new SendMessageRespHandler());
                        //创建群聊响应
                        ch.pipeline().addLast(new CreateGroupRespHandler());
                        //群消息响应
                        ch.pipeline().addLast(new SendGroupMessageRespHandler());
                        //编码
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接服务端成功");
                //开启指令输入的线程
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else {
                if (retry > 0) {
                    int order = (MAX_RETRY - retry) + 1;
                    //本地重连时间
                    int delay = 1 << order;
                    bootstrap.config().group().schedule(() -> {
                        connect(bootstrap, host, port, retry - 1);
                    }, delay, TimeUnit.SECONDS);
                } else {
                    System.out.println("连接服务器失败");
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtils.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }


}
