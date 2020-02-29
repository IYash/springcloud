package com.clnn;


import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 需要模拟并发
 */
public class ThrealLocalTest {

    ExecutorService exes = Executors.newFixedThreadPool(5);
    CountDownLatch countDownLatch = new CountDownLatch(5);
    CNumber tlc=new CNumber();
    CNumber tlic = new CNumber();
    private class CTask implements Runnable{
        int i;

        public CTask(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println("--------------------");
            //这是一个错误的使用方式，tlc需要每次都 new 出来
            tlc.setNum(i);
            tlic.setNum(i);
            TestThreadLocal.setTL(tlc);
            TestThreadLocal.setTLI(tlic);
            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("tl---->"+TestThreadLocal.getTL().getNum());
            System.out.println("tli---->"+TestThreadLocal.getTLI().getNum());
            countDownLatch.countDown();
        }
    }
    @Test
    public void test() throws InterruptedException {
        for(int i=0;i<5;i++){
            exes.execute(new CTask(i));
        }
        System.out.println("begin to count...");
        countDownLatch.await();
    }
}
