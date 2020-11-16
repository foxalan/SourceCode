package com.alan.tfive_server;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author alan
 * function:
 */
public class ClientServer implements  Runnable{
    private static final String TAG = "ClientServer";

    private final int mPort;

    private boolean mIsRunning;

    private ServerSocket mServerSocket;

    private Context context;

    public ClientServer(Context context, int port) {
        mPort = port;
        this.context=context;
    }

    public void start() {
        Log.i("wenfeng","start");
        mIsRunning = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            mServerSocket = new ServerSocket(mPort);
            Log.i("wenfeng","wait for accept"); //等待连接
            Socket socket = mServerSocket.accept();
            handle(socket);  //处理连接
        } catch (SocketException e) {
            // The server was stopped; ignore.
            Log.i("wenfeng","SocketException",e);
        } catch (IOException e) {
            Log.i("wenfeng","IOException",e);
        } catch (Exception ignore) {
        }
    }

    public void handle(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            AssetManager mAssets = context.getResources().getAssets();
            String route = "wenfeng.html";
            output = new PrintStream(socket.getOutputStream());
            byte[] bytes;
            //装载wenfeng.html文件，用于返回给浏览器
            bytes = loadContent(route, mAssets);

            //返回服务器状态
            output.println("HTTP/1.0 200 OK");
            output.println("Content-Type: " + "text/html");
            output.println("Content-Length: " + bytes.length);
            output.println();
            //返回数据
            output.write(bytes);
            output.flush();
        } finally {
            try {
                if (null != output) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //该函数用于装载html文件
    public static byte[] loadContent(String fileName, AssetManager assetManager) throws IOException {
        InputStream input = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            input = assetManager.open(fileName);
            byte[] buffer = new byte[1024];
            int size;
            while (-1 != (size = input.read(buffer))) {
                output.write(buffer, 0, size);
            }
            output.flush();
            return output.toByteArray();
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
