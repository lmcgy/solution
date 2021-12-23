package com.liu.demo3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 * @Author: Gavin
 * @Date: 2021/12/22 15:13
 */
@Slf4j
public class Test1 {

    public static void main(String[] args) {
        Object A =  new Object();
        Object B = new Object();

        Thread t1 = new Thread(() -> {
            synchronized (A) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    log.info("t1执行B");
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (B) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    log.info("t2执行A");
                }
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
