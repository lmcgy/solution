package com.liu.base.message.service.demo1;

/**
 * description:
 *
 * @author miao.liu
 * @since 2022/06/30 14:37
 */
public class Test {

    public static void main(String[] args) {

        Child child = new Child();
        child.setName("liu");
        child.setAge(1);

        //show(child);

    }

    public static void show(Parent parent){
        Child child = (Child) parent;
        System.out.println(child.getName());

    }


}
