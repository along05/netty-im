package com.along.netty.im.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-21
 */
public interface ConsoleCommand {

    void exec(Channel channel , Scanner scanner) ;

}
