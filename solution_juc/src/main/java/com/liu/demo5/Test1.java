package com.liu.demo5;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author miao.liu
 * @since 2022/06/11 10:59
 */
@Slf4j
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        /*Thread thread = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("thread is interrupted");
                    break;
                }
            }
        }, "t1");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();*/

        ThreadMain  threadMain = new ThreadMain();
        threadMain.start();
        TimeUnit.SECONDS.sleep(4);
        threadMain.stop();


    }
}

@Slf4j
class ThreadMain{
    private Thread thread;

    public void start(){
        thread = new Thread(()->{
            while (true){
                Thread thread1 = Thread.currentThread();
                if (thread1.isInterrupted()){
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("执行任务");
                } catch (InterruptedException e) {
                    thread.interrupt();
                }
            }
        });
        thread.start();
    }

    public void stop(){
        thread.interrupt();
    }
}
