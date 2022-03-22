package org.study.coding.class05;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022-03-15 10:40:48
 */
public class MainTest03 {

    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minCost1(str1, str2, 5, 3, 2));
        System.out.println(minCost2(str1, str2, 5, 3, 2));

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 3, 2, 4));
        System.out.println(minCost2(str1, str2, 3, 2, 4));

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 1, 7, 5));
        System.out.println(minCost2(str1, str2, 1, 7, 5));

        str1 = "abcdf";
        str2 = "";
        System.out.println(minCost1(str1, str2, 2, 9, 8));
        System.out.println(minCost2(str1, str2, 2, 9, 8));


        int testTimes = 10;
        int maxLen = 2;
        for (int i = 0; i < testTimes; i++) {
            String s1 = randomStr(maxLen);
            String s2 = randomStr(maxLen);
            int cost1 = minCost1(s1, s2, 2, 9, 8);
            int cost2 = minCost2(s1, s2, 2, 9, 8);

            if (cost1 != cost2){
                System.out.println("Facker");
                System.out.println("s1:"+s1);
                System.out.println("s2:"+s2);
                System.out.println("cost1:"+cost1);
                System.out.println("cost2:"+cost2);
            }
        }
        System.out.println("Finsh");

    }

    public static String randomStr(int maxLen){
        int len = (int) (Math.random()*(maxLen+1));
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int)(Math.random()*255));
        }

        return Arrays.toString(str);
    }

    public static int minCost1(String s1, String s2, int insertCost, int deleteCost, int replaceCost) {
        // 参数过滤
        if (s1 == null || s2 == null) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        return process(str1, str1.length, str2, str2.length, insertCost, deleteCost, replaceCost);
    }


    public static int process(char[] str1, int index1, char[] str2, int index2, int insertCost, int deleteCost, int replaceCost) {
        if (index1 == 0 && index2 == 0) {
            return 0;
        }
        if (index1 == 0) {
            return index2 * insertCost;
        }
        if (index2 == 0) {
            return index1 * deleteCost;
        }
        // insert
        int p1 = process(str1, index1, str2, index2 - 1, insertCost, deleteCost, replaceCost) + insertCost;
        // delete
        int p2 = process(str1, index1 - 1, str2, index2, insertCost, deleteCost, replaceCost) + deleteCost;
        // -1 or replace
        int p3 = str1[index1 - 1] == str2[index2 - 1]
                ? process(str1, index1 - 1, str2, index2 - 1, insertCost, deleteCost, replaceCost)
                : process(str1, index1 - 1, str2, index2 - 1, insertCost, deleteCost, replaceCost) + replaceCost;

        return Math.min(Math.min(p1, p2), p3);
    }

    public static int minCost2(String s1, String s2, int insertCost, int deleteCost, int replaceCost) {
        if (s1 == null || s2 == null) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int len1 = str1.length;
        int len2 = str2.length;
        int[][] dp = new int[len1 + 1][len2 + 1];

        // col 0
        for (int index1 = 1; index1 <= len1; index1++) {
            dp[index1][0] = index1 * deleteCost;
        }

        // row 0
        for (int index2 = 1; index2 <= len2; index2++) {
            dp[0][index2] = index2 * insertCost;
        }

        for (int index1 = 1; index1 <= len1; index1++) {
            for (int index2 = 1; index2 <= len2; index2++) {
                // insert
                int p1 = dp[index1][index2 - 1] + insertCost;
                // delete
                int p2 = dp[index1 - 1][index2] + deleteCost;
                // -1 or replace
                int p3 = str1[index1 - 1] == str2[index2 - 1]
                        ? dp[index1 - 1][index2 - 1]
                        : dp[index1 - 1][index2 - 1] + replaceCost;

                dp[index1][index2] = Math.min(Math.min(p1, p2), p3);
            }
        }

        return dp[len1][len2];
    }
}
