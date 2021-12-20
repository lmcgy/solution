package com.liu.chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable {

    // 控制台输入
    private BufferedReader console;
    // 输出流
    private DataOutputStream dos;
    // 客户端名称
    private String name;
    // 控制线程
    private boolean isRunning = true;

    public Send(Socket client, String name) {
        try {
            console = new BufferedReader(new InputStreamReader(System.in));
            dos = new DataOutputStream(client.getOutputStream());
            this.name = name;
            send(this.name); // 把自己的名字发给服务端
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
            Util.closeAll(dos, console);
        }
    }

    /**
     * 从控制台接收数据并发送数据
     */
    public void send(String msg) {
        try {
            if (msg != null && !"".equals(msg)) {
                dos.writeUTF(msg);
                dos.flush(); // 强制刷新
            }
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
            Util.closeAll(dos, console);
        }
    }

    // 从控制台接收数据
    private String getMsgFromConsole() {
        try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void run() {
        while (isRunning) {
            send(getMsgFromConsole());
        }
    }
}
