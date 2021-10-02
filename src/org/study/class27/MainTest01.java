package org.study.class27;

import java.util.Arrays;

/**
 * @author phil
 * @date 2021/9/27 15:56
 */
public class MainTest01 {

    public static int[] getNext(char[] str2) {
        int[] next = new int[str2.length];

        next[0] = -1;
        next[1] = 0;

        int i = 2;
        // cn 是当前 i 位置的前一个位置的信息【也就是前缀串和后缀串相等的最大长度】
        int cn = 0;

        while (i < next.length) {
            if (str2[i - 1] == str2[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }

        return next;
    }


    public static int getIndexOf(String s1, String s2) {
        if (null == s1 || null == s2 || s1.length() < 1 || s2.length() < 1) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] next = getNext(str2);

        // 主串指针
        int x = 0;

        // 子串指针
        int y = 0;

        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;

    }

    public static int[] getNext2(char[] str2) {
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int preIndexNext = 0;
        while (i < next.length) {
            if (str2[i - 1] == str2[preIndexNext]) {
                next[i++] = ++preIndexNext;
            } else if (preIndexNext > 0) {
                preIndexNext = next[preIndexNext];
            } else {
                next[i++] = 0;
            }
        }
        return next;

    }


    public static int getIndexOf2(String s1, String s2) {
        if (null == s1 || null == s2 || s1.length() < 1 || s2.length() < 1) {
            return -1;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();


        int x = 0;
        int y = 0;
        int[] next = getNext2(str2);

        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }

        return y == str2.length ? x - y : -1;
    }


    public static int[] getNext3(char[] str2) {
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int preIndexNext = 0;
        while (i < str2.length) {
            if (str2[i - 1] == str2[preIndexNext]) {
                next[i++] = ++preIndexNext;
            } else if (preIndexNext > 0) {
                preIndexNext = next[preIndexNext];
            } else {
                next[i++] = 0;
            }
        }


        return next;
    }


    public static int getIndexOf3(String s1, String s2) {
        if (null == s1 || null == s2 || s1.length() < 1 || s2.length() < 1) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int[] next = getNext3(str2);


        int x = 0;
        int y = 0;

        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }

        return y == str2.length ? x - y : -1;
    }

    public static void main(String[] args) {
        String s1 = "abcdcdaaabbbcccbbcdaaabbbcccfdsfhsdkjfhsdfhjs";
        String s2 = "cdaaabbbccc";

        System.out.println(Arrays.toString(getNext(s2.toCharArray())));
        System.out.println(Arrays.toString(getNext2(s2.toCharArray())));
        System.out.println(Arrays.toString(getNext3(s2.toCharArray())));

        System.out.println(getIndexOf(s1, s2));
        System.out.println(getIndexOf2(s1, s2));
        System.out.println(getIndexOf3(s1, s2));
    }

}
