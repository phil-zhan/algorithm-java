package org.study.coding.class28;

/**
 * 外观数列
 * 给定一个正整数 n ，输出外观数列的第 n 项。
 *
 * 「外观数列」是一个整数序列，从数字 1 开始，序列中的每一项都是对前一项的描述。
 *
 * 你可以将其视作是由递归公式定义的数字字符串序列：
 *
 * countAndSay(1) = "1"
 * countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
 *
 *
 *
 * @since 2022-04-12 08:05:47
 */
public class Problem_0038_CountAndSay {

	public static String countAndSay(int n) {
		if (n < 1) {
			return "";
		}
		if (n == 1) {
			return "1";
		}
		char[] last = countAndSay(n - 1).toCharArray();
		StringBuilder ans = new StringBuilder();
		int times = 1;
		for (int i = 1; i < last.length; i++) {
			if (last[i - 1] == last[i]) {
				times++;
			} else {
				ans.append(String.valueOf(times));
				ans.append(String.valueOf(last[i - 1]));
				times = 1;
			}
		}
		ans.append(String.valueOf(times));
		ans.append(String.valueOf(last[last.length - 1]));
		return ans.toString();
	}

	public static void main(String[] args) {
		System.out.println(countAndSay(20));
	}

}
