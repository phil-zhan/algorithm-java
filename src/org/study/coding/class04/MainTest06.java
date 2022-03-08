package org.study.coding.class04;

import java.util.Arrays;

public class MainTest06 {

    /**
     * [a,b,c] 满足 a + c != 2b
     * [2a,2b,2c]  2a + 2c != 2(2b)
     * [2a+1,2b+1,2c+1]  2a+1 + 2c+1 != 2(2b+1)
     * [2a,2b,2c,2a+1,2b+1,2c+1] 也满足条件
     * @since 2022-03-08 09:14:00
     */
    public static int[] makeNo(int size){
        if (size == 1){
            return new int[]{1};
        }

        int halfSize = (size + 1) >> 1;

        int[] base = makeNo(halfSize);

        int[] ans = new int[size];

        int ai = 0;
        for (; ai < halfSize; ai++) {
            ans[ai] = base[ai] * 2;
        }

        for (int i = 0; ai < size; ai++, i++) {
            ans[ai] = base[i] * 2 + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!Code06_MakeNo.isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(Code06_MakeNo.isValid(makeNo(1042)));
        System.out.println(Code06_MakeNo.isValid(makeNo(2981)));
    }
}
