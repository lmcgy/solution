package com.liu.demo1;


import lombok.extern.slf4j.Slf4j;



/**
 *  wait notify 的正确姿势
 * @Author: Gavin
 * @Date: 2021/12/20 14:35
 */
@Slf4j
public class TestStep1 {



    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTackOut = false;


    public static void main(String[] args) throws Exception {

        new Thread(() -> {
            synchronized (room) {
                log.info("有烟没？[{}]", hasCigarette);
                if (!hasCigarette) {
                    log.info("没烟，先歇会！");
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.info("可以开始干活了");
                }
            }
        }, "小南").start();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    log.info("可以开始干活了");
                }
            }, "其它人").start();

        }

        Thread.sleep(2);
        new Thread(() -> {
            // 这里能不能加 synchronized (room)？
            hasCigarette = true;
            log.info("烟到了噢！");
        }, "送烟的").start();
    }

}
