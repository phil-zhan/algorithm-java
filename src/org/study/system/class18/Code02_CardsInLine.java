package org.study.system.class18;

public class Code02_CardsInLine {

	// 根据规则，返回获胜者的分数
	public static int win1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		// 先手的姿态获得的分数
		int first = f1(arr, 0, arr.length - 1);

		// 后手的姿态获得的分数
		int second = g1(arr, 0, arr.length - 1);
		return Math.max(first, second);
	}

	// arr[L..R]，先手获得的最好分数返回
	public static int f1(int[] arr, int L, int R) {
		if (L == R) {
			return arr[L];
		}
		int p1 = arr[L] + g1(arr, L + 1, R);
		int p2 = arr[R] + g1(arr, L, R - 1);
		return Math.max(p1, p2);
	}

	// // arr[L..R]，后手获得的最好分数返回
	public static int g1(int[] arr, int L, int R) {
		if (L == R) {
			return 0;
		}

		// 对手拿走了L位置的数。剩下的最优
		int p1 = f1(arr, L + 1, R);

		// 对手拿走了R位置的数。剩下的最优
		int p2 = f1(arr, L, R - 1);


		// 为什么是最小值？？因为当前是后手。对手肯定会先选大的。把小的留给你。
		// 你能算出来，别人也能算出来
		return Math.min(p1, p2);
	}

	public static int win2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;

		// 第一个维度是 L 的变化范围
		// 第二个维度是 R 的变化范围
		int[][] fmap = new int[N][N];
		int[][] gmap = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				fmap[i][j] = -1;
				gmap[i][j] = -1;
			}
		}
		int first = f2(arr, 0, arr.length - 1, fmap, gmap);
		int second = g2(arr, 0, arr.length - 1, fmap, gmap);
		return Math.max(first, second);
	}

	// arr[L..R]，先手获得的最好分数返回
	public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
		if (fmap[L][R] != -1) {
			return fmap[L][R];
		}
		int ans = 0;
		if (L == R) {
			ans = arr[L];
		} else {
			int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
			int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
			ans = Math.max(p1, p2);
		}
		fmap[L][R] = ans;
		return ans;
	}

	// // arr[L..R]，后手获得的最好分数返回
	public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
		if (gmap[L][R] != -1) {
			return gmap[L][R];
		}
		int ans = 0;
		if (L != R) {

			// 对手拿走了L位置的数
			int p1 = f2(arr, L + 1, R, fmap, gmap);

			// 对手拿走了R位置的数
			int p2 = f2(arr, L, R - 1, fmap, gmap);
			ans = Math.min(p1, p2);
		}
		gmap[L][R] = ans;
		return ans;
	}

	public static int win3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[][] fmap = new int[N][N];
		int[][] gmap = new int[N][N];

		// 设置f表对角线的值。g表的对角线初始化后本身就是0.不用设置
		for (int i = 0; i < N; i++) {
			fmap[i][i] = arr[i];
		}


		// 只考虑上三角。L肯定是大于R的
		for (int startCol = 1; startCol < N; startCol++) {
			int L = 0;
			int R = startCol;
			while (R < N) {

				// f表中的当前位置 依赖于 g表中对应位置的左边元素和正下面的位置
				fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
				gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);

				// 闲着斜率为45度的斜线。从左上到右下
				L++;
				R++;
			}
		}
		return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
	}

	public static void main(String[] args) {
		int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
		System.out.println(win1(arr));
		System.out.println(win2(arr));
		System.out.println(win3(arr));

	}

}
