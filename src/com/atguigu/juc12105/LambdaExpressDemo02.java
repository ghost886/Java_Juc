package com.atguigu.juc12105;

@FunctionalInterface  //自动加上这个注解，隐式的注解
interface Foo{
//    public void sayHello();
    public int add(int x,int y);

    public default int mul1(int x,int y){
        return x*y;
    }

    public static double div(int x,int y){
        return x/y;
    }
}
/*
*
* 函数式编程->才能用lambda表达式
*
* 接口里面有且仅有一个接口
*
* 1、lambda小口诀
* 拷贝中括号，写死右箭头，落地大括号
* 2、@FunctionalInterface
* 3、default 可以定义多个
* 4、static  多个
*
* lambda表达式解决了匿名内部类代码冗余的问题
*
* Java ： 面向接口编程
* Java 学习流式编程
*/

public class LambdaExpressDemo02 {
    public static void main(String[] args) {//尽量不要在main里面写业务逻辑方法
        //这里面有且仅有一个方法
//        Foo foo=new Foo() {  //这样写完全没问题
//            @Override
//            public void sayHello() {
//                System.out.println("hello world");
//            }
//        };
//        foo.sayHello();
        Foo foo=(int x,int y)->{
            System.out.println("hello world");
            return x+y;
        };
        System.out.println(foo.add(3,6));
        System.out.println(foo.mul1(3,5));
        System.out.println(Foo.div(18, 6));
    }
}
