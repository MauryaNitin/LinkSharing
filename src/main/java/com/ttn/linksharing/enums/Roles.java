package com.ttn.linksharing.enums;

public enum Roles {
    USER("User"), ADMIN("Admin");
    String value;

    Roles(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
