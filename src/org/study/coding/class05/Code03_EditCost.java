package org.study.coding.class05;

public class Code03_EditCost {

	/**
	 * https://leetcode.com/problems/edit-distance/
	 * 本题是 LeetCode的加强版
	 * LeetCode认为所有的代价都是1.
	 *
	 * 要从 字符串s1 变换成 字符串s2
	 * 增加一个字符的成本是 ic
	 * 删除一个字符的成本是 dc
	 * 替换一个字符的成本是 rc
	 * 请返回最小的变换（编辑）成本
	 *
	 * 编辑距离可以描述两个字符串的相似程度，在工程上，用在搜索领域的模糊搜索等。
	 *
	 * 本题利用样本对应模型。
	 * str1做行，str2做列
	 * dp[i][j] : 表示str1前缀i个字符，str2前缀取 j个字符。从str1编辑成str2的编辑距离是多少
	 *
	 * @since 2022-03-05 09:21:26
	 */
	public static int minCost1(String s1, String s2, int ic, int dc, int rc) {
		if (s1 == null || s2 == null) {
			return 0;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length + 1;
		int M = str2.length + 1;
		int[][] dp = new int[N][M];

		// 从空串编辑到空，成本是0
		// dp[0][0] = 0

		// 第一列
		// str2 是空。要从str1变成str2，只能删除
		for (int i = 1; i < N; i++) {
			dp[i][0] = dc * i;
		}

		// 第一行
		// str1是空，要变成str2，只能添加
		for (int j = 1; j < M; j++) {
			dp[0][j] = ic * j;
		}

		// 普遍位置
		// 1) str1的当第 i 位置是多余的，让str1的前面去搞定str2。删除str1的i字符
		// 2) str1的当前 整体先变成str2的前面除j位置的所有字符，然后再在str2上增加一个 j 字符
		// 3) 如果str1[i] == str2[j] 那么str1的前面i-1个字符去搞定str2的j-1个字符就行
		// 4) 如果str1[i] != str2[j] 那么str1的前面i-1个字符去搞定str2的j-1个字符就行,最后一个字符替换
		// 不再有其他的可能
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {

				// 3)和4）的整合
				dp[i][j] = dp[i - 1][j - 1] + (str1[i - 1] == str2[j - 1] ? 0 : rc);

				// 2）插如代价
				dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);

				// 1) 删除代价
				dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
			}
		}
		return dp[N - 1][M - 1];
	}

	/**
	 * 空间压缩
	 * @since 2022-03-05 10:02:48
	 */
	public static int minCost2(String str1, String str2, int ic, int dc, int rc) {
		if (str1 == null || str2 == null) {
			return 0;
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
		char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
		if (chs1.length < chs2.length) {
			int tmp = ic;
			ic = dc;
			dc = tmp;
		}
		int[] dp = new int[shorts.length + 1];
		for (int i = 1; i <= shorts.length; i++) {
			dp[i] = ic * i;
		}
		for (int i = 1; i <= longs.length; i++) {
			int pre = dp[0];
			dp[0] = dc * i;
			for (int j = 1; j <= shorts.length; j++) {
				int tmp = dp[j];
				if (longs[i - 1] == shorts[j - 1]) {
					dp[j] = pre;
				} else {
					dp[j] = pre + rc;
				}
				dp[j] = Math.min(dp[j], dp[j - 1] + ic);
				dp[j] = Math.min(dp[j], tmp + dc);
				pre = tmp;
			}
		}
		return dp[shorts.length];
	}

	public static void main(String[] args) {
		String str1 = "ab12cd3";
		String str2 = "abcdf";
		System.out.println(minCost1(str1, str2, 5, 3, 2));
		System.out.println(minCost2(str1, str2, 5, 3, 2));

		str1 = "abcdf";
		str2 = "ab12cd3";
		System.out.println(minCost1(str1, str2, 3, 2, 4));
		System.out.println(minCost2(str1, str2, 3, 2, 4));

		str1 = "";
		str2 = "ab12cd3";
		System.out.println(minCost1(str1, str2, 1, 7, 5));
		System.out.println(minCost2(str1, str2, 1, 7, 5));

		str1 = "abcdf";
		str2 = "";
		System.out.println(minCost1(str1, str2, 2, 9, 8));
		System.out.println(minCost2(str1, str2, 2, 9, 8));

	}

}
