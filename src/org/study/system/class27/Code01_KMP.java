package org.study.system.class27;

/**
 *
 * 字符串的查找（匹配）算法
 */
public class Code01_KMP {

	public static int getIndexOf(String s1, String s2) {
		if (s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()) {
			return -1;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();

		// 主串指针
		int x = 0;

		// 子串指针
		int y = 0;
		// O(M) m <= n
		int[] next = getNextArray(str2);
		// O(N)

		// 求时间复杂度  考虑 x  和  x-y  【两个量的的最大值都只是 N 】【时间复杂度也就是 O(N)】
		while (x < str1.length && y < str2.length) {
			if (str1[x] == str2[y]) {
				x++;
				y++;
			} else if (next[y] == -1) {
				// y == 0
				// 子串在当前y位置 没有前缀信息【也就是字段已经左移到了最开头的 0 位置】
				x++;
			} else {
				// 子串向右推
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
	}

	/**
	 * 每个位置的信息，考虑的是当前位置的 前面 的字符串，前缀和后缀的最大匹配长度【前后缀都不能取到整体】
	 * 这个信息数组也就是 next 数组
	 * 人为规定  任何字符串，0位置的信息为 -1. 1位置的信息是 0 。
	 *
	 * next 数组是对 target 字符串求的
	 */
	public static int[] getNextArray(char[] str2) {
		if (str2.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[str2.length];

		// 人为规定
		next[0] = -1;
		next[1] = 0;

		// 目前在哪个位置上求next数组的值
		int i = 2;

		// 当前是哪个位置的值 在和 i-1 位置的字符比较【相等：当前的next 就是 cn+1 .不相等：cn就跳到对应 next值的 下标位置】
		int cn = 0;
		while (i < next.length) {

			// 配成功的时候
			if (str2[i - 1] == str2[cn]) {
				// 即设置了next数组的值，也修改了 cn 的值
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);
			if (getIndexOf(str, match) != str.indexOf(match)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
