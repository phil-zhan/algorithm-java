package org.study.coding.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * // 本题测试链接 : https://leetcode.com/problems/palindrome-partitioning-ii/
 * 4. 问题一：一个字符串至少要切几刀能让切出来的子串都是回文串
 * 5. 问题二：返回问题一的其中一种划分结果
 * 6. 问题三：返回问题一的所有划分结果
 * <p>
 * 考虑从左往右的尝试
 * 1）考虑第一个字符是否是回文，是的话，看后面需要切多少刀。不是就跳过
 * 2）考虑前两个字符是否是回文，是的话，看后面需要切多少刀。不是就跳过
 * 3）考虑前三个字符是否是回文，是的话，看后面需要切多少刀。不是就跳过
 * 。。。
 * 以此类推
 * 最后累加
 * <p>
 * 也就是考虑第一刀切在哪。再加上后面的数量。
 *
 * @since 2022-03-12 05:09:37
 */
public class Code02_PalindromePartitioningII {


    /**
     * 测试链接只测了本题的第一问，直接提交可以通过
     *
     * @since 2022-03-12 05:09:47
     */
    public static int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;

        // 为了避免每次都要去检查[L...R]的范围上是不是问问。导致时间复杂度增加
        // 先生成一张表. checkMap[i][j] 表示i 到 j 范围上是否是回文
        boolean[][] checkMap = createCheckMap(str, N);

        // dp[i]: 表示 [i...n-1] 需要切成多少部分
        int[] dp = new int[N];
        // 每个位置都需要后面的位置来做参考。这里补个0.省去对最后的位置单独考虑
        //dp[N] = 0;

        // 考虑第一刀切在哪。切完第一刀后。剩下的 [i...n-1] 还需要多少刀
        // 切第一刀的时候，必须保证所切位置的左边是回文串
        for (int i = N - 1; i >= 0; i--) {
            if (checkMap[i][N - 1]) {
                // 剩下的位置不需要再切了
                dp[i] = 1;
            } else {
                // 考虑后面的还需要切的最少部分。
                // 是从右往左，后面的都是求过的，直接拿dp 。再抓一个最小值即可
                int next = Integer.MAX_VALUE;
                for (int j = i; j < N; j++) {

                    // [i...j] 是回文，取 j+1 的dp,也就是 [j+1...n-1] 需要切出多少部分
                    // 也就是当前来的 i 号字符，最多能搞定多少个字符，
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }

                // i 号字符搞定的那一部分。加上剩下的，就是当前的
                dp[i] = 1 + next;
            }
        }

        // 部分数减1，就是需要切的刀数
        return dp[0] - 1;
    }

    public static boolean[][] createCheckMap(char[] str, int N) {
        boolean[][] ans = new boolean[N][N];

        // 对角线和 贴近对角线的 上对角线【只有一个数】。都是 true
        // 为了每次填 两个。少一次循环。将对角线的最后一个数拆出去单独填
        for (int i = 0; i < N - 1; i++) {
            ans[i][i] = true;
            ans[i][i + 1] = str[i] == str[i + 1];
        }
        ans[N - 1][N - 1] = true;

        // 普遍位置
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {

                // 左右两边的数相等。中间的位置也都要是回文
                // 中间的范围，也就是左下角的位置
                ans[i][j] = str[i] == str[j] && ans[i + 1][j - 1];
            }
        }
        return ans;
    }

    /**
     * 本题第二问，返回其中一种结果
     * 同Code01.采用回溯法
     *
     * @since 2022-03-12 05:09:54
     */
    public static List<String> minCutOneWay(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str, N);

            // dp[i]: 表示 [i...n-1] 需要切成多少部分
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }
            // dp[i]
            // 如果 (0....5) 回文！  dp[0] == dp[6] + 1
            // (0....5)
            for (int i = 0, j = 1; j <= N; j++) {
                // 前面一部分是回文，
                // [i...n-1] 所切成的部分等于 [j...n-1] 所切成的部分加1
                // 【加这个条件，是为了将前面部分推的尽可能的长】
                //
                // j及其后面至少需要切成的部分数加上[i...j-1] 这一部分，正好等于[i...n-1]的部分数
                //
                // 如果不满足该条件，说明在切的时候，压根就不是将当前的部分作为一部分去切的
                // 说明在 j的前面切了一刀
                // [i...j-1] 是当前字符串的第一个最长回文串
                if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                    ans.add(s.substring(i, j));
                    i = j;
                }
            }
        }
        return ans;
    }

    /**
     * 本题第三问，返回所有结果
     *
     * @since 2022-03-12 05:10:15
     */
    public static List<List<String>> minCutAllWays(String s) {
        List<List<String>> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str, N);
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }
            process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
        }
        return ans;
    }

    /**
     * 考虑 [i...j]作为当前的第一部分。去考虑剩下的
     *
     * s[0....i-1]  存到path里去了
     * s[i..j-1]考察的分出来的第一份
     *
     * @since 2022-03-12 05:10:35
     */
    public static void process(String s, int i, int j, boolean[][] checkMap, int[] dp,
                               List<String> path,
                               List<List<String>> ans) {
        if (j == s.length()) {
            // s[i...N-1]
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));

                // 这里一定要拷贝
                // 不然重复加一个内存地址，后面的值也会影响先前进来过的值
                ans.add(copyStringList(path));

                // 清理现场【拷贝完之后就删除】
                path.remove(path.size() - 1);
            }
        } else {
            // s[i...j-1]
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));

                // 前面的 [i...j-1] 已经考虑过了。去考虑从 j 开始到最后
                process(s, j, j + 1, checkMap, dp, path, ans);

                // 清理现场【子步骤搞定之后就删除】
                path.remove(path.size() - 1);
            }

            // 当前的 [i...j-1] 没搞定，准备将 j指针后裔，扩大窗口范围
            process(s, i, j + 1, checkMap, dp, path, ans);
        }
    }

    public static List<String> copyStringList(List<String> list) {
        return new ArrayList<>(list);
    }

    public static void main(String[] args) {
        String s;
        List<String> ans2;
        List<List<String>> ans3;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "abacbc";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabccbac";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabaa";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();
        System.out.println("本题第二问，返回其中一种结果测试结束");
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试开始");
        s = "cbbbcbc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "aaaaaa";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "fcfffcffcc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试结束");
    }

}
