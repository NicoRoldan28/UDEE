package com.api.UDEE.domain;

public enum TypeUser {

    EMPLOYEE("EMPLOYEE"),
    CLIENT("CLIENT");

    private String name;

    TypeUser(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }
}
