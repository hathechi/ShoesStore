package com.example.shoesstore.Moder;

public class User {
    private int id = 0;
    private String username;
    private String password;
    private String email;
    private String ngaytaotk;

    public User(int id, String username, String password, String email, String ngaytaotk) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ngaytaotk = ngaytaotk;
    }

    public User() {
    }

    public String getNgaytaotk() {
        return ngaytaotk;
    }

    public void setNgaytaotk(String ngaytaotk) {
        this.ngaytaotk = ngaytaotk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
