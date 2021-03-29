package org.kumsal.ficomSoft;

public class settingModel {
    private String username;
    private String name;
    private String surname;
    private String password;
    private boolean isAuth;
    private int id;
    public settingModel(String username, String name, String surname, String password,boolean isAuth,int id) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isAuth=isAuth;
        this.id=id;
    }
    public settingModel(String username, String name, String surname, String password,int id) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
