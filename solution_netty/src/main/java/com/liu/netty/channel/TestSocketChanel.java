package com.liu.netty.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * @author: miao.liu
 * @create: 2022-07-21 09:38
 * @describe:
 **/
@Slf4j
public class TestSocketChanel {

    public static void main(String[] args) throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8080));

        SelectionKey sscKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 阻塞，有事件就返回,没事件就同步阻塞
            int select = selector.select();

            if (select > 0) {
                // 获取就绪的事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isAcceptable()) {
                        try (ServerSocketChannel channel = (ServerSocketChannel) next.channel()) {
                            SocketChannel accept = channel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                        }
                    } else if (next.isReadable()) {

                        try (SocketChannel channel = (SocketChannel) next.channel()) {

                            ByteBuffer buffer = ByteBuffer.allocate(16);

                            while (true) {
                                int read = channel.read(buffer);
                                if (read == -1) {
                                    break;
                                }
                                buffer.flip();
                                while (buffer.hasRemaining()) {
                                    byte b = buffer.get();
                                    System.out.println((char) b);
                                }
                                buffer.compact();
                            }
                        }
                    }
                    // 使用后就将事件删除
                    iterator.remove();
                }
            }
        }
    }
}
