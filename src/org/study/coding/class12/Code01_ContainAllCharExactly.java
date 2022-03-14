package org.study.coding.class12;

import java.util.Arrays;

/**
 * 本题测试链接 : https://leetcode.com/problems/permutation-in-string/
 *
 * 给定长度为m的字符串aim，以及一个长度为n的字符串str，问能否在str中找到一个长度为m的连续子串，
 * 使得这个子串刚好由aim的m个字符组成，顺序无所谓，返回任意满足条件的一个子串的起始位置，未找到返回-1
 *
 * 也就是所求的 str 的子串，得和 aim 的种类要一样【不能多也不能少】。每个字符出现的次数也要一样
 * 解法：窗口+HashMap
 * 遍历aim。就能知道aim的词频
 * 维持一个长度为 aim长度 的窗口
 * 在str上，每次滑动窗口，都去检查，欠 aim 多少个字符。，窗口里面有的字符，被认为是还掉的字符
 * 每次都全检查一下欠的字符，
 * 如果欠的字符减掉当前字符，数量是大于等于0的，就认为是有效的还款。欠账总数减1
 * 如果欠的字符减掉当前字符，数量是小于0的，就认为是无效的还款，欠账总数不变
 * 当来到某个窗口时，欠账总数为0.就认为当前窗口内的字符是aim的变形。否则就不是 aim 的变形，
 * 去到下一个窗口。
 * 由于是固定窗口，每次都向右扩一个。同时左边缩一个。
 * 此时右边的字符减1【进入窗口，还帐（考虑是否是有效的还账）】
 * 此时左边的字符加1【进入窗口，欠帐】
 * 加上当前字符，如果是大于0，说明是有效的欠账。小于等于0，视为无效的欠账【根据有效和无效去更新总的欠账数】
 * 根据总的欠账数是否是变形词
 *
 *
 * 当某个窗口能将欠账还清的时候
 *
 *
 *
 *
 * @since 2022-03-13 11:03:18
 */
public class Code01_ContainAllCharExactly {

	/**
	 * leetcode
	 * @since 2022-03-13 02:27:59
	 */
	public boolean checkInclusion(String s1, String s2) {

		return containExactly3(s2,s1) != -1;
	}



	public static int containExactly1(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] aim = a.toCharArray();
		Arrays.sort(aim);
		String aimSort = String.valueOf(aim);
		for (int L = 0; L < s.length(); L++) {
			for (int R = L; R < s.length(); R++) {
				char[] cur = s.substring(L, R + 1).toCharArray();
				Arrays.sort(cur);
				String curSort = String.valueOf(cur);
				if (curSort.equals(aimSort)) {
					return L;
				}
			}
		}
		return -1;
	}

	public static int containExactly2(String s, String a) {
		if (s == null || a == null || s.length() < a.length()) {
			return -1;
		}
		char[] str = s.toCharArray();
		char[] aim = a.toCharArray();
		for (int L = 0; L <= str.length - aim.length; L++) {
			if (isCountEqual(str, L, aim)) {
				return L;
			}
		}
		return -1;
	}

	public static boolean isCountEqual(char[] str, int L, char[] aim) {
		int[] count = new int[256];
		for (int i = 0; i < aim.length; i++) {
			count[aim[i]]++;
		}
		for (int i = 0; i < aim.length; i++) {
			if (count[str[L + i]]-- == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 最优解
	 * @since 2022-03-13 01:47:10
	 */
	public static int containExactly3(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() < s2.length()) {
			return -1;
		}
		char[] str2 = s2.toCharArray();
		int M = str2.length;

		// 欠账表
		int[] count = new int[256];
		for (int i = 0; i < M; i++) {
			count[str2[i]]++;
		}
		// 当前还欠的数量
		int all = M;


		char[] str1 = s1.toCharArray();
		int R = 0;
		// 0~M-1
		for (; R < M; R++) {
			// 最早的M个字符，让其窗口初步形成
			if (count[str1[R]]-- > 0) {
				all--;
			}
		}
		// 窗口初步形成了，并没有判断有效无效，决定下一个位置一上来判断【初步窗口，all肯定不会等于0.也就是不会形成有效变形串】
		// 接下来的过程，窗口右进一个，左吐一个
		// 每次都是在一个位置的时候，判断前一个窗口
		// 最后一个窗口放在循环外面
		for (; R < str1.length; R++) {
			if (all == 0) {
				// R-1
				// 返回的是变形词的首位置
				return R - M;
			}
			if (count[str1[R]]-- > 0) {
				all--;
			}
			if (count[str1[R - M]]++ >= 0) {
				all++;
			}
		}

		// 记得处理最后一个窗口
		return all == 0 ? R - M : -1;
	}

	// for test
	public static String getRandomString(int possibilities, int maxSize) {
		char[] ans = new char[(int) (Math.random() * maxSize) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strMaxSize = 20;
		int aimMaxSize = 10;
		int testTimes = 500000;
		System.out.println("test begin, test time : " + testTimes);
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strMaxSize);
			String aim = getRandomString(possibilities, aimMaxSize);
			int ans1 = containExactly1(str, aim);
			int ans2 = containExactly2(str, aim);
			int ans3 = containExactly3(str, aim);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("Oops!");
				System.out.println(str);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("test finish");

	}

}
