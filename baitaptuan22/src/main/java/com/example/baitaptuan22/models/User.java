package com.example.baitaptuan22.models;

public class User {
    private int idsv;
    private String name;
    private String email;

    private String className;

    public User() {
    }

    //
    public User(int idsv, String name, String email, String className) {
        this.idsv = idsv;
        this.name = name;
        this.email = email;
        this.className = className;
    }

    public int getIdsv() {
        return idsv;
    }

    public void setIdsv(int idsv) {
        this.idsv = idsv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "User [idsv=" + idsv + ", name=" + name + ", email=" + email + ", className=" + className + "]";
    }
}
