package com.liu.data_structure.queue;

/**
 * 数组实现的循环队列
 */
public class CircularQueue {
    private String[] items;
    private int n;
    private int head = 0;
    private int tail = 0;

    public CircularQueue(int capacity){
        this.items = new String[capacity];
        this.n = capacity;
    }

    public boolean enQueue(String value){
        if ((tail+1)%n == head) return false;
        items[tail] = value;
        tail = (tail+1)%n;
        return true;
    }

    public String deQueue(){
        if (head == tail) return null;
        String data = items[head];
        head = (head+1)%n;
        return data;
    }
}
