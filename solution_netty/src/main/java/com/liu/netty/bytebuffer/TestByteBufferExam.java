package com.liu.netty.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author: miao.liu
 * @create: 2022-07-19 21:33
 * @describe: 黏包，半包
 **/
public class TestByteBufferExam {

    public static void main(String[] args) {

        ByteBuffer allocate = ByteBuffer.allocate(32);
        allocate.put("Hello,world\nI`m zhangsan\nHo".getBytes());
        splite(allocate);
        allocate.put("w are you?\n".getBytes());
        splite(allocate);


    }

    private static void splite(ByteBuffer allocate) {
        allocate.flip();
        for (int i = 0; i < allocate.limit(); i++) {

            // allocate.get(i) 的position没有移动
            if (allocate.get(i) == '\n'){
                int length = i+1 - allocate.position();
                ByteBuffer buffer = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    buffer.put(allocate.get());
                }
                buffer.flip();
                String s = StandardCharsets.UTF_8.decode(buffer).toString();
                System.out.print(s);
            }
        }
        allocate.compact();
    }
}
