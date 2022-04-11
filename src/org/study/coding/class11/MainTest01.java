package org.study.coding.class11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author phil
 * @date 2022-04-08 11:02:48
 */
public class MainTest01 {

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxLen = 10;
        for (int time = 0; time < testTime; time++) {
            String s = randomStr(maxLen);

            // 验证问题1
            int ans1 = minInsertions(s);
            int ans2 = minInsertions(s);
            if (ans1 != ans2) {
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                System.out.println(s);
                System.out.println("ans1:" + ans1);
                System.out.println("ans2:" + ans2);
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
            }

            // 验证问题2
            String ans3 = minInsertionsOneWay(s);
            if (!checkPalindrome(ans3)){
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                System.out.println(s);
                System.out.println("ans3:" + ans3);
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
            }

            // 验证问题3
            List<String> ans4 = minInsertionsAllWays(s);
            ans4.forEach((ele)->{
                if (!checkPalindrome(ele)){
                    System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                    System.out.println(s);
                    System.out.println("ele:" + ele);
                    System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                }
            });

        }
        System.out.println("!!!!!!!!!!Nice!!!!!!!!!!");




    }


    public static String randomStr(int maxLen) {
        int len = (int) (Math.random() * (maxLen + 1));
        char[] ori = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] str = new char[len];

        for (int index = 0; index < len; index++) {
            // 下标 0...25
            str[index] = ori[(int) (Math.random() * (26))];
        }
        return new String(str);
    }

    public static boolean checkPalindrome(String s){
        char[] str = s.toCharArray();
        int left = 0;
        int right = str.length-1;
        boolean ans = true;
        while (left<right){
            if (str[left] != str[right]){
                ans = false;
                break;
            }
            left++;
            right--;
        }
        return ans;
    }

    public static int minInsertions(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int len = s.length();
        char[] str = s.toCharArray();
        int[][] dp = new int[len][len];
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        for (int i = len - 3; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        return dp[0][len - 1];
    }

    public static String minInsertionsOneWay(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int len = s.length();
        char[] str = s.toCharArray();

        // dp[i][j]:[i...j]范围上，至少需要添加多少字符串，才能让源字符串变成回文串
        int[][] dp = new int[len][len];
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        for (int i = len - 3; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        // collect ans
        char[] path = new char[len + dp[0][len - 1]];
        int left = 0;
        int right = len - 1;

        int pl = 0;
        int pr = path.length - 1;
        while (left < right) {
            if (dp[left][right] == dp[left + 1][right] + 1) {
                // 先右边，
                path[pl++] = str[left];
                path[pr--] = str[left++];
            } else if (dp[left][right] == dp[left][right - 1] + 1) {
                // 先左边
                path[pl++] = str[right];
                path[pr--] = str[right--];
            } else {
                // 左右相等 先中间
                path[pl++] = str[left++];
                path[pr--] = str[right--];
            }
        }
        if (left == right) {
            path[pl] = str[left];
        }
        return new String(path);
    }

    public static List<String> minInsertionsAllWays(String s) {
        List<String> list = new ArrayList<>();
        if (s == null || s.length() < 2) {
            list.add(s);
            return list;
        }
        char[] str = s.toCharArray();
        int len = s.length();
        int[][] dp = new int[len][len];
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }

        for (int i = len - 3; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }
        int m = len + dp[0][len - 1];
        process(str, dp, 0, len - 1, 0, m - 1, new char[m], list);
        return list;
    }

    public static void process(char[] str, int[][] dp, int left, int right, int pl, int pr, char[] path, List<String> ans) {
        if (left >= right) {
            if (left == right) {
                path[pl] = str[left];
            }
            ans.add(new String(path));
        } else {
            if (dp[left][right] == dp[left + 1][right] + 1) {
                // 先右边，
                path[pl] = str[left];
                path[pr] = str[left];
                process(str, dp, left + 1, right, pl + 1, pr - 1, path, ans);

            }
            if (dp[left][right] == dp[left][right - 1] + 1) {
                // 先左边
                path[pl] = str[right];
                path[pr] = str[right];
                process(str, dp, left, right - 1, pl + 1, pr - 1, path, ans);
            }
            if (str[left] == str[right] && (dp[left + 1][right - 1] == dp[left][right])) {
                // 左右相等 先中间
                path[pl] = str[left];
                path[pr] = str[right];
                process(str, dp, left + 1, right - 1, pl + 1, pr - 1, path, ans);
            }
        }
    }
}
