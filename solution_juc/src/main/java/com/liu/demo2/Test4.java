package com.liu.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 异步模式-生产者/ 消费者模式
 * @Author: Gavin
 * @Date: 2021/12/22 11:25
 */
@Slf4j
public class Test4 {

    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(2);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                Message message = new Message(finalI, "msg" + finalI);
                queue.put(message);
            },"生产者"+finalI).start();
        }

        new Thread(()->{
            while (true) {
                queue.take();
            }
        },"消费者").start();
    }



    static class MessageQueue{

        private int capacity;
        private  LinkedList<Message> queue  = new LinkedList<>();

        public MessageQueue(int capacity) {
            this.capacity = capacity;
        }

        public void put(Message msg){
            synchronized (queue) {
                while (queue.size() == capacity) {
                    try {
                        log.info("队列已经满了，等待消费");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("插入消息,id:{}",msg.getId());
                queue.addLast(msg);
                queue.notifyAll();
            }

        }

        public Message take(){
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        log.info("队列为空，等待插入");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message first = queue.removeFirst();
                log.info("消费消息- id:{},msg:{}",first.getId(),first.message);
                queue.notifyAll();
                return first;
            }
        }
    }

    static class Message{
        private int id;
        private String message;

        public Message(int id, String message) {
            this.id = id;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public int getId() {
            return id;
        }
    }
}
