package com.liu.netty.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author: miao.liu
 * @create: 2022-07-20 20:22
 * @describe:
 **/
public class TestFileChannel {

    public static void main(String[] args) {

        try (FileChannel input = new FileInputStream("/Users/lmcgy/software/projects/solution/solution_netty/data/data.txt").getChannel();
             FileChannel out = new FileOutputStream("/Users/lmcgy/software/projects/solution/solution_netty/data/to.txt").getChannel()
        ) {
            long size = input.size();
            for (long i = size; i > 0 ; ) {
                // transferTo 一次最大能拷贝2G ,会返回实际拷贝的数据量

                long l = input.transferTo(size-i, i, out);
                i =  i - l;
            }
        } catch (IOException e) {
        }

    }
}
