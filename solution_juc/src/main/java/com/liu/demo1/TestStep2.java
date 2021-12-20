package com.liu.demo1;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * @Author: Gavin
 * @Date: 2021/12/20 15:49
 */
@Slf4j
public class TestStep2 {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTackOut = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            synchronized (room) {
                log.info("有烟吗？:{}",hasCigarette);
                if (!hasCigarette) {
                    log.info("没有烟草，先休息！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("有烟吗？:{}",hasCigarette);
                if (hasCigarette){
                    log.info("有烟草了，开始干活!");
                }
            }
        },"小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (room) {
                    log.info("开始干活");
                }
            },"其他人" ).start();

        }

        sleep(1000);

        new Thread(()->{
            synchronized (room) {
                hasCigarette = true;
                log.info("烟到了");
                room.notify();
            }
        },"送烟的").start();

    }
}
