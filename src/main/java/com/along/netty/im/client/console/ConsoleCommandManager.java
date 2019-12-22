package com.along.netty.im.client.console;

import com.along.netty.im.utils.SessionUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author huanglong
 * @date 2019-12-21
 */
public class ConsoleCommandManager implements ConsoleCommand{

    public static final Map<String , ConsoleCommand> consoleCommandMap ;

    static {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("sendToGroup", new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Channel channel, Scanner scanner) {
        //获取指令
        String command = scanner.next();
        if (!SessionUtil.hasLogin(channel)){
            return ;
        }

        ConsoleCommand consoleCommand = consoleCommandMap.get(command) ;

        if (consoleCommand!=null){
            consoleCommand.exec(channel , scanner);
        } else {
            System.err.println("无法识别【" + command + "]指令，请重新输入");
        }
    }

}
