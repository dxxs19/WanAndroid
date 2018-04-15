package com.wei.wanandroid.javasources.designpatterns.builder;

/**
 * Builder模式：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 */
public class Client
{
    public static void main(String[] args) {
        Belle belle = new Belle.Builder()
                .setAge(22)
                .setHeight(167)
                .create();

        System.out.println(belle.getAge() + ", " + belle.getHeight());
    }
}
