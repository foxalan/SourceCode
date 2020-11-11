package com.alan.tfive_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author alan
 * function:
 * todo
 * 1.编写response
 * 2.post数据解析
 * 3.数据加密
 */
public class MainServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket server = new ServerSocket(port);
        // server将一直等待连接的到来
        System.out.println("server start");

        while (true){
            Socket socket = server.accept();
            System.out.println("accept");
            //todo 使用线程池代替
            new Thread(new SocketExecuteRunnable(socket)).start();
        }
    }

}
