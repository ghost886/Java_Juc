package com.atguigu.juc12105;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//多态，父类的引用指向子类的实例

//匿名内部类:接口可以new，这种语法现象就叫匿名内部类
//接口当然可以new啦,否则哪来的匿名内部类，接口也是一种特殊的类

//Java中实现多线程有两种途径：继承Thread类，实现Runnable接口
//Thread类实现了Runnable接口
class Ticket{
    private int number=30;
    //左边是接口，右边是实现类
    //List list =new ArrayList();
    Lock l=new ReentrantLock();//可重入锁
    public void sale(){
        l.lock();//在try里面lock
        try{
            if(number>0){
                //Thread.currentThread().getName()获得当前线程名称
                System.out.println(Thread.currentThread().getName()+"\t卖出第:"+(number--)+"张票\t还剩下"+number+"张票");
            }
        }
        catch (Exception e){
            e.printStackTrace();//在控制台输出，查看完整错误信息
        }
        finally {//try可以和finally组合
            l.unlock();//一定要有这一步
        }
    }

}

public class SaleTicketDemo1 {
    //线程操纵资源类
    public static void main(String[] args) {//主线程，一切程序的入口,仅仅是处触发入口
//        Thread t1=new Thread();//线程去操纵资源类
//        Thread t2=new Thread();
//        Thread t3=new Thread();
//        t1.start();
//        如无必要，这种方法不要用了，不推荐

        Ticket ticket=new Ticket();
        /**粗糙版的匿名内部类版本
        //下面这种写法可以少写一种类
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=30; i++) {
                    ticket.sale();
                }
            }
        }, "A").start();//start了以后，不是马上启动线程，线程的启动是底层操作系统和CPU调度
        //线程粗分5种状态，细分6种
        //Thread.State;   new runnable blocked(阻塞) waiting(死等，不见不散) time_waiting（过时不候） terminated
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=31 ; i++) {
                    ticket.sale();
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=31 ; i++) {
                    ticket.sale();
                }
            }
        }, "C").start();
        **/
        
        //lambda表达式
        new Thread(()->{for (int i = 1; i <=30; i++) ticket.sale();},"A").start();
        new Thread(()->{for (int i = 1; i <=30; i++) ticket.sale();},"B").start();
        new Thread(()->{for (int i = 1; i <=30; i++) ticket.sale();},"C").start();
    }
}
