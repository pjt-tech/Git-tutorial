package com.kye.studend_school;

import java.util.ArrayList;

public class School {
    String name;
    ArrayList<Student> students = new ArrayList<Student>();

    public School(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public ArrayList<Student> getStudent() {
            return students;
    }

    public void setStudent(ArrayList<Student> student) {
        this.students = student;
    }

    public School(String name, String students, ArrayList<Student> student) {
        this.name = name;
        this.students = student;
    }

    public void addItem(Student student){
        students.add(student);
    }
    public int size(){
        return students.size();
    }

    public String toString() {
        String output = "";
        output = output + "학교이름 :" + name + "\n";
        for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
            output += "학생 # " + i + ":" + student.getName() + "," + student.getAge() + "\n";
        }
        return output;
    }

}

