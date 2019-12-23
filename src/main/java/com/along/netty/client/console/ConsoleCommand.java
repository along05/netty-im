package com.along.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-23
 */
public interface ConsoleCommand {

    void exec(Scanner scanner , Channel channel) ;

}
