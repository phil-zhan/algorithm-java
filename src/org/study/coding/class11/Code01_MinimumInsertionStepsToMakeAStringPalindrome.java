package org.study.coding.class11;

import java.util.ArrayList;
import java.util.List;


/**
 * // 本题测试链接 : https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
 * 1. 问题一：一个字符串至少需要添加多少个字符能整体变成回文串
 * 范围尝试模型
 * 考虑 [L...R] 范围上，要让其变为回文串，至少需要添加多少个字符【上三角】
 * dp[i][j]: 表示 i到j 范围上
 * <p>
 * 对角线都是 0 【一个字符，其本身就是回文】
 * 倒数第二条对角线。就两个字符，如果两个字符相等的话，就添加0个。不相等的话，就添加 1 个
 * <p>
 * 普遍位置：
 * dp[i][j]:
 * 1)考虑dp[i][j-1] 需要多少个。最后再在最左侧添加一个 arr[j]  【i位置的字符不等于 j位置的字符】
 * 2)考虑dp[i+1][j] 需要多少个。最后再在最右侧添加一个 arr[i]  【i位置的字符不等于 j位置的字符】
 * 3）如果 i位置的字符等于 j 位置的字符，那么中间需要多少个，就是这个串需要多少个
 * 三种可能性抓一个最小的就是答案
 * dp表从下往上。从左往右去填
 * <p>
 * <p>
 * <p>
 * <p>
 * 2. 问题二：返回问题一的其中一种添加结果
 * 3. 问题三：返回问题一的所有添加结果
 *
 * @since 2022-03-12 02:24:04
 */
public class Code01_MinimumInsertionStepsToMakeAStringPalindrome {

    /**
     * 测试链接只测了本题的第一问，直接提交可以通过
     *
     * @since 2022-03-12 02:36:00
     */
    public static int minInsertions(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    /**
     * 本题第二问，返回其中一种结果
     * 利用dp表进行回溯，找到答案
     * 准备一个 n + ans 个长度的一维数组 【n是原字符长度。ans 是至少需要多少个字符，才能使原字符变为回文串】
     * <p>
     * 看当前的位置是从哪些位置变化来的
     * 答案位置是 dp[0][n-1]
     * 在某个位置，
     * 如果从左边变化来的。就说明当初是 先搞定了 [L...R-1] 范围上的字符，最后再在最左侧加一个 arr[R]使其变为回文的
     * 就在当前范围的首尾位置设置上答案字符【arr[R]】.首尾都是它
     * <p>
     * 如果是从下边变化来的。就说明当初是 先搞定了 [L+1...R] 范围上的字符，最后再在最右侧加一个 arr[L]使其变为回文的
     * 就在当前范围的首尾位置设置上答案字符【arr[L]】.首尾都是它
     * <p>
     * 如果是从左下边变化来的。就说明当初是 先搞定了 [L+1...R-1] 范围上的字符，L位置的字符和R位置的字符是自我消化的
     * 就在当前范围的首尾位置设置上答案字符【arr[L]】.首尾都是它【因为首尾是相等的】
     * <p>
     * <p>
     * 问题三是收集所有的可能。就利用递归。在遇到分支的时候。所有分支都去走深度优先
     *
     * @since 2022-03-12 02:36:11
     */
    public static String minInsertionsOneWay(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        int L = 0;
        int R = N - 1;

        // 准备收集答案
        char[] ans = new char[N + dp[L][R]];
        int ansl = 0;
        int ansr = ans.length - 1;

        // 保证窗口范围
        // 保证 R-1 也不会到 L 的左边去
        // 保证 L+1 也不会到 R 的右边去
        while (L < R) {
            // 用的是 else if  只考虑一个可能性
            if (dp[L][R - 1] == dp[L][R] - 1) {
                // 是先搞定 [L...R-1]。最后再搞定 R
                ans[ansl++] = str[R];
                ans[ansr--] = str[R--];
            } else if (dp[L + 1][R] == dp[L][R] - 1) {
                // 是先搞定 [L+1...R]。最后再搞定 L
                ans[ansl++] = str[L];
                ans[ansr--] = str[L++];
            } else {
                // 是先搞定 [L+1...R-1]。L 和 R自己消化
                ans[ansl++] = str[L++];
                ans[ansr--] = str[R--];
            }
        }

        // 只有一个数
        if (L == R) {
            ans[ansl] = str[L];
        }
        return String.valueOf(ans);
    }

    /**
     * 本题第三问，返回所有可能的结果
     * 问题三是收集所有的可能。就利用递归。在遇到分支的时候。所有分支都去走深度优先
     *
     * @since 2022-03-12 02:36:20
     */
    public static List<String> minInsertionsAllWays(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            int[][] dp = new int[N][N];
            for (int i = 0; i < N - 1; i++) {
                dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
            }
            for (int i = N - 3; i >= 0; i--) {
                for (int j = i + 2; j < N; j++) {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                    if (str[i] == str[j]) {
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                    }
                }
            }
            int M = N + dp[0][N - 1];
            char[] path = new char[M];
            process(str, dp, 0, N - 1, path, 0, M - 1, ans);
        }
        return ans;
    }

    /**
     * 当前来到的动态规划中的格子，(L,R)
     * path ....  [pl....pr] ....
     *
     * @since 2022-03-12 02:36:29
     */
    public static void process(char[] str, int[][] dp, int L, int R, char[] path, int pl, int pr, List<String> ans) {
        if (L >= R) {
            // L > R  L==R
            if (L == R) {
                path[pl] = str[L];
            }
            ans.add(String.valueOf(path));
        } else {
            // 用的是 if 考虑所有可能性
            // 虽然是深度优先，但是不用恢复现场。后面填的时候，会重新覆盖前面填过的【仅限本题】
            if (dp[L][R - 1] == dp[L][R] - 1) {
                path[pl] = str[R];
                path[pr] = str[R];
                process(str, dp, L, R - 1, path, pl + 1, pr - 1, ans);
            }
            if (dp[L + 1][R] == dp[L][R] - 1) {
                path[pl] = str[L];
                path[pr] = str[L];
                process(str, dp, L + 1, R, path, pl + 1, pr - 1, ans);
            }
            if (str[L] == str[R] && (dp[L + 1][R - 1] == dp[L][R])) {
                path[pl] = str[L];
                path[pr] = str[R];
                process(str, dp, L + 1, R - 1, path, pl + 1, pr - 1, ans);
            }
        }
    }

    public static void main(String[] args) {
        String s = null;
        String ans2 = null;
        List<String> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "mbadm";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "leetcode";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "aabaa";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);
        System.out.println("本题第二问，返回其中一种结果测试结束");

        System.out.println();

        System.out.println("本题第三问，返回所有可能的结果测试开始");
        s = "mbadm";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "leetcode";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "aabaa";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能的结果测试结束");
    }

}
