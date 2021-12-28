package com.liu.demo4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Gavin
 * @Date: 2021/12/23 10:34
 */
public class Test1 {

    public static void main(String[] args) {
        AtomicInteger cas = new AtomicInteger();

        for (int i = 0; i < 10; i++) {
            cas.compareAndSet(1,2);
        }
    }
}
