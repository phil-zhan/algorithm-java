package org.study.coding.class05;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phil
 * @date 2022-03-15 16:57:37
 */
public class MainTest04 {
    public static void main(String[] args) {

        char[] x = {'a', 'b', 'c', 'd'};
        char[] y = {'a', 'd'};

        System.out.println(onlyDelete(x, y));

        int str1Len = 20;
        int str2Len = 10;
        int v = 5;
        int testTime = 10000;
        boolean pass = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String str1 = Code04_DeleteMinCost.generateRandomString(str1Len, v);
            String str2 = Code04_DeleteMinCost.generateRandomString(str2Len, v);
            int ans1 = minCost1(str1, str2);
            int ans2 = Code04_DeleteMinCost.minCost2(str1, str2);
            int ans3 = Code04_DeleteMinCost.minCost3(str1, str2);
            int ans4 = Code04_DeleteMinCost.minCost4(str1, str2);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                pass = false;
                System.out.println(str1);
                System.out.println(str2);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("test pass : " + pass);
    }

    public static int minCost1(String s1, String s2) {
        List<String> subs2 = new ArrayList<>();
        process(s2.toCharArray(), 0, "", subs2);

        subs2.sort((o1, o2) -> o2.length() - o1.length());
        for (String str : subs2) {
            if (s1.contains(str)) {
                return s2.length() - str.length();
            }
        }

        return s2.length();
    }

    public static void process(char[] str2, int index, String path, List<String> subs2) {
        if (str2.length == index) {
            subs2.add(path);
            return;
        }

        process(str2, index + 1, path, subs2);
        process(str2, index + 1, path + str2[index], subs2);
    }

    /**
     * 仅考虑删除的情况下。str1至少需要删除多少个字符，能够变成str2
     *
     * @since 2022-03-15 17:41:27
     */
    public static int onlyDelete(char[] str1, char[] str2) {
        int[][] dp = new int[str1.length + 1][str2.length + 1];
        for (int index1 = 0; index1 <= str1.length; index1++) {
            for (int index2 = 0; index2 <= str2.length; index2++) {
                dp[index1][index2] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;

        // col 0
        for (int index1 = 1; index1 <= str1.length; index1++) {
            dp[index1][0] = index1;
        }

        for (int index1 = 1; index1 <= str1.length; index1++) {
            for (int index2 = 1; index2 <= str2.length; index2++) {
                if (dp[index1 - 1][index2] != Integer.MAX_VALUE) {
                    dp[index1][index2] = dp[index1 - 1][index2] + 1;
                }
                if (str1[index1 - 1] == str2[index2 - 1] && dp[index1 - 1][index2 - 1] != Integer.MAX_VALUE) {
                    dp[index1][index2] = Math.min(dp[index1][index2], dp[index1 - 1][index2 - 1]);
                }

            }
        }

        return dp[str1.length][str2.length];
    }
}
