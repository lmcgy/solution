package com.liu.chat;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {


    public static void main(String[] args) throws IOException {
        System.out.println("请输入您的名称:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();
        if ("".equals(name)) {
            return;
        }
        Socket client = new Socket("localhost", 8888);
        new Thread(new Send(client, name)).start(); // 发送一条通道
        new Thread(new Receive(client)).start(); // 接收一条通道
    }
}
