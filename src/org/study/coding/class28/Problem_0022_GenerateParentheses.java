package org.study.coding.class28;

import java.util.ArrayList;
import java.util.List;

/**
 * 括号生成
 *
 *
 * @since 2022-04-11 23:48:39
 */
public class Problem_0022_GenerateParentheses {

	public static List<String> generateParenthesis(int n) {
		char[] path = new char[n << 1];
		List<String> ans = new ArrayList<>();
		process(path, 0, 0, n, ans);
		return ans;
	}



	/**
	 * path 做的决定  path[0....index-1]做完决定的！
	 * path[index.....] 还没做决定，当前轮到index位置做决定！
	 * leftMinusRight：之前的决定中，左括号比右括号多多少【也就是之前的决定中，左括号的数量减去右括号的数量
	 * leftRest：还剩下多少左括号.
	 *
	 * 保证上游的有效性，下游不需要验证了，可以直接收集答案
	 *
	 * @since 2022-04-12 07:18:16
	 */
	public static void process(char[] path, int index, int leftMinusRight, int leftRest, List<String> ans) {
		if (index == path.length) {
			ans.add(String.valueOf(path));
		} else {
			// index (   )
			if (leftRest > 0) {
				// 还剩下多少左括号可用，当前位置可以给左括号

				path[index] = '(';
				process(path, index + 1, leftMinusRight + 1, leftRest - 1, ans);
			}
			if (leftMinusRight > 0) {
				// 之前的决定中，左括号比右括号多多少【也就是之前的决定中，左括号的数量减去右括号的数量】

				path[index] = ')';
				process(path, index + 1, leftMinusRight - 1, leftRest, ans);
			}
		}
	}

	/**
	 * 不剪枝的做法
	 * 从左到右的尝试
	 * 来到index的时候，index可以是左括号也可以是右括号
	 * 在最后收集答案的时候，需要验证是否有效
	 *
	 * @since 2022-04-12 07:16:31
	 */
	public static List<String> generateParenthesis2(int n) {
		char[] path = new char[n << 1];
		List<String> ans = new ArrayList<>();
		process2(path, 0, ans);
		return ans;
	}

	public static void process2(char[] path, int index, List<String> ans) {
		if (index == path.length) {
			if (isValid(path)) {
				ans.add(String.valueOf(path));
			}
		} else {
			path[index] = '(';
			process2(path, index + 1, ans);

			path[index] = ')';
			process2(path, index + 1, ans);
		}
	}

	public static boolean isValid(char[] path) {
		int count = 0;
		for (char cha : path) {
			if (cha == '(') {
				count++;
			} else {
				count--;
			}
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

}
