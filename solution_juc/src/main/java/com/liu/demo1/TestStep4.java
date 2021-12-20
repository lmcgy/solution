package com.liu.demo1;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Gavin
 * @Date: 2021/12/20 16:27
 */
@Slf4j
public class TestStep4 {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTackOut = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            synchronized (room) {
                log.info("有烟吗？:{}",hasCigarette);
                while (!hasCigarette) {
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
                } else {
                    log.info("干不了");
                }
            }
        }, "小南").start();

        new Thread(()->{
            synchronized (room) {
                log.info("外卖到了吗？:{}",hasTackOut);
                while (!hasTackOut) {
                    log.info("没有到，休息下！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("外卖到了吗？:{}",hasTackOut);
                if (hasTackOut){
                    log.info("有外卖了，开始干活!");
                } else {
                    log.info("没有外卖了,干不了");
                }
            }
        },"小龙女").start();

        Thread.sleep(3000);

        new Thread(()->{
            synchronized (room) {
                hasTackOut = true;
                log.info("外卖到了噢");
                room.notifyAll();
            }
        },"送外卖的").start();

        Thread.sleep(2000);

        new Thread(()->{
            synchronized (room) {
                hasCigarette = true;
                log.info("烟草到了噢");
                room.notifyAll();
            }
        },"送烟草的").start();
    }
}
