package com.ttn.linksharing.enums;

public enum Seriousness {
    CASUAL("Casual"), SERIOUS("Serious"), VERY_SERIOUS("Very Serious");
    String value;

    Seriousness(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
