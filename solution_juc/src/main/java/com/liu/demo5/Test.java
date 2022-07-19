package com.liu.demo5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author miao.liu
 * @since 2022/06/10 21:11
 */
@Slf4j
public class Test {


    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Runnable runnable = () -> log.debug("runnable");

        Thread thread = new Thread(runnable);
        thread.start();


        Thread thread1 = new Thread(()->{
            log.debug("thread...");
        });
        thread1.start();

        Callable callable = () -> {
            TimeUnit.SECONDS.sleep(1);
            return 100;
        };
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        Thread thread2 = new Thread(futureTask,"task");
        thread2.start();
        Integer integer = futureTask.get();
        log.debug("Task result:{}",integer);


    }
}
