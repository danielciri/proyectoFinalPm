package com.danielcirilo.frasescelebres.Models;


public class Usuario {
    private String username;
    private String password;

    public Usuario() {
        this.username = null;
        this.password = null;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public boolean isNull(){
        if(username.length()==0 && password.length()==0){
            return false;
        }else{
            return true;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
