package org.study.coding.class17;

/**
 * 测试链接 : https://leetcode-cn.com/problems/21dk04/
 * 给定两个字符串S和T，返回S的所有子序列中有多少个子序列的字面值等于T
 * 字面值相等，就是所组成的字符串相等
 * 认为每个字符都是不同的。就算是字面值一样。也算不一样
 * S： "1122"
 * T:  "12"
 * 那么S的0、2位置。0、3位置。1、2位置。1、3位置。  所组成的字符串都是不一样的
 * <p>
 * <p>
 * 解法：
 * 考虑样本对应模型
 * dp[i][j]：S[0...i] 随意组成子序列。有多少个子序列的字面值等于T[0...j]这个前缀串
 * 当前来到 i 位置【考虑i位置的字符要不要】
 * 1）：完全不考虑i位置的字符 dp[i][j] = dp[i-1][j]
 * 2）：一定要包含i位置的字符。此时必须S[i]=T[j]。满足这个前提的条件下 dp[i][j] = dp[i-1][j-1]
 * 两种可能性相加，就是当前的dp[i][j]
 *
 *
 *
 * @since 2022-03-18 05:05:56
 */
public class Code04_DistinctSubseq {

    /**
     * 递归
     * @since 2022-03-18 05:33:37
     */
    public static int numDistinct1(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        return process(s, t, s.length, t.length);
    }

    /**
     * 这里的 i、j都是右边界的开区间【也可以理解为包含字符的个数】
     * @since 2022-05-18 14:52:32
     */
    public static int process(char[] s, char[] t, int i, int j) {
        if (j == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        int res = process(s, t, i - 1, j);
        if (s[i - 1] == t[j - 1]) {
            res += process(s, t, i - 1, j - 1);
        }
        return res;
    }


    /**
     * S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列
     * 记为dp[i][j]
     * 可能性1）S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
     * 可能性2）S[...i]的所有子序列中，都必须以s[i]结尾，
     * 这要求S[i] == T[j]，则dp[i][j]包含dp[i-1][j-1]
     *
     * 该写法可能理解比较困难。直接看下一个版本也行
     *
     * @since 2022-03-18 10:21:40
     */
    public static int numDistinct2(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        // dp[i][j] : s[0..i] T[0...j] 【之前的考虑】

        // dp[i][j] : s只拿前i个字符做子序列，有多少个子序列，字面值等于T的前j个字符的前缀串
        // 【和实际的有点出入。之前考虑的是0...i 范围。这里是前 i个。个数是从1开始。范围是从0开始】
        int[][] dp = new int[s.length + 1][t.length + 1];
        // dp[0][0]
        // dp[0][j] = s只拿前0个字符做子序列, T前j个字符
        for (int j = 0; j <= t.length; j++) {
            dp[0][j] = 0;
        }
        // 因为一个普遍的位置，依赖的是它的上边和左上角。
        // 在第二行或第二列的时候。如果S[i]=T[i] ,此时的dp[i][j]应该是等于1.
        // 但是 如果第一行和第一列都设置为0.那么 dp[i][j] = dp[i-1][j] + dp[i-1][j-1].  就会等于0
        // 为了防止这种情况发生。将第一行或第一列设置为0.  也就是 dp[0][j] 或 dp[i][0] 设置为0
        for (int i = 0; i <= s.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= s.length; i++) {
            for (int j = 1; j <= t.length; j++) {
                dp[i][j] = dp[i - 1][j] + (s[i - 1] == t[j - 1] ? dp[i - 1][j - 1] : 0);
            }
        }
        return dp[s.length][t.length];
    }

    public static int numDistinct3(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[] dp = new int[t.length + 1];
        dp[0] = 1;
        for (int j = 1; j <= t.length; j++) {
            dp[j] = 0;
        }
        for (int i = 1; i <= s.length; i++) {
            for (int j = t.length; j >= 1; j--) {
                dp[j] += s[i - 1] == t[j - 1] ? dp[j - 1] : 0;
            }
        }
        return dp[t.length];
    }

    /**
     * dp[i][j]：S[0...i] 随意组成子序列。有多少个子序列的字面值等于T[0...j]这个前缀串
     *
     *
     * @since 2022-03-18 09:45:05
     */
    public static int dp(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int N = s.length;
        int M = t.length;
        int[][] dp = new int[N][M];
        // s[0..0] T[0..0] dp[0][0]
        dp[0][0] = s[0] == t[0] ? 1 : 0;

        // 这张dp的右上半区。s的长度小于t的长度。就算是全部字符都用上，也搞不定t的前缀。都是0


        for (int i = 1; i < N; i++) {
            // 当前位置相等，就等于前一个位置的值加1.否则等于前一个位置的值
            dp[i][0] = s[i] == t[0] ? (dp[i - 1][0] + 1) : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= Math.min(i, M - 1); j++) {
                dp[i][j] = dp[i - 1][j];
                if (s[i] == t[j]) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[N - 1][M - 1];
    }

    public static void main(String[] args) {
        String S = "1212311112121132121231111212113212123111121211321212311112121132121231111212113212123111121211321212311112121132" +
                "1212311112121132121231111212113212123111121211321212311112121132121231111212113212123111121211321212311112121132" +
                "1212311112121132121231111212113212123111121211321212311112121132121231111212113212123111121211321212311112121132" +
                "1212311112121132121231111212113212123111121211321212311112121132121231111212113212123111121211321212311112121132" +
                "1212311112121132121231111212113212123111121211321212311112121132121231111212113212123111121211321212311112121132" +
                "1212311112121132121231111212113212123111121211321212311112121132121231111212113212123111121211321212311112121132";
        String T = "13";

        System.out.println(numDistinct3(S, T));
        System.out.println(dp(S, T));

    }

}
