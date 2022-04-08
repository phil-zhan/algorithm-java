package org.study.coding.class10;

/**
 * 题测试链接 : https://leetcode-cn.com/problems/boolean-evaluation-lcci/
 *
 * 给定一个布尔表达式和一个期望的布尔结果 result，
 * 布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。
 * 实现一个函数，算出有几种可使该表达式得出 result 值的括号方法。
 *
 * 示例 1:
 * 输入: s = "1^0|0|1", result = 0
 *
 * 输出: 2
 * 解释:两种可能的括号方法是
 * 1^(0|(0|1))
 * 1^((0|0)|1)
 * 示例 2:
 *
 * 输入: s = "0&0&0&1^1|0", result = 1
 *
 * 输出: 10
 *
 * 解法：
 *
 * 偶数位置 非0即1
 * 奇数位置 只可能是逻辑符号 and  or xor
 *
 * 总长度肯定是奇数。 0或1 中间夹杂着逻辑符号。开头和结尾位置肯定 非0即1
 *
 * 考虑每一个逻辑符号。最后才集合该符号的可能性有多少种。求累加和
 *
 * 返回str[L...R]这一段，为true的方法数，和false的方法数。【范围上的尝试】
 *
 * 假设每个逻辑字符
 * 左边为 true 的可能有 a种     右边为 true 的可能有 b种
 * 左边为 false 的可能有 c种    右边为 false 的可能有 d种
 *
 *
 * 最后为true的总方法数
 * 如果是 ’&‘ ： a*b
 * 如果是 ‘|’ ： a*b + a*d + c*b
 * 如果是 ‘^’ ： a*d + c*b
 *
 * 最后为false的总方法数
 * 如果是 ’&‘ ： a*d +  c*b + c*d
 * 如果是 ‘|’ ： c*d
 * 如果是 ‘^’ ： a*b + c*d
 *
 * @since 2022-03-12 10:46:56
 */
public class Code05_BooleanEvaluation {

    /**
     * 返回 express 随机加括号，能搞出 desired（0或1） 的方法数有多少种
     * @since 2022-03-12 01:28:52
     */
    public static int countEval0(String express, int desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        int N = exp.length;
        Info[][] dp = new Info[N][N];
        Info allInfo = func(exp, 0, exp.length - 1, dp);
        return desired == 1 ? allInfo.t : allInfo.f;
    }

    public static class Info {

        // 负责的范围上，为true的方法数
        public int t;

        // 负责的范围上，为false的方法数
        public int f;
        public Info(int tr, int fa) {
            t = tr;
            f = fa;
        }
    }

    // 限制:
    // L...R上，一定有奇数个字符
    // L位置的字符和R位置的字符，非0即1，不能是逻辑符号！
    // 返回str[L...R]这一段，为true的方法数，和false的方法数
    public static Info func(char[] str, int L, int R, Info[][] dp) {
        if (dp[L][R] != null) {
            return dp[L][R];
        }
        int t = 0;
        int f = 0;
        if (L == R) {
            // 注意这个判断的时候，用字符的1不要用数字的1.容易出错
            t = str[L] == '1' ? 1 : 0;
            f = str[L] == '0' ? 1 : 0;
        } else {
            // L..R >=3
            // 每一个种逻辑符号，split枚举的东西。枚举每一个逻辑符号做最后一个字符
            // 都去试试最后结合
            // L 和 R 肯定都是数字
            for (int split = L + 1; split < R; split += 2) {
                Info leftInfo = func(str, L, split - 1, dp);
                Info rightInfo = func(str, split + 1, R, dp);
                int a = leftInfo.t;
                int b = leftInfo.f;
                int c = rightInfo.t;
                int d = rightInfo.f;
                switch (str[split]) {
                    case '&':
                        t += a * c;
                        f += b * c + b * d + a * d;
                        break;
                    case '|':
                        t += a * c + a * d + b * c;
                        f += b * d;
                        break;
                    case '^':
                        t += a * d + b * c;
                        f += a * c + b * d;
                        break;
					default:
						// 啥也不干
                }
            }

        }
        dp[L][R] = new Info(t, f);
        return dp[L][R];
    }

    public static int countEval1(String express, int desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        return f(exp, desired, 0, exp.length - 1);
    }

    public static int f(char[] str, int desired, int L, int R) {
        if (L == R) {
            if (str[L] == '1') {
                return desired;
            } else {
                return desired ^ 1;
            }
        }
        int res = 0;
        if (desired == 1) {
            for (int i = L + 1; i < R; i += 2) {
                switch (str[i]) {
                    case '&':
                        res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
                        break;
                    case '|':
                        res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
                        res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
                        res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
                        break;
                    case '^':
                        res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
                        res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
                        break;
                    default:
                }
            }
        } else {
            for (int i = L + 1; i < R; i += 2) {
                switch (str[i]) {
                    case '&':
                        res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
                        res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
                        res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
                        break;
                    case '|':
                        res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
                        break;
                    case '^':
                        res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
                        res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
                        break;
                    default:
                }
            }
        }
        return res;
    }

    public static int countEval2(String express, int desired) {
        if (express == null || "".equals(express)) {
            return 0;
        }
        char[] exp = express.toCharArray();
        int N = exp.length;

        // dp[i][j][k] . j 到 k 范围上搞定 i 的方法数
        int[][][] dp = new int[2][N][N];
        dp[0][0][0] = exp[0] == '0' ? 1 : 0;
        dp[1][0][0] = dp[0][0][0] ^ 1;

        for (int i = 2; i < exp.length; i += 2) {
            dp[0][i][i] = exp[i] == '1' ? 0 : 1;
            dp[1][i][i] = exp[i] == '0' ? 0 : 1;
            for (int j = i - 2; j >= 0; j -= 2) {
                for (int k = j; k < i; k += 2) {
                    if (exp[k + 1] == '&') {
                        dp[1][j][i] += dp[1][j][k] * dp[1][k + 2][i];
                        dp[0][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[0][k + 2][i] + dp[0][j][k] * dp[1][k + 2][i];
                    } else if (exp[k + 1] == '|') {
                        dp[1][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
                        dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i];
                    } else {
                        dp[1][j][i] += dp[0][j][k] * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
                        dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i] + dp[1][j][k] * dp[1][k + 2][i];
                    }
                }
            }
        }
        return dp[desired][0][N - 1];
    }

}
