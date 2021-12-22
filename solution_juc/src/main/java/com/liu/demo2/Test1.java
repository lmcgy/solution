package com.liu.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 保护性暂停模式-join
 * 同步-一一对应
 * @Author: Gavin
 * @Date: 2021/12/22 9:23
 */
@Slf4j
public class Test1 {

    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            List<String> list  = new ArrayList<>();
            list.add("1");
            list.add("2");
            log.info("download complete");
            guardedObject.complete(list);
        },"t1").start();

        log.info("waiting");

        Object o = guardedObject.get();
        log.info("response : {}",((List<String>) o).size());
    }


    static class GuardedObject {
        private Object response;
        private final Object lock = new Object();

        public Object get() {
            synchronized (lock) {
                while (response == null) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return response;
            }
        }

        public void complete(Object response){
            synchronized (lock) {
                this.response = response;
                lock.notifyAll();
            }
        }
    }
}
