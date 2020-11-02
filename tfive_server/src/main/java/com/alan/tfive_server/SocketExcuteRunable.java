package com.alan.tfive_server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author alan
 * function:
 */
public class SocketExcuteRunable implements Runnable {

    private Socket socket;

    public SocketExcuteRunable(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        // 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(bytes)) != -1) {
                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                sb.append(new String(bytes, 0, len,"UTF-8"));
            }
            System.out.println("get message from client: " + sb);
            OutputStream outputStream = socket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            String buffer = "buffer";
            bufferedOutputStream.write(buffer.getBytes());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
