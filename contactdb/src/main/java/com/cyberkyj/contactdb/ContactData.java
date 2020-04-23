package com.cyberkyj.contactdb;

public class ContactData {

    String name;
    String phone;
    String email;

    public ContactData(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String[] getDataArray(){
        String[] data = { this.name, this.phone, this.email};
        return data;
    }
}
