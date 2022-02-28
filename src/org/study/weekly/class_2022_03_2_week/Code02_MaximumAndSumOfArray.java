package org.study.weekly.class_2022_03_2_week;

// 测试链接 : https://leetcode.com/problems/maximum-and-sum-of-array/
// 1, 状态压缩动态规划
// 2, km算法
// 3, 最小费用最大流(以后安排)
public class Code02_MaximumAndSumOfArray {

	public static int maximumANDSum1(int[] arr, int m) {
		int status = (int) Math.pow(3, m) - 1;
		int[] dp = new int[status + 1];
		return process1(arr, 0, status, m, dp);
	}

	public static int process1(int[] arr, int i, int status, int m, int[] dp) {
		if (dp[status] != 0) {
			return dp[status];
		}
		if (i == arr.length) {
			return 0;
		}
		int ans = 0;
		for (int j = 1, bit = 1; j <= m; j++, bit *= 3) {
			if ((status / bit) % 3 > 0) {
				ans = Math.max(ans, (arr[i] & j) + process1(arr, i + 1, status - bit, m, dp));
			}
		}
		dp[status] = ans;
		return ans;
	}

	public static int maximumANDSum2(int[] arr, int m) {
		m <<= 1;
		int[][] graph = new int[m][m];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0, num = 1; j < m; num++, j += 2) {
				graph[i][j] = arr[i] & num;
				graph[i][j + 1] = arr[i] & num;
			}
		}
		return km(graph);
	}

	public static int km(int[][] graph) {
		int N = graph.length;
		int[] match = new int[N];
		int[] lx = new int[N];
		int[] ly = new int[N];
		boolean[] x = new boolean[N];
		boolean[] y = new boolean[N];
		int[] slack = new int[N];
		int invalid = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			match[i] = -1;
			lx[i] = -invalid;
			for (int j = 0; j < N; j++) {
				lx[i] = Math.max(lx[i], graph[i][j]);
			}
			ly[i] = 0;
		}
		for (int t = 0; t < N; t++) {
			clean(x);
			clean(y);
			for (int i = 0; i < N; i++) {
				slack[i] = invalid;
			}
			while (!dfs(t, x, y, lx, ly, match, slack, graph)) {
				int d = invalid;
				for (int i = 0; i < N; i++) {
					if (!y[i] && slack[i] < d) {
						d = slack[i];
					}
				}
				for (int i = 0; i < N; i++) {
					if (x[i]) {
						lx[i] = lx[i] - d;
						x[i] = false;
					}
					if (y[i]) {
						ly[i] = ly[i] + d;
						y[i] = false;
					}
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += (lx[i] + ly[i]);
		}
		return ans;
	}

	public static void clean(boolean[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = false;
		}
	}

	public static boolean dfs(int t, boolean[] x, boolean[] y, int[] lx, int[] ly, int[] match, int[] slack,
			int[][] map) {
		int N = map.length;
		x[t] = true;
		for (int u = 0; u < N; u++) {
			int d = lx[t] + ly[u] - map[t][u];
			if (!y[u] && d == 0) {
				y[u] = true;
				if (match[u] == -1 || dfs(match[u], x, y, lx, ly, match, slack, map)) {
					match[u] = t;
					return true;
				}
			} else {
				slack[u] = Math.min(slack[u], d);
			}
		}
		return false;
	}

}
