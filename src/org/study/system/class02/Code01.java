package org.study.system.class02;

/**
 * @author phil
 * @date 2021/12/7 16:59
 */
public class Code01 {

    public static void main(String[] args) {
        int a = 3;
        int b = 4;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);
    }
}
