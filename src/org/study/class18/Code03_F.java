package org.study.class18;

/**
 * 斐波那数列
 * 当前项等于前面两项之和
 *
 * @author phil
 * @date 2021/7/19 16:10
 */
public class Code03_F {

    private static int getFbn(int n){
        if (n == 1 || n == 2) {
            return 1;
        }

        return getFbn(n-1)+getFbn(n-2);
    }

    public static void main(String[] args) {
        System.out.println(getFbn(10));
    }
}
