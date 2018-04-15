package com.wei.wanandroid.javasources.designpatterns.singleton;

/**
 * 单例模式：确保某一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。
 */
public class Client
{
    public static void main(String[] args) {
        HungrySingleton hungrySingleton = HungrySingleton.getInstance();
        HungrySingleton hungrySingleton1 = HungrySingleton.getInstance();
        System.out.println("hungrySingleton == hungrySingleton ? " + ( hungrySingleton == hungrySingleton1 ) );

        LazySingleton lazySingleton = LazySingleton.getInstance();
        LazySingleton lazySingleton1 = LazySingleton.getInstance();
        System.out.println("lazySingleton == lazySingleton1 ? " + ( lazySingleton == lazySingleton1 ) );

        DCLSingleton dclSingleton = DCLSingleton.getInstance();
        DCLSingleton dclSingleton1 = DCLSingleton.getInstance();
        System.out.println("dclSingleton == dclSingleton1 ? " + ( dclSingleton == dclSingleton1 ) );
        System.out.println(dclSingleton + ", " + dclSingleton1);

        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
        EnumSingleton enumSingleton1 = EnumSingleton.INSTANCE;
        System.out.println("enumSingleton == enumSingleton1 ? " + ( enumSingleton == enumSingleton1 ) );
        System.out.println(enumSingleton + ", " + enumSingleton1);

    }
}
