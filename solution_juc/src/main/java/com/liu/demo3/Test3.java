package com.liu.demo3;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 哲学家就餐问题-ReentrantLock
 * @Author: Gavin
 * @Date: 2021/12/22 16:27
 */
@Slf4j
public class Test3 {
    public static void main(String[] args) {
        Chopstick c1= new Chopstick("张三");
        Chopstick c2= new Chopstick("李四");
        Chopstick c3= new Chopstick("王五");
        Chopstick c4= new Chopstick("李白");
        Chopstick c5= new Chopstick("杜甫");

        new Philosopher("张三",c1,c2).start();
        new Philosopher("李四",c2,c3).start();
        new Philosopher("王五",c3,c4).start();
        new Philosopher("李白",c4,c5).start();
        new Philosopher("杜甫",c5,c1).start();
    }

    static class  Chopstick extends ReentrantLock {
        private String name;

        public Chopstick(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Chopstick{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class Philosopher extends  Thread {
        private Chopstick left;
        private Chopstick right;

        public Philosopher (String name, Chopstick left, Chopstick right){
            super(name);
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            // 一直去做这个事情
            while (true) {
                if (left.tryLock()) {
                    try {
                        if (right.tryLock()){
                            try {
                                eat();
                            } finally {
                                right.unlock();
                            }
                        }
                    } finally {
                        left.unlock();
                    }
                }
            }
        }

        private void eat(){
            log.info("eating....");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
