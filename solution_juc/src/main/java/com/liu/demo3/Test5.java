package com.liu.demo3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Gavin
 * @Date: 2021/12/22 17:03
 */
@Slf4j
public class Test5 {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.info("1");
        }, "t1");


        new Thread(()->{
            log.info("2");
            LockSupport.unpark(t1);
        },"t2").start();

        t1.start();
    }
}
