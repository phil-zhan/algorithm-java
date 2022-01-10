package org.study.class06;

/**
 * @author phil
 * @date 2022/1/7 10:01
 */
public class HeapFullException extends RuntimeException {
    protected int code = 500;

    public HeapFullException(int code, String message) {
        super(message);
        this.code = code;
    }
}
