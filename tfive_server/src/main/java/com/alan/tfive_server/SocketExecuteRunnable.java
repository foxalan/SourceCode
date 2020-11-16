package com.alan.tfive_server;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author alan
 * function:
 */
public class SocketExecuteRunnable implements Runnable {

    private Socket socket;
    //请求类型
    private String requestType;
    //请求Url
    private String url;


    public SocketExecuteRunnable(Socket socket){
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
            System.out.println("从客户端得到的信息 ");
            System.out.println(sb.toString());
            String[] requestArray =  sb.toString().split("\r\n");
            String[] strings2 = requestArray[0].split(" ");
            requestType = strings2[0];
            url = strings2[1];
            System.out.println("请求TYPE: "+requestType);
            System.out.println("请求URL: "+url);
            //拼接请求行
            String responseFirstLine = "HTTP/1.1 200 OK";
            //拼接请求头
            String responseHead = "Content-type:text/plain";
            //192.168.43.104
            PrintStream output = new PrintStream(socket.getOutputStream());

            String jsonData = "{\"票据金额\":\"253.00\"}";
            output.println(responseFirstLine);
            output.println(responseHead);
            output.println("Content-Length: " + jsonData.getBytes().length);
            output.println();
            output.write(jsonData.getBytes());
            output.flush();

//            outputStream.write((responseFirstLine+responseHead+"\r\n"+"hello").getBytes());
//            outputStream.flush();
//            outputStream.write(responseHead.getBytes());
//            outputStream.write("\r\n".getBytes());
//
//            //返回内容
//            outputStream.write("test".getBytes());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
