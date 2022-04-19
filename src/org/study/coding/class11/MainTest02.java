package org.study.coding.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phil
 * @date 2022-04-08 15:48:46
 */
public class MainTest02 {

    public static void main(String[] args) {
        System.out.println(minCut("abcdc"));
        System.out.println(minCutOneWay("abcdc"));
        System.out.println(minCutAllWays("abcdc"));
    }

    public static int minCut(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        char[] str = s.toCharArray();
        int len = str.length;
        boolean[][] checkMap = createCheckMap(str);

        int[] dp = new int[len + 1];
        dp[len] = 0;

        for (int i = len - 1; i >= 0; i--) {
            if (checkMap[i][len - 1]) {
                // 剩下的都是回文，完整的一个部分就是回文，不用切
                dp[i] = 1;
            } else {
                int next = Integer.MAX_VALUE;
                for (int j = i; j < len; j++) {
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                dp[i] = next + 1;
            }
        }
        return dp[0] - 1;
    }

    public static boolean[][] createCheckMap(char[] str) {
        int len = str.length;
        boolean[][] dp = new boolean[len][len];
        for (int index = 0; index < len - 1; index++) {
            dp[index][index] = true;
            dp[index][index + 1] = str[index] == str[index + 1];
        }
        dp[len - 1][len - 1] = true;

        for (int i = len - 3; i >= 0; i--) {
            for (int j = i + 2; j < len; j++) {
                dp[i][j] = str[i] == str[j] && dp[i + 1][j - 1];
            }
        }
        return dp;
    }


    public static List<String> minCutOneWay(String s) {
        List<String> list = new ArrayList<>();
        if (s == null || s.length() < 2) {
            list.add(s);
            return list;
        }

        char[] str = s.toCharArray();
        int len = str.length;
        boolean[][] checkMap = createCheckMap(str);
        int[] dp = new int[len + 1];
        dp[len] = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (checkMap[i][len - 1]) {
                dp[i] = 1;
            } else {
                int next = Integer.MAX_VALUE;
                for (int j = i; j < len; j++) {
                    if (checkMap[i][j]) {
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                dp[i] = next + 1;
            }
        }

        for (int i = 0, j = 1; j <= len; j++) {
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                list.add(s.substring(i, j));
                i = j;
            }
        }
        return list;
    }

    public static List<List<String>> minCutAllWays(String s) {
        List<List<String>> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str);
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
}
