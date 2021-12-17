package com.liu.data_structure.algorithm;

/**
 * @Author: Gavin
 * @Date: 2021/12/16 14:32
 */
public class Test {

    private Integer id;
    private Integer age;
    private Integer length;
    private String name;

    public Test(Integer id, Integer age, Integer length, String name) {
        this.id = id;
        this.age = age;
        this.length = length;
        this.name = name;
    }

    public Test() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
