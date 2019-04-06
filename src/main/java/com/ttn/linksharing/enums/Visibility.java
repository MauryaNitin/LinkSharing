package com.ttn.linksharing.enums;

public enum Visibility {
    PUBLIC("Public"), PRIVATE("Private");

    String value;

    Visibility(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

}
