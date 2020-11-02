package com.alan.tfive_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author alan
 * function:
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
            // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from client: " + sb);

            OutputStream outputStream = socket.getOutputStream();
            String info = "这里是服务器端，我们接受到了你的请求信息，正在处理...处理完成！";
            outputStream.write(info.getBytes());
            outputStream.close();
            socket.close();
        }

    }

}
