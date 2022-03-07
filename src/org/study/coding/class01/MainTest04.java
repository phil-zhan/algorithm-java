package org.study.coding.class01;

public class MainTest04 {

    public int minSteps1(String s) {

        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int step1 = 0;
        int gi = 0;

        for (int index = 0; index < str.length; index++) {
            if (str[index] == 'G') {
                step1 += index - gi++;
            }
        }

        int step2 = 0;
        int bi = 0;

        for (int index = 0; index < str.length; index++) {
            if (str[index] == 'B') {
                step2 += index - bi++;
            }
        }

        return Math.min(step1, step2);

    }

    public static void main(String[] args) {
        int maxLen = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String str = Code04_MinSwapStep.randomString(maxLen);
            int ans1 = new MainTest04().minSteps1(str);
            int ans2 = Code04_MinSwapStep.minSteps2(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

}
