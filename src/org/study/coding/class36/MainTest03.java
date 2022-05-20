package org.study.coding.class36;

public class MainTest03 {

    public static void main(String[] args) {
        String s1 = "abcdcdaaabbbcccbbcdaaabbbcccfdsfhsdkjfhsdfhjs";
        String s2 = "cdaaabbbccc";

        System.out.println(getIndexOf(s1, s2));


        System.out.println(count(s1, s2));
        System.out.println(Code03_MatchCount.sa(s1, s2));
    }


    /**
     * 改写kmp
     *
     * @since 2022-05-19 15:11:02
     */
    public static int count(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return 0;
        }
        int count = 0;
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int[] next = getNextArray(str2);
        int x = 0;
        int y = 0;

        while (x < str1.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
                if (y == str2.length) {
                    count++;
                    y = next[y];
                }
            } else if (next[y] != -1) {
                y = next[y];
            } else {
                x++;
            }
        }

        return count;
    }

    /**
     * next数组多求一位
     * 比如：str2 = aaaa
     * 那么，next = -1,0,1,2,3
     * 最后一个3表示，终止位置之前的字符串最长前缀和最长后缀的匹配长度
     * 也就是next数组补一位
     *
     * @since 2022-05-19 15:22:19
     */
    public static int[] getNextArray(char[] str2) {
        if (str2.length == 1) {
            return new int[] { -1, 0 };
        }
        int[] next = new int[str2.length + 1];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
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
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int[] next = getNext(str2);

        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] != -1) {
                y = next[y];
            } else {
                x++;
            }
        }

        return y == str2.length ? x - y : -1;
    }

    public static int[] getNext(char[] str2) {
        if (str2.length == 0) {
            return new int[0];
        }
        if (str2.length == 1) {
            return new int[]{-1};
        }
        if (str2.length == 2) {
            return new int[]{-1, 0};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int index = 2;
        int cn = 0;
        while (index < str2.length) {
            if (str2[index - 1] == str2[cn]) {
                next[index++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[index++] = 0;
            }
        }
        return next;
    }
}
