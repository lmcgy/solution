package com.liu.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            Socket client = server.accept();
            ChatChannel channel = new ChatChannel(client);
            ChatChannel.all.add(channel);// 统一管理客户端的通道
            new Thread(channel).start(); // 启动一条通道
        }
    }

}
