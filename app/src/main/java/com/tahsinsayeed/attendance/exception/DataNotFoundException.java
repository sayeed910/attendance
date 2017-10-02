package com.tahsinsayeed.attendance.exception;

/**
 * Created by sayeed on 7/8/17.
 */
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String s) {
        super(s);
    }
    public DataNotFoundException(){
        super();
    }
}
