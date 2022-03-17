package org.study.coding.class14;

/**
 * https://leetcode.com/problems/longest-valid-parentheses/
 *
 * 给定一个只由左括号和右括号的字符串，返回最长的有效括号子串的长度
 *
 * 解法：
 * 考察必须以 i 位置的字符结尾。往左延伸多长能有效【从左往右考察】
 * 1）如果 i 位置是左括号。无法延伸。最大长度是0
 * 2）如果 i 位置是右括号。就看看左边需要延伸多长，能找到和自己配对的左括号。找到之后，
 * 与 i 配对的左括号的再左一个位置的最大延伸长度，也需要加到当前的最大长度里面来。
 * 不需要多步跳。因为在算那个 再左 位置的时候。就已经把前面的有效括号的最大长度累加起来了
 *
 * 往左延伸时、左括号数量不等于右括号数量。就无效
 *
 * @since 2022-03-16 07:43:34
 */
public class Code01_Parentheses {

	public static boolean valid(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			count += str[i] == '(' ? 1 : -1;
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	public static int needParentheses(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				count++;
			} else {
				// 遇到的是')'
				if (count == 0) {
					need++;
				} else {
					count--;
				}
			}
		}
		return count + need;
	}

	public static boolean isValid(char[] str) {
		if (str == null || str.length == 0) {
			return false;
		}
		int status = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	// s只由(和)组成
	// 求最长有效括号子串长度
    public static int longestValidParentheses(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		// dp[i] : 子串必须以i位置结尾的情况下，往左最远能扩出多长的有效区域
		int[] dp = new int[str.length];
		// dp[0] = 0; （  ）
		int pre = 0;
		int ans = 0;
		for (int i = 1; i < str.length; i++) {
			if (str[i] == ')') {
				// 当前谁和i位置的)，去配！
				pre = i - dp[i - 1] - 1;
				// 与str[i]配对的左括号的位置 pre
				if (pre >= 0 && str[pre] == '(') {
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
				}
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

}
