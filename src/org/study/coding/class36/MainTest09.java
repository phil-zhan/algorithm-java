package org.study.coding.class36;

public class MainTest09 {

    public String maxString(String s, int k) {
        if (s == null || s.length() < k) {
            return "";
        }
        char[] str = s.toCharArray();
        int size = str.length;
        char[] stack = new char[size];
        int si = 0;
        for (int i = 0; i < size; i++) {
            while (si > 0 && stack[si - 1] < str[i] && si + size - i > k) {
                si--;
            }
            if (si + size - i == k) {
                return String.valueOf(stack, 0, si) + s.substring(i);
            }
            stack[si++] = str[i];
        }
        return String.valueOf(stack,0,k);
    }

    public static void main(String[] args) {
        int n = 12;
        int r = 5;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * (n + 1));
            String str = Code09_MaxKLenSequence.randomString(len, r);
            int k = (int) (Math.random() * (str.length() + 1));
            String ans1 = new MainTest09().maxString(str, k);
            String ans2 = Code09_MaxKLenSequence.test(str, k);
            if (!ans1.equals(ans2)) {
                System.out.println("出错了！");
                System.out.println(str);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
