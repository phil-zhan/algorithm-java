package org.study.class06;

/**
 * @author phil
 * @date 2022/1/7 10:01
 */
public class HeapNullException extends RuntimeException {
    protected int code = 500;

    public HeapNullException(int code, String message) {
        super(message);
        this.code = code;
    }
}
