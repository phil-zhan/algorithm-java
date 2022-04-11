package org.study.coding.class26;

import java.util.LinkedList;
import java.util.List;

/**
 * 本题测试链接 : https://leetcode.com/problems/expression-add-operators/
 * 3. 给定一个仅包含数字0-9的字符串和一个目标值，在数字之间添加 二元 运算符（不是一元）+、-或*，返回所有能够得到目标值的表达式。
 *    输入: num = "123", target = 6
 *    输出: ["1+2+3", "1*2*3"]
 *    示例2:
 *    输入: num = "232", target = 8
 *    输出: ["2*3+2", "2+3*2"]
 *    示例 3:
 *    输入: num = "105", target = 5
 *    输出: ["1*0+5","10-5"]
 *    示例4:
 *    输入: num = "00", target = 0
 *    输出: ["0+0", "0-0", "0*0"]
 *
 * 解法：
 * 从左到右的尝试模型
 * 来到i位置的时候，考虑i位置的前面是不添加符号、添加加号、添加减号或者是添加乘号 【从1位置开始遍历】
 *
 *
 * @since 2022-04-06 22:34:43
 */
public class Code03_ExpressionAddOperators {

	/**
	 * 最优解
	 *
	 * @since 2022-04-08 07:20:12
	 */
	public static List<String> addOperators(String num, int target) {
		List<String> ret = new LinkedList<>();
		if (num.length() == 0) {
			return ret;
		}
		// 沿途的数字拷贝和+ - * 的决定，放在path里
		char[] path = new char[num.length() * 2 - 1];
		// num -> char[]
		char[] digits = num.toCharArray();
		long n = 0;

		// 尝试0~i前缀作为第一部分
		for (int i = 0; i < digits.length; i++) {
			n = n * 10 + digits[i] - '0';
			path[i] = digits[i];

			// 后续过程
			dfs(ret, path, i + 1, 0, n, digits, i + 1, target);
			if (n == 0) {
				break;
			}
		}
		return ret;
	}


	/**
	 * char[] digits 固定参数，字符类型数组，等同于num
	 * int target 目标
	 * char[] path 之前做的决定，已经从左往右依次填写的字符在其中，可能含有'0'~'9' 与 * - +
	 * int len path[0..len-1]已经填写好，len是终止
	 *
	 * int index 字符类型数组num, 使用到了哪
	 * left -> 前面固定的部分
	 * cur -> 前一块【可能改变的部分】【因为有优先级】
	 * 【做完当前位置的决定后，记得维护这两个参数】
	 *
	 * 【这两个参数也可以不要，可以每一步都去计算表达式】
	 *
	 * 默认 left + cur ...
	 *
	 * @since 2022-04-06 22:35:14
	 */
	public static void dfs(List<String> res, char[] path, int len, 
			long left, long cur, 
			char[] num, int index, int aim) {

		// 来到了最后
		if (index == num.length) {
			if (left + cur == aim) {
				res.add(new String(path, 0, len));
			}
			return;
		}
		long n = 0;
		int j = len + 1;

		// pos ~ i
		// 这个for，是在枚举可能的前缀，也就是前面多少个数不添加符号
		for (int i = index; i < num.length; i++) {
			// 试每一个可能的前缀，作为第一个数字！
			// num[index...i] 作为第一个数字！
			// 【也就是考虑不填的情况】
			// n 就是当前的可变cur
			n = n * 10 + num[i] - '0';
			path[j++] = num[i];

			// +
			path[len] = '+';
			dfs(res, path, j, left + cur, n, num, i + 1, aim);

			// -
			path[len] = '-';
			dfs(res, path, j, left + cur, -n, num, i + 1, aim);

			// *
			path[len] = '*';
			dfs(res, path, j, left, cur * n, num, i + 1, aim);

			// index位置的数是0，只能走一遍加减乘。也就是0只能自己做第一个数。不能和其他任何数组成一个数。例如012，这是无效的表达
			if (num[index] == '0') {
				break;
			}
		}
	}

}
