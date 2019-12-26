package com.along.netty.socket;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端两秒发送一个hello world给服务端，服务端收到以后打印
 *
 * @author huanglong
 * @date 2019-12-22
 */
public class Server {

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);

        //（1）接受新连接线程
        new Thread(() -> {
            while (true) {
                try {
                    //阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    //每个连接创建一个新的线程，负责读取数据
                    new Thread(() -> {
                        try{
                            int len;
                            byte[] data = new byte[1024] ;
                            InputStream inputStream = socket.getInputStream();
                            //按字节流读取数据
                            while((len = inputStream.read(data)) != -1){
                                System.out.println("["+Thread.currentThread().getName()+"]:"+new String(data, 0, len));
                            }
                        }catch (IOException e){
                            System.out.println("【传输异常】:" + e.getMessage());
                        }
                    }).start();
                } catch (IOException e) {
                    System.out.println("【传输异常】:" + e.getMessage());
                }
            }
        }).start();
    }
}
