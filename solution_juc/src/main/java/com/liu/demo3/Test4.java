package com.liu.demo3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 版本的等烟、等外卖
 * @Author: Gavin
 * @Date: 2021/12/22 16:41
 */
@Slf4j
public class Test4 {

    static final ReentrantLock room = new ReentrantLock();
    static boolean hasCigarette = false;
    static boolean hasTackOut = false;
    static Condition hasCigaretteCd = room.newCondition();
    static Condition hasTackOutCd = room.newCondition();


    public static void main(String[] args) throws InterruptedException {



        new Thread(() -> {
            room.lock();
            try {
                log.info("烟草到了吗？:{}",hasCigarette);
                while (!hasCigarette) {
                    log.info("没有烟草，先休息！");
                    try {
                        hasCigaretteCd.await();
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

            } finally {
                room.unlock();
            }
        }, "小南").start();

        new Thread(()->{
            room.lock();
            try {
                log.info("外卖到了吗？:{}",hasTackOut);
                while (!hasTackOut) {
                    log.info("没有到，休息下！");
                    try {
                        hasTackOutCd.await();
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
            } finally {
                room.unlock();
            }
        },"小龙女").start();

        Thread.sleep(3000);

        new Thread(()->{
            room.lock();
            try {
                hasTackOut = true;
                hasTackOutCd.signal();
                log.info("外卖到了噢");
            } finally {
                room.unlock();
            }
        },"送外卖的").start();

        Thread.sleep(2000);

        new Thread(()->{
            room.lock();
            try {
                hasCigarette = true;
                hasCigaretteCd.signal();
                log.info("烟草到了噢");
            } finally {
                room.unlock();
            }
        },"送烟草的").start();
    }
}
