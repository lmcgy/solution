package com.liu.demo4;

import java.io.Serializable;

/**
 * @Author: Gavin
 * @Date: 2021/12/27 14:36
 */
public final class Singleton implements Serializable {

    // 构造方法为私有，也不能保证反射 来重新创建对象
    private Singleton(){}

    // 这样创建对象，能否保住单例创建对象的线程安全问题 (类加载阶段的 静态成员变量的赋值是线程安全的)
    private static final Singleton INSTANCE = new Singleton();

    public static Singleton getInstance(){
        return INSTANCE;
    }

    // 防止反序列化 破坏
    public Object readResolve(){
        return INSTANCE;
    }


}
