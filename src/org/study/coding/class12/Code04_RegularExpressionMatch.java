package org.study.coding.class12;

/**
 * // 测试链接 : https://leetcode.com/problems/regular-expression-matching/
 * <p>
 * 给你一个字符串s和一个字符规律p，请你来实现一个支持 '.'和'*'的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配 零个 或多个前面的那一个元素
 * 所谓匹配，是要涵盖整个字符串s的，而不是部分字符串。
 * 返回p能否匹配s
 * <p>
 * 【‘*’ 只能和前面一个字符搭配使用，也就是两个 '*' 不能放在一起。 ‘*’ 也不能放在最前面】
 * 所谓匹配:也就是能不能通过表达式将目标字符推出来
 * ‘*’ 的前面也能是 ‘.’
 * <p>
 * 解法：
 * 考虑行列（样本）对应模型
 * dp[i][j] : str[i...n-1] 能不能被表达式 exp[j...m-1] 所搞定
 *
 * @since 2022-03-13 11:03:39
 */
public class Code04_RegularExpressionMatch {


    /**
     * 初始尝试版本，不包含斜率优化
     *
     * @since 2022-03-13 08:38:55
     */
    public static boolean isMatch1(String str, String exp) {

        //
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();

        // str不能有 '.' 和 ‘*’
        // exp开头不能是 ‘*’
        // exp两个 ‘*’ 不能挨着
        return isValid(s, e) && process(s, e, 0, 0);
    }


    /**
     * str[si...n-1] 能不能被 exp[ei...m-1]配出来！ true false
     * <p>
     * 来到一个普遍的位置。看看其下一个位置是不是 '*'
     * <p>
     * 1)exp[ei + 1] 下一个位置是‘*’ ：
     * ① ：str[si] != exp[ei] : 只能选择让 exp[ei] 和 exp[ei + 1] 变为 0个字符。让后续 ei+2 去搞定 str[si...n-1]
     * ① ：str[si] == exp[ei] : 可以让 exp[ei+1] 这个 ‘*’ 变成 0、1、2...n-ei  个 exp[ei]  【这里就要枚举了。只要有一个能搞定，当前就能搞定】
     * <p>
     * 2)exp[ei + 1] 下一个位置不是‘*’ ：str[i] 必须要被 exp[ei]. 后面没有可以操作的空间了【要么是两个字符直接相等。要么 exp[ei] 是一个 '.'】
     * 当前查理的话，就去看后面的能不能搞定。当前如果搞不定，直接返回false。
     *
     * @since 2022-03-13 08:39:28
     */
    public static boolean process(char[] s, char[] e, int si, int ei) {
        if (ei == e.length) {
            // exp 没了 str？
            return si == s.length;
        }
        // exp[ei]还有字符
        // ei + 1位置的字符，不是*
        if (ei + 1 == e.length || e[ei + 1] != '*') {
            // ei + 1 不是*
            // str[si] 必须和 exp[ei] 能配上！
            return si != s.length && (e[ei] == s[si] || e[ei] == '.') && process(s, e, si + 1, ei + 1);
        }



        // exp[ei]还有字符
        // ei + 1位置的字符，是*!
        while (si != s.length && (e[ei] == s[si] || e[ei] == '.')) {

            // 所有分支都走，只要有一个能走通，直接返回 true
            // exp[ei]和exp[ei+1]这个*能搞定多少。都去试一遍
            if (process(s, e, si, ei + 2)) {
                return true;
            }
            si++;
        }

        // si 已经结尾了
        // 或者是ei位置搞不定，只能让 ei + 1 位置的 * 变0个字符，然后让 ei+2 的位置继续捣腾
        return process(s, e, si, ei + 2);
    }

    /**
     * 改记忆化搜索+斜率优化
     *
     * 时间不够的话，可以直接放弃斜率优化。将上面的 while 拿过来
     *
     * @since 2022-03-13 09:29:00
     */
    public static boolean isMatch2(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        if (!isValid(s, e)) {
            return false;
        }

        // 如果是 boolean 没有状态来表示没算过
        int[][] dp = new int[s.length + 1][e.length + 1];
        // dp[i][j] = 0, 没算过！
        // dp[i][j] = -1 算过，返回值是false
        // dp[i][j] = 1 算过，返回值是true
        return isValid(s, e) && process2(s, e, 0, 0, dp);
    }

    public static boolean process2(char[] s, char[] e, int si, int ei, int[][] dp) {
        if (dp[si][ei] != 0) {
            return dp[si][ei] == 1;
        }
        boolean ans = false;
        if (ei == e.length) {
            ans = si == s.length;
        } else {
            if (ei + 1 == e.length || e[ei + 1] != '*') {
                ans = si != s.length && (e[ei] == s[si] || e[ei] == '.') && process2(s, e, si + 1, ei + 1, dp);
            } else {

                // 非斜率优化放在这

                if (si == s.length) {
                    // ei ei+1 *
                    ans = process2(s, e, si, ei + 2, dp);
                } else {
                    // si没结束
                    if (s[si] != e[ei] && e[ei] != '.') {
                        // 当前搞不定，只能指望后面了
                        ans = process2(s, e, si, ei + 2, dp);
                    } else {
                        // s[si] 可以和 e[ei]配上
                        ans = process2(s, e, si, ei + 2, dp) || process2(s, e, si + 1, ei, dp);
                    }
                }



            }
        }
        dp[si][ei] = ans ? 1 : -1;
        return ans;
    }

    /**
     * 动态规划版本 + 斜率优化
     *
     * @since 2022-03-13 09:10:41
     */
    public static boolean isMatch3(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        if (!isValid(s, p)) {
            return false;
        }
        int N = s.length;
        int M = p.length;
        boolean[][] dp = new boolean[N + 1][M + 1];
        dp[N][M] = true;
        for (int j = M - 1; j >= 0; j--) {
            dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
        }
        // dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
        if (N > 0 && M > 0) {
            dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = M - 2; j >= 0; j--) {
                if (p[j + 1] != '*') {
                    dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
                } else {
                    if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }


    /**
     * 检查参数有效性
     *
     * @since 2022-03-13 08:38:24
     */
    public static boolean isValid(char[] s, char[] e) {
        // s中不能有'.' or '*'
        for (char c : s) {
            if (c == '*' || c == '.') {
                return false;
            }
        }
        // 开头的e[0]不能是'*'，没有相邻的'*'
        for (int i = 0; i < e.length; i++) {
            if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }
}
