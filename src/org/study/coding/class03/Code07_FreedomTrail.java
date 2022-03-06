package org.study.coding.class03;

import java.util.ArrayList;
import java.util.HashMap;

// 本题测试链接 : https://leetcode.com/problems/freedom-trail/
public class Code07_FreedomTrail {

	/**
	 * 生成一张表
	 * Map<String,List<String>>
	 * a:存在的位置有哪些
	 * b:存在的位置有哪些
	 * c:存在的位置有哪些
	 * ...
	 *
	 * 遍历目标串，来到某个字符，找到该字符存在的位置，让所有位置都去转一下，最后抓一个最优的
	 * @since 2022-03-03 07:21:30
	 */
	public static int findRotateSteps(String r, String k) {
		char[] ring = r.toCharArray();
		int N = ring.length;
		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < N; i++) {
			if (!map.containsKey(ring[i])) {
				map.put(ring[i], new ArrayList<>());
			}
			map.get(ring[i]).add(i);
		}
		char[] str = k.toCharArray();
		int M = str.length;

		// 行代表轮盘的字符
		// 列代表目标字符
		// dp[i][j] 表示轮盘指针上一步在 i 位置，要拨出 目标的 j 位置的字符 。最小的代价是什么
		int[][] dp = new int[N][M + 1];
		// hashmap
		// dp[][] == -1 : 表示没算过！
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= M; j++) {
				dp[i][j] = -1;
			}
		}
		return process(0, 0, str, map, N, dp);
	}

	/**
	 *  电话里：指针指着的上一个按键preButton
	 * 	目标里：此时要搞定哪个字符？keyIndex
	 * 	map : key 一种字符 value: 哪些位置拥有这个字符
	 * 	N: 电话大小
	 * 	f(0, 0, aim, map, N)
	 * 	返回最少的代价
	 * 	每一次的代价是指针移动到当前位置的代价加一下确认的代价
	 *
	 *
	 * 	从index开始，到搞定完所有的字符的最小代价
	 * @since 2022-03-03 07:33:18
	 */
	public static int process(int preButton, int index, char[] str, HashMap<Character, ArrayList<Integer>> map, int N,int[][] dp) {

		// 傻缓存
		if (dp[preButton][index] != -1) {
			return dp[preButton][index];
		}
		int ans = Integer.MAX_VALUE;
		if (index == str.length) {
			ans = 0;
		} else {
			// 还有字符需要搞定呢！
			char cur = str[index];
			ArrayList<Integer> nextPositions = map.get(cur);
			for (int next : nextPositions) {

				// next 就是当前的可选位置
				int cost = dial(preButton, next, N) + 1 + process(next, index + 1, str, map, N, dp);
				ans = Math.min(ans, cost);
			}
		}
		dp[preButton][index] = ans;
		return ans;
	}

	public static int dial(int i1, int i2, int size) {
		return Math.min(Math.abs(i1 - i2), Math.min(i1, i2) + size - Math.max(i1, i2));
	}

}
