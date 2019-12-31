package com.kye.sel_check;

public class Singer {
    String name;
    int age;

    public Singer(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void setName(String name){
        this.name = name;

    }
    public void setAge(int age){
        this.age = age;
    }
    public String getName() {
        return name;

    }
    public int getAge() {
        return age;
    }
}
