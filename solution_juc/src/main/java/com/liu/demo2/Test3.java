package com.liu.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 保护性暂停模式-多任务版本
 * 同步-一一对应
 * @Author: Gavin
 * @Date: 2021/12/22 10:33
 */
@Slf4j
public class Test3 {

    static class GuardedObject {
        private int id;
        private Object response;

        public GuardedObject(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public Object get(long timeOut) {
            // 锁当前实例
            synchronized (this) {
                if (timeOut == 0) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    long now = System.currentTimeMillis();
                    long delay = 0;
                    while (response == null) {
                        try {
                            long wait = timeOut - delay;
                            log.info("等待时长:{}", wait);
                            if (wait <= 0) {
                                log.info("等待超时了");
                                break;
                            }
                            this.wait(wait);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        delay = System.currentTimeMillis() - now;
                    }
                }
                return response;
            }
        }

        public void complete(Object response) {
            synchronized (this) {
                this.response = response;
                this.notifyAll();
            }
        }
    }

    static class MailBox {
        // static 所有的类共享这个变量
        private static Map<Integer, GuardedObject> map = new Hashtable<>();
        private static int id = 1;

        private static synchronized int getCentre() {
            return id++;
        }

        public static GuardedObject createGuarded() {
            GuardedObject guardedObject = new GuardedObject(getCentre());
            map.put(guardedObject.getId(), guardedObject);
            return guardedObject;
        }

        public static GuardedObject getGuarded(int id) {
            return map.remove(id);
        }

        public static Set<Integer> getKeys() {
            return map.keySet();
        }
    }

    static class People extends Thread {

        @Override
        public void run() {
            GuardedObject guarded = MailBox.createGuarded();
            log.info("开始收信 id:{}", guarded.getId());
            Object o = guarded.get(5000);
            log.info("收到来信，message:{}", o);
        }
    }

    static class PostMan extends Thread {
        private int id;
        private String message;

        public PostMan(int id, String message) {
            this.id = id;
            this.message = message;
        }

        @Override
        public void run() {
            GuardedObject guarded = MailBox.getGuarded(id);
            log.info("邮差开始送信，id:{},message:{}", id, message);
            guarded.complete(message);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Set<Integer> keys = MailBox.getKeys();
        for (Integer key : keys) {
            new PostMan(key,"msg"+key).start();
        }

    }
}
