package com.jiajiayue.all.regiondrp;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author WangHao
 * @date 2019/7/1 11:14
 */
public class CallableTask {


    public static void main(String[] args) throws Exception {
        Person person = new Person();
        // 创建异步任务
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (person){
                    person.age++;
                    try {
                        Thread.sleep(3*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread1 = " + person.age);
                }

            }
        });
        thread1.start();

        //

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (person){
                    person.age++;
                    person.notify();
                    System.out.println("thread2 = " + person.age);
                }
            }
        });
        thread2.start();
    }

}