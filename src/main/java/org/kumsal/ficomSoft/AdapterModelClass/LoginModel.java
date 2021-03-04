package org.kumsal.ficomSoft.AdapterModelClass;

public class LoginModel {
    private String loginby;
    private String name;
    private String surname;
    private String password;
    private String username;

    public LoginModel(){};

    public LoginModel(String loginby, String name, String surname, String username, String password) {
        this.loginby = loginby;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.username = username;
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

    public String getLoginby() {
        return loginby;
    }

    public void setLoginby(String loginby) {
        this.loginby = loginby;
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
}
