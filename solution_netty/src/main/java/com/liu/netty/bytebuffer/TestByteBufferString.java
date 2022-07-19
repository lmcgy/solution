package com.liu.netty.bytebuffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author: miao.liu
 * @create: 2022-07-19 21:33
 * @describe: byteBuffer to string
 **/
public class TestByteBufferString {

    public static void main(String[] args) {

        // 1. 字符串转bytebuffer
        ByteBuffer allocate = ByteBuffer.allocate(16);
        allocate.put("hello".getBytes());
        allocate.flip(); // 才能读取到
        String s1 = StandardCharsets.UTF_8.decode(allocate).toString();
        System.out.println(s1);

        // 2. charset
        ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");
        String s = StandardCharsets.UTF_8.decode(hello).toString();
        System.out.println(s);

        // 3.wrap
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());


    }
}
