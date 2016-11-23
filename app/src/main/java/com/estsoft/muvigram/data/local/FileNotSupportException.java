package com.estsoft.muvigram.data.local;

/**
 * Created by estsoft on 2016-11-18.
 */

public class FileNotSupportException extends Exception {
    public FileNotSupportException() {
        super("File format is not supported");
    }

    public FileNotSupportException(String message) {
        super(message);
    }

    public FileNotSupportException(String message, Throwable cause) {
        super(message, cause);
    }
}
