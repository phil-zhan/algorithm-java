package org.study.coding.class30;

/**
 * 一条包含字母A-Z 的消息通过以下的方式进行了 编码 ：
 * <p>
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
 * <p>
 * "AAJF" 对应分组 (1 1 10 6)
 * "KJF" 对应分组 (11 10 6)
 * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
 * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。
 * 例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，相当于解码该字符串可以表示的任何编码消息。
 * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
 * 由于答案数目可能非常大，返回10^9 + 7的模。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "*"
 * 输出：9
 * 解释：这一条编码消息可以表示 "1"、"2"、"3"、"4"、"5"、"6"、"7"、"8" 或 "9" 中的任意一条。
 * 可以分别解码成字符串 "A"、"B"、"C"、"D"、"E"、"F"、"G"、"H" 和 "I" 。
 * 因此，"*" 总共有 9 种解码方法。
 * 示例 2：
 * <p>
 * 输入：s = "1*"
 * 输出：18
 * 解释：这一条编码消息可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条。
 * 每种消息都可以由 2 种方法解码（例如，"11" 可以解码成 "AA" 或 "K"）。
 * 因此，"1*" 共有 9 * 2 = 18 种解码方法。
 * 示例 3：
 * <p>
 * 输入：s = "2*"
 * 输出：15
 * 解释：这一条编码消息可以表示 "21"、"22"、"23"、"24"、"25"、"26"、"27"、"28" 或 "29" 中的任意一条。
 * "21"、"22"、"23"、"24"、"25" 和 "26" 由 2 种解码方法，但 "27"、"28" 和 "29" 仅有 1 种解码方法。
 * 因此，"2*" 共有 (6 * 2) + (3 * 1) = 12 + 3 = 15 种解码方法。
 * <p>
 * <p>
 * 解法：
 * 从左到右的尝试模型
 * 来到index位置，考虑index自己单个作为一个字符【index不能单独面对0字符】
 * 也可以考虑index位置和index+1位置组合成一个字符 【不要超过27】
 * 需要注意的是
 * <p>
 * 1）str[i] != '*'
 * 考虑单独自己，也可以考虑和i+1位置一起
 * 2) str[i] == '*'
 * 考虑单独自己，也可以考虑和i+1位置一起
 *
 * @since 2022-04-15 07:38:04
 */
public class Problem_0639_DecodeWaysII {
    public static void main(String[] args) {

        System.out.println(numDecodings0("1*"));
    }

    public static int numDecodings0(String str) {
        return f(str.toCharArray(), 0);
    }

    public static int f(char[] str, int i) {
        if (i == str.length) {
            return 1;
        }
        if (str[i] == '0') {
            return 0;
        }
        // str[index]有字符且不是'0'
        if (str[i] != '*') {
            // str[index] = 1~9
            // i -> 单转
            int p1 = f(str, i + 1);

            // 没有 i+1 位置了
            if (i + 1 == str.length) {
                return p1;
            }

            // i+1 位置也要分是不是 *
            if (str[i + 1] != '*') {
                int num = (str[i] - '0') * 10 + str[i + 1] - '0';
                int p2 = 0;
                if (num < 27) {
                    p2 = f(str, i + 2);
                }
                return p1 + p2;
            } else {
                // str[i+1] == '*'
                // i i+1 -> 一起转 1* 2* 3* 9*
                int p2 = 0;
                if (str[i] < '3') {

                    // * 可以表示 1...9
                    p2 = f(str, i + 2) * (str[i] == '1' ? 9 : 6);
                }
                return p1 + p2;
            }
        } else { // str[i] == '*' 1~9
            // i 单转 9种
            int p1 = 9 * f(str, i + 1);
            if (i + 1 == str.length) {
                return p1;
            }

            // 考虑一起转
            if (str[i + 1] != '*') {
                // * 0 10 20
                // * 1 11 21
                // * 2 12 22
                // * 3 13 23
                // * 6 16 26
                // * 7 17
                // * 8 18
                // * 9 19
                int p2 = (str[i + 1] < '7' ? 2 : 1) * f(str, i + 2);
                return p1 + p2;
            } else { // str[i+1] == *
                // **
                // 11~19 9
                // 21 ~26 6
                // 15
                int p2 = 15 * f(str, i + 2);
                return p1 + p2;
            }
        }
    }

    public static long mod = 1000000007;

    public static int numDecodings1(String str) {
        long[] dp = new long[str.length()];
        return (int) ways1(str.toCharArray(), 0, dp);
    }

    /**
     * 缓存法
     *
     * @since 2022-04-15 07:46:00
     */
    public static long ways1(char[] s, int i, long[] dp) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return 0;
        }
        if (dp[i] != 0) {
            return dp[i];
        }
        long ans = ways1(s, i + 1, dp) * (s[i] == '*' ? 9 : 1);
        if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
            if (i + 1 < s.length) {
                if (s[i + 1] == '*') {
                    ans += ways1(s, i + 2, dp) * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                } else {
                    if (s[i] == '*') {
                        ans += ways1(s, i + 2, dp) * (s[i + 1] < '7' ? 2 : 1);
                    } else {
                        ans += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? ways1(s, i + 2, dp) : 0;
                    }
                }
            }
        }
        ans %= mod;
        dp[i] = ans;
        return ans;
    }

    /**
     * 动态规划
     *
     * @since 2022-04-15 07:46:14
     */
    public static int numDecodings2(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long[] dp = new long[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] != '0') {
                dp[i] = dp[i + 1] * (s[i] == '*' ? 9 : 1);
                if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
                    if (i + 1 < n) {
                        if (s[i + 1] == '*') {
                            dp[i] += dp[i + 2] * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                        } else {
                            if (s[i] == '*') {
                                dp[i] += dp[i + 2] * (s[i + 1] < '7' ? 2 : 1);
                            } else {
                                dp[i] += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? dp[i + 2] : 0;
                            }
                        }
                    }
                }
                dp[i] %= mod;
            }
        }
        return (int) dp[0];
    }

    /**
     * 空间优化
     *
     * @since 2022-04-15 07:46:35
     */
    public static int numDecodings3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long a = 1;
        long b = 1;
        long c = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] != '0') {
                c = b * (s[i] == '*' ? 9 : 1);
                if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
                    if (i + 1 < n) {
                        if (s[i + 1] == '*') {
                            c += a * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                        } else {
                            if (s[i] == '*') {
                                c += a * (s[i + 1] < '7' ? 2 : 1);
                            } else {
                                c += a * (((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? 1 : 0);
                            }
                        }
                    }
                }
            }
            c %= mod;
            a = b;
            b = c;
            c = 0;
        }
        return (int) b;
    }

}
