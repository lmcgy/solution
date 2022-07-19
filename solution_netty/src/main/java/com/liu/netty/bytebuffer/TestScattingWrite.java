package com.liu.netty.bytebuffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author: miao.liu
 * @create: 2022-07-19 23:35
 * @describe:
 **/
public class TestScattingWrite {

    public static void main(String[] args) {
        try (FileChannel channel = new RandomAccessFile("/Users/lmcgy/software/projects/solution/solution_netty/data/word.txt","rw").getChannel()) {
            ByteBuffer hello = StandardCharsets.UTF_8.encode("hello");
            ByteBuffer java = StandardCharsets.UTF_8.encode("java");
            ByteBuffer niHao = StandardCharsets.UTF_8.encode("你好");
            channel.write(new ByteBuffer[]{hello,java,niHao});

        } catch (IOException e) {
        }

    }
}
