package com.along.netty.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author huanglong
 * @date 2019-12-22
 */
public class Client {

    public static void main(String[] args) {
        new Thread(() -> {
            try{
                Socket socket = new Socket("127.0.0.1" , 8000) ;
                while(true){
                    try{
                        socket.getOutputStream().write(System.getProperty("name").getBytes());
                        System.out.println(System.getProperty("name"));
                        Thread.sleep(2000);
                    }catch (Exception e){}
                }
            }catch (IOException e){}
        }).start();
    }

}
