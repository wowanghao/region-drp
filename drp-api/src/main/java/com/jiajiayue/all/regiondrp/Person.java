package com.jiajiayue.all.regiondrp;

import lombok.Data;

/**
 * @author WangHao
 * @date 2019/6/29 8:52
 */
@Data
public class Person {
    String name;
    int age;

    public synchronized void testSyn(){
        System.out.println("testSyn");
    }

    public void testPerson()  {
        System.out.println("testPerson");
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
