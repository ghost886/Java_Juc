package com.atguigu.juc12105;

/*
题目：两个线程，可以操作初始值为0的一个变量，
实现一个线程对该变量加1，一个线程对该变量减1
实现交替，10轮，变量初始值为0
 */
/*
1、在高内聚低耦合的前提下，线程操作资源类
2、判断/干活/通知
3、多线程交互中，必须要防止多线程的虚假唤醒，即多线程的判断中不许用if,只能用while
 */
class AirCondition{
    private int number=0;
    public synchronized void increment() throws InterruptedException{
        //判断
        if(number!=0){
            this.wait();
        }
        //干活
        number++;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        //通知
        this.notifyAll();//这个也比较重要
    }
    public synchronized void decrement() throws InterruptedException{
        if(number==0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }
}

public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        AirCondition airCondition=new AirCondition();

        new Thread(()->{
            for (int i=0;i<10;i++) {
                try {
                    Thread.sleep(100);
                    airCondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i=0;i<10;i++) {
                try {
                    Thread.sleep(200);
                    airCondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
        new Thread(()->{
            for (int i=0;i<10;i++) {
                try {
                    Thread.sleep(300);
                    airCondition.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i=0;i<10;i++) {
                try {
                    Thread.sleep(400);
                    airCondition.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
