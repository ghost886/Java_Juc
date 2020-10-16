package com.atguigu.juc12105;

/*
ArrayList底层是object(ArrayList什么都能装，当然底层是object)，底层相当于new了一个object数组
初始值为10，扩容原值的一半
扩用ArrayList.copyof
HashMap初始值是16,扩容是原值的一倍

扩容原理：
1 把原来的数组复制到另一个内存空间更大的数组中
2 把新元素添加到扩容以后的数组中
这个地方注意这一句
     int newCapacity = oldCapacity + (oldCapacity >> 1);
    oldCapacity >> 1    右移运算符   原来长度的一半 再加上原长度也就是每次扩容是原来的1.5倍

ArrayList,set,Map线程不安全
Vector线程安全，代价是加锁
并发性和数据一致性本质上是相左的
 */
/*
1 故障现象
java.util.ConcurrentModificationException
并发修改异常
2 解决办法
new Vector<>();


Collections.synchronizedList(new ArrayList<>());


new CopyOnWriteArrayList<>();//写时复制类

    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
3优化建议
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotSafeDemo3 {
    public static void main(String[] args) {
        Map<String,String> map= new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
//        SetNotSafe();
//        listNotSafe();

    }

    public static void SetNotSafe() {
        Set<String> set =new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    public static void listNotSafe() {
        List<String> list =new CopyOnWriteArrayList<>();//写时复制类
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }

}
//        List<String> list=new ArrayList<>();
//        List<String> list=new Vector<>();
//        List<String> list= Collections.synchronizedList(new ArrayList<>());//没有new
        /*
            foreach源码
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
         */

//        list.add("a");
//        list.add("b");
//        list.add("c");
//list.forEach((t) -> System.out.println(t));   同等效力
//        list.forEach(System.out::println);
