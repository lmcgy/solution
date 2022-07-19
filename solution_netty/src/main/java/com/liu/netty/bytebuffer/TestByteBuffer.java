package com.liu.netty.bytebuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: miao.liu
 * @create: 2022-07-19 21:33
 * @describe: byteBuffer
 **/
public class TestByteBuffer {

    /**
     * ByteBuffer 主要的几个字段
     * private int mark = -1  标记;
     * private int position 读写指向位置;
     * private int limit 读写限制值;
     * private int capacity   初始值也就是 容量
     */
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("/Users/lmcgy/software/projects/solution/solution_netty/data.txt").getChannel()) {
            // 加载到java内存中
            ByteBuffer buffer = ByteBuffer.allocate(5);

            // 加载到物理内存中，不由jvm管理
            // ByteBuffer.allocateDirect(5);

            while (true){
                // 读到末尾就是-1
                int read = channel.read(buffer);
                if (-1 == read){
                    break;
                }

                // 切换到读取的模式
                buffer.flip();

                while (buffer.hasRemaining()){
                    byte b = buffer.get();
                    System.out.print((char) b);
                }

                // 压缩 或者 clear  - buffer.clear()
                buffer.compact();

                // 设置从头开始读取-buffer.rewind();

                // buffer.mark(); buffer.reset();


            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
