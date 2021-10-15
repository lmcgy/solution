package com.liu.chat;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatChannel implements Runnable {

    public static List<ChatChannel> all = new ArrayList<ChatChannel>();// 通道列表

    private DataInputStream dis; // 输入流
    private DataOutputStream dos;// 输出流
    private String name;// 客户端名称
    private boolean isRunning = true;

    public ChatChannel(Socket client) {
        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
            this.name = dis.readUTF();
            System.out.println(this.name + "进入了聊天室");
            this.send(this.name + "，您好！欢迎您进入聊天室");
            sendOthers(this.name + "进入了聊天室", true); // 系统消息
        } catch (IOException e) {
            e.printStackTrace();
            Util.closeAll(dis, dos);
            isRunning = false;
        }
    }

    /**
     * 读取数据
     */
    private String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            Util.closeAll(dis);
            isRunning = false;
            all.remove(this); // 移除自身
        }
        return msg;
    }

    /**
     * 发送数据
     */
    private void send(String msg) {
        if (msg != null && !"".equals(msg)) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                Util.closeAll(dos);
                isRunning = false;
                all.remove(this); // 移除自身
            }
        }
    }

    /**
     * @param msg    消息内容
     * @param sysMsg 是否是系统消息
     */
    private void sendOthers(String msg, boolean sysMsg) {
        // 加入私聊的判断，约定@name#格式为私聊
        if (msg.startsWith("@") && msg.indexOf("#") > -1) { // 私聊
            // 获取name
            String name = msg.substring(1, msg.indexOf("#"));
            String content = msg.substring(msg.indexOf("#") + 1);
            for (ChatChannel other : all) {
                if (name.equals(other.name)) {
                    other.send(this.name + "悄悄地对您说:" + content);
                }
            }
        } else {
            for (ChatChannel other : all) {
                if (other == this) {
                    continue;
                }
                if (sysMsg) {
                    other.send("系统信息:" + msg);
                } else {
                    // 发送其他客户端
                    other.send(this.name + "对所有人说:" + msg);
                }
            }
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            sendOthers(receive(), false); // 用户消息
        }
    }
}
