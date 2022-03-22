package org.study.coding.class20;


/**
 * 4. 给定一个字符串str，当然可以生成很多子序列，返回有多少个子序列是回文子序列，空序列不算回文
 * 比如，str = “aba”，回文子序列：{a}、{a}、 {a,a}、 {b}、{a,b,a}，返回5
 * <p>
 * <p>
 * 解法：
 * 设计dp。【范围上的尝试模型】
 * dp[i][j]：表示str[i...j] 范围上的所有子序列，能搞出几个回文来。空串不算
 * <p>
 * 1）：回文子序列不选择i位置的字符，也不选择j位置的字符。回文子序列的数量是 a
 * 2）：回文子序列一定不选i位置的字符。但选j位置的字符。回文子序列的数量是 b
 * 3）：回文子序列一定选i位置的字符。但不选j位置的字符。回文子序列的数量是 c
 * 4）：回文子序列一定选i位置的字符。也选j位置的字符。回文子序列的数量是 d
 * 需要满足str[i] = str[j]
 * 当满足这个情况的前提下。只要[i+1...j-1] 上的任意一个回文，在加上i和j位置，都能构成回文
 * [i+1...j-1] 范围上的位置，一个也不要，在加上i和j位置，也能构成回文.dp[i+1][j-1] 上的回文数量是不算空串的
 * d = dp[i+1][j-1]+1
 * <p>
 * <p>
 * a+b = dp[i+1][j] : 不要i位置的。j位置的可要可不要
 * a+c = dp[i][j-1] : 不要j 位置的字符。i位置的字符可要可不要
 * a = dp[i+1][j-1] : 左右两个位置的都不要
 * <p>
 * dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1] = a+b+c
 * 可能的话再加一个d
 * 就是dp[i][j] = dp[i][j] + d
 *
 * @since 2022-03-22 10:59:59
 */
public class Code04_PalindromeWays {

    public static int ways1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        char[] path = new char[s.length];
        return process(str.toCharArray(), 0, path, 0);
    }

    public static int process(char[] s, int si, char[] path, int pi) {
        if (si == s.length) {
            return isP(path, pi) ? 1 : 0;
        }
        int ans = process(s, si + 1, path, pi);
        path[pi] = s[si];
        ans += process(s, si + 1, path, pi + 1);
        return ans;
    }

    public static boolean isP(char[] path, int pi) {
        if (pi == 0) {
            return false;
        }
        int L = 0;
        int R = pi - 1;
        while (L < R) {
            if (path[L++] != path[R--]) {
                return false;
            }
        }
        return true;
    }

    public static int ways2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        int n = s.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = s[i] == s[i + 1] ? 3 : 2;
        }
        for (int L = n - 3; L >= 0; L--) {
            for (int R = L + 2; R < n; R++) {
                dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1];
                if (s[L] == s[R]) {
                    dp[L][R] += dp[L + 1][R - 1] + 1;
                }

                // 恶意炫技
                // dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - (s[L] != s[R] ? dp[L + 1][R - 1] : -1);

            }
        }
        return dp[0][n - 1];
    }

    public static String randomString(int len, int types) {
        char[] str = new char[len];
        for (int i = 0; i < str.length; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * types));
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int N = 10;
        int types = 5;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * N);
            String str = randomString(len, types);
            int ans1 = ways1(str);
            int ans2 = ways2(str);
            if (ans1 != ans2) {
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}
