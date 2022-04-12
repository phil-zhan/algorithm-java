package org.study.coding.class28;

import java.util.ArrayList;
import java.util.List;

/**
 * 电话号码的字母组合
 * 手机键盘9建
 *
 * 解法：
 * 深度优先遍历
 * 来到摸个数字的时候，枚举其所有可能的字符。然后深度优先去考虑下一层。到底的时候，收集答案
 *
 * @since 2022-04-11 23:09:39
 */

public class Problem_0017_LetterCombinationsOfAPhoneNumber {


	@SuppressWarnings("all")
	public static char[][] phone = { 
			{ 'a', 'b', 'c' }, 		// 2    0
			{ 'd', 'e', 'f' }, 		// 3    1
			{ 'g', 'h', 'i' }, 		// 4    2
			{ 'j', 'k', 'l' }, 		// 5    3
			{ 'm', 'n', 'o' }, 		// 6
			{ 'p', 'q', 'r', 's' }, // 7 
			{ 't', 'u', 'v' },   	// 8
			{ 'w', 'x', 'y', 'z' }, // 9
	};

	// "23"
	public static List<String> letterCombinations(String digits) {
		List<String> ans = new ArrayList<>();
		if (digits == null || digits.length() == 0) {
			return ans;
		}
		char[] str = digits.toCharArray();
		char[] path = new char[str.length];
		process(str, 0, path, ans);
		return ans;
	}

	/**
	 * 当前来到了 index 位置 。
	 * index 之前的结果都存在 path 里面
	 * ans 是用来收集答案的
	 *
	 * @since 2022-04-11 23:14:44
	 */
	public static void process(char[] str, int index, char[] path, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(path));
		} else {
			char[] candidates = phone[str[index] - '2'];
			for (char cur : candidates) {
				path[index] = cur;
				process(str, index + 1, path, ans);
			}
		}
	}

}
