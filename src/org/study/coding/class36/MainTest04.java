package org.study.coding.class36;

public class MainTest04 {

    public static void main(String[] args) {
        String str1 = "(()())()(())";
        System.out.println(sores(str1));

        // (()()) + (((()))) + ((())())
        // (()()) -> 2 * 2 + 1 -> 5
        // (((()))) -> 5
        // ((())()) -> ((2 + 1) * 2) + 1 -> 7
        // 所以下面的结果应该是175
        String str2 = "(()())(((())))((())())";
        System.out.println(sores(str2));

        // (()()()) + (()(()))
        // (()()()) -> 2 * 2 * 2 + 1 -> 9
        // (()(())) -> 2 * 3 + 1 -> 7
        // 所以下面的结果应该是63
        String str3 = "(()()())(()(()))";
        System.out.println(sores(str3));
    }

    public static int sores(String s1) {
        return compute(0, s1.toCharArray())[0];
    }

    public static int[] compute(int index, char[] str) {

        // 当前就是右括号
        if (str[index] == ')') {
            return new int[]{1, index};
        }

        // 当前不是右括号。去考虑计算 i+1 及其之后的值
        int ans = 1;
        while (index < str.length && str[index] != ')') {
            int[] info = compute(index + 1, str);
            ans *= info[0] + 1;
            index = info[1] + 1;
        }
        return new int[]{ans, index};
    }
}
