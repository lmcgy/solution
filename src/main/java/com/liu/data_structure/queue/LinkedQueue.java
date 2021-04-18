package com.liu.data_structure.queue;


/**
 * 基于链表实现的数组
 */
public class LinkedQueue {

    private Node head = null; //头指针
    private Node tail = null; //尾指针


    public void enQueue(String value){
        Node node = new Node(value,null);
        if (tail == null){
            head = node;
            tail = node;
        }else{
            tail.next = node;
            tail = node;
        }
    }

    public String deQueue(){
        if (tail == null) return null;
        String res = head.getData();
        head = head.next;
        if (head == null)tail = null;
        return res;
    }


    private static class Node{
        private String data;
        private Node next;

        public Node(String data,Node next){
            this.data = data;
            this.next = next;
        }
        public Node(){

        }
        public String getData(){
            return this.data;
        }
    }
}
