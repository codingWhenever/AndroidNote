package com.sz.leo.androidnote.chapter09.retrofit.http;

/**
 * @author：leo
 * @date：2019/6/18
 * @email：lei.lu@e-at.com
 */
public class Fault extends RuntimeException {
    private int errorCode;

    public Fault(int code, String message) {
        super(message);
        errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
