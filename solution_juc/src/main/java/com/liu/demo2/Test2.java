package com.liu.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 超时版本的保护性暂停模式
 * @Author: Gavin
 * @Date: 2021/12/22 9:55
 */
@Slf4j
public class Test2 {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            List<String> list  = new ArrayList<>();
            list.add("1");
            list.add("12");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("complete over");
            guardedObject.complete(list);
        },"t1").start();

        log.info("waiting");

        Object o = guardedObject.get(2000);
        if (o == null) {
            log.info("get result is null");
        } else {
            log.info("get result length : {}",((List<String>)o).size());
        }

    }


    static class GuardedObject {

        private Object response;
        private final Object lock = new Object();

        public Object get(long timeOut){
            synchronized (lock){
                long now = System.currentTimeMillis();
                long delay = 0;
                while (response == null) {
                    try {
                        long wait = timeOut - delay;
                        log.info("等待时长:{}",wait);
                        if (wait <= 0) {
                            log.info("等待超时了");
                            break;
                        }
                        lock.wait(wait);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    delay = System.currentTimeMillis()-now;
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
