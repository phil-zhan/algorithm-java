package org.study.coding.class01;

public class MainTest03 {

    public int tableSizeFor(int num) {
        num--;
        num |= num >>> 1;
        num |= num >>> 2;
        num |= num >>> 4;
        num |= num >>> 8;
        num |= num >>> 16;


        return num < 0 ? 1 : num + 1;
    }

    public static void main(String[] args) {
       System.out.println(new MainTest03().tableSizeFor(123456));
    }
}
