package com.pgrela.wlunch.common;

public class MenuException extends RuntimeException {
    public MenuException(String s) {
        super(s);
    }
    public MenuException(String s, Exception e) {
        super(s, e);
    }
}
