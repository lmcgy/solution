package com.liu.demo5;


import java.util.ArrayList;

public class TestThreadSafe {

    static final int THREAD_NUM = 2;
    static final int LOOP_NUM = 200;

    public static void main(String[] args) {
        ThreadSafeSubClass threadSafeSubClass= new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(()->{
                threadSafeSubClass.method1(LOOP_NUM);
            },"thread"+i).start();
        }
    }

}

class ThreadSafe{
    public final void method1(int loopNumber){

        ArrayList<Object> list = new ArrayList<>();

        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    private void method2(ArrayList<Object> list) {
        list.add("1");
    }

    public void method3(ArrayList<Object> list) {
        list.remove(0);
    }

}

class ThreadSafeSubClass extends ThreadSafe {
    @Override
    public void method3(ArrayList<Object> list) {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}


