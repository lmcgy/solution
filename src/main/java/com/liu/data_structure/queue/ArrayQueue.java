package com.liu.data_structure.queue;

/**
 * 数组实现的顺序队列
 *
 */
public class ArrayQueue {

    private String[] items;
    private int n;
    private int head = 0;
    private int tail = 0;

    public ArrayQueue(int capacity){
        this.items = new String[capacity];
        this.n = capacity;
    }

    public boolean enQueue(String value){
        if (tail ==n)return false;
        items[tail++] = value;
        return true;
    }

    public String deQueue(){
        if (head == tail) return null;
        return items[head++];
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(10);
        arrayQueue.enQueue("1");
        arrayQueue.enQueue("2");
        arrayQueue.enQueue("3");
        arrayQueue.enQueue("4");

        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());
        System.out.println(arrayQueue.deQueue());


    }



}
