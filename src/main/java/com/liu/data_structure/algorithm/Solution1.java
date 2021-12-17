package com.liu.data_structure.algorithm;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 算法-贪心算法-课程表
 */
public class Solution1 {


    public static void main(String[] args) {
        List<Test> list  = new ArrayList<>();
        Test test1 = new Test(1,38,168,"name");
        Test test2 = new Test(1,30,170,"name");
        Test test3 = new Test(1,30,168,"name");
        Test test4 = new Test(1,30,168,"name");
        Test test5 = new Test(1,40,172,"name");
        Test test6 = new Test(6,45,175,"name");
        Test test7 = new Test(7,27,166,"name");
        Test test8 = new Test(8,36,168,"name");
        Test test9 = new Test(9,27,170,"name");
        Test test0 = new Test(0,30,170,"name");
        list.add(test0);
        list.add(test1);
        list.add(test2);
        list.add(test3);
        list.add(test4);
        list.add(test5);
        list.add(test6);
        list.add(test7);
        list.add(test8);
        list.add(test9);

        Comparator<Test> id = Comparator.comparing(Test::getId);
        Comparator<Test> age = Comparator.comparing(Test::getAge);
        Comparator<Test> length = Comparator.comparing(Test::getLength);

        list.sort(id.thenComparing(age).thenComparing(length));

        for (Test test : list) {
            System.out.println("test--id"+test.getId()+"--age:"+test.getAge()+"--length:"+test.getLength());
        }

    }


}
