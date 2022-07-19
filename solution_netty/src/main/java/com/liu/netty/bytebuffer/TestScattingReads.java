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
public class TestScattingReads {
    public static void main(String[] args) {


        try (FileChannel channel = new RandomAccessFile("/Users/lmcgy/software/projects/solution/solution_netty/data/read.txt", "r").getChannel()) {
            ByteBuffer one = ByteBuffer.allocate(3);
            ByteBuffer two = ByteBuffer.allocate(3);
            ByteBuffer three = ByteBuffer.allocate(5);

            channel.read(new ByteBuffer[]{one,two,three});

            one.flip();
            two.flip();
            three.flip();

            String s = StandardCharsets.UTF_8.decode(one).toString();
            System.out.println(s);
            String s1 = StandardCharsets.UTF_8.decode(two).toString();
            System.out.println(s1);
            String s2 = StandardCharsets.UTF_8.decode(three).toString();
            System.out.println(s2);

        } catch (IOException e) {
        }
    }
}
