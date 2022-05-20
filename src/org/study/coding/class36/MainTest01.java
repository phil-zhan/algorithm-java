package org.study.coding.class36;

public class MainTest01 {
    private static int[] lens = null;

    public static char kth(int n, int k) {
        if (lens == null) {
            fillLens();
        }

        if (n == 1) {
            return 'a';
        }
        int half = lens[n - 1];
        if (k <= half) {
            return kth(n - 1, k);
        } else if (k == half + 1) {
            return (char) ('a' + n - 1);
        } else {

            return invert(kth(n - 1, ((half + 1) << 1) - k));
        }


    }

    private static char invert(char c) {
        return (char) ('a' << 1 + 24 - c);
    }


    private static void fillLens() {
        lens = new int[26];
        lens[1] = 1;
        for (int i = 2; i <= 25; i++) {
            lens[i] = (lens[i - 1] << 1) + 1;
        }
    }


    public static String generateString(int n) {
        String s = "a";
        for (int i = 2; i <= n; i++) {
            s = s + (char) (i + 'a' - 1) + reverseInvert(s);
        }

        return s;
    }

    public static String reverseInvert(String s) {
        char[] invert = invert(s);
        int left = 0;
        int right = invert.length - 1;
        while (left < right) {
            char temp = invert[left];
            invert[left++] = invert[right];
            invert[right--] = temp;
        }

        return String.valueOf(invert);

    }

    private static char[] invert(String s) {
        char[] res = s.toCharArray();
        for (int i = 0; i < res.length; i++) {
            res[i] = invert(res[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        int n = 20;
        String str = generateString(n);

        System.out.println("测试开始");
        for (int i = 1; i <= str.length() ; i++) {
            if (str.charAt(i-1) != kth(n,i)){
                System.out.println("出错啦");
            }
        }

        System.out.println("测试结束");
    }
}
