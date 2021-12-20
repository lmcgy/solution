package com.liu.data_structure.queue;

/**
 * 动态队列（数组实现）
 */
public class DynamicQueue {
   private String[] items;
   private int n;
   private int head = 0;
   private int tail = 0;

   public DynamicQueue(int capacity){
       this.items = new String[capacity];
       this.n  = capacity;
   }

   public boolean enQueue(String value){
       if (tail == n){
           if (head == 0) return false;
           for (int i = head; i < tail ; i++) {
               items[i-head] = items[i];
           }
           tail-=head;
           head  =0;
           System.out.println("start dynamic");
       }
       items[tail++] = value;
       return true;
   }

   public String deQueue(){
       if (head == tail) return null;
       return items[head++];
   }


    public static void main(String[] args) {
        DynamicQueue dynamicQueue = new DynamicQueue(10);
        dynamicQueue.enQueue("a");
        dynamicQueue.enQueue("b");
        dynamicQueue.enQueue("c");
        dynamicQueue.enQueue("d");
        dynamicQueue.enQueue("e");
        dynamicQueue.enQueue("f");
        dynamicQueue.enQueue("g");
        dynamicQueue.enQueue("h");
        dynamicQueue.enQueue("i");
        dynamicQueue.enQueue("j");

        System.out.println(dynamicQueue.deQueue());
        System.out.println(dynamicQueue.deQueue());
        System.out.println(dynamicQueue.deQueue());
        System.out.println(dynamicQueue.deQueue());


        dynamicQueue.enQueue("k");
        dynamicQueue.enQueue("l");
        dynamicQueue.enQueue("m");
        dynamicQueue.enQueue("n");


        System.out.println(dynamicQueue.deQueue());


        dynamicQueue.enQueue("a");
    }
}
