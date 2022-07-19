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
        ByteBuffer allocate = ByteBuffer.allocate(16);
        // 1. 字符串转bytebuffer
        allocate.put("hello".getBytes());

        // 2. charset
        ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");

        // 3.wrap
        ByteBuffer wrap = ByteBuffer.wrap("hello".getBytes());


    }
}
