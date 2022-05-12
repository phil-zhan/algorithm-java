package org.study.primary.class01;

public class MainTest01 {
    public static void printBinary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
    }


    public static void main(String[] args) {
        printBinary(4);
    }
}
