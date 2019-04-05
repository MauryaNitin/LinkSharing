package com.ttn.linksharing.exceptions;

public class StorageFileNotFoundException extends RuntimeException {
    StorageFileNotFoundException(String message){
        super(message);
    }
}
