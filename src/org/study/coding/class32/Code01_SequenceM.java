package org.study.coding.class32;

import java.util.Arrays;

/**
 * 快手面试题
 *
 * 给定一个数组arr，arr[i] = j，表示第i号试题的难度为j。给定一个非负数M
 * 想出一张卷子，对于任何相邻的两道题目，前一题的难度不能超过后一题的难度+M
 * 返回所有可能的卷子种数
 *
 * 题意：
 * 任何两个相邻的题目，都需要满足 *前一题的难度不能超过后一题的难度+M*
 *
 * 解法：
 * 使用IndexTree。【在一个数组中，下标从1开始。add(x,3): 在x位置上加一个3。 sum(x): 计算1...x 的累加和】【两个方法的时间复杂度都能做到logN。线段树也能实现同样的效果和时间复杂度。但是线段树的代码量比较重】
 *
 * 考虑从左往右的尝试模型。
 * 先将所有的题目按照难度从小到大排序。【如果当前位置之前出现违法的相邻题目。要想让整个卷子合法。就要让当前的题目难度大于等于前面的卷子难度。不然无法让之前的难度变合法】
 * 将0...i 所有的题目都使用完，有多少合法的卷子。
 * 假设 0...i 有 m 套合法的卷子。现在来到了一个数x。 看看能整体增值出多少套来
 * 1）因为是按照从小到大排序的。新来的数x，放在每一套卷子的后面。不会改变每一套卷子的合法性
 * 2）新来的卷子往前插。可以插在大于等于 x-M 的前面【可以保证 x >= x-M+M】 。【M是题目的规定】。可以插的位置，到当前的x的位置，有多少个数，就能形成多少套有效的新卷子
 * 【对于 0...i 所形成的 z 套卷子。数字种类和对应数字的个数都是一样。只是排列不一样】
 * 【也就是说，能得到一套卷子增值的数量，其他套卷子增值的数量也都是一样的】
 * 【两种情况求和。就是当前总的卷子数】
 * 【排列不一样的卷子，也算不一样的】
 *
 * 需要考虑，来到某个位置的时候，之前的所有数字中，大于等于某个值的个数有多少。这个操作要是快的话，整体就简单了。
 * 用indexTree实现
 * 假设所有的值的取值范围是从1...100
 * 就做出一个int类型的数组count。【表示count[i]=j :值为i的数的个数有j个 】
 * 来到某个数的时候x【x是具体的值，不是下标】，将count[x]++ 【将原始数组的具体值做为count的下标】
 * 这样在count上，查某个前缀上的累加和，就能得到小于等于某个数的个数有多少
 * 【sum[x]】：就是查count数组中[1...x]的前缀和。查到小于等于x的个数有多少，再查一次小于等于当前数y的个数有多少， 那么就能得到x...y的个数有多少。
 *
 * 当前的卷子个数计算完之后，记得将当前的数也加到indexTree 中去。方便后面统计数字
 *
 * @since 2022-04-19 21:59:46
 */
public class Code01_SequenceM {

	/**
	 * 纯暴力方法，生成所有排列，一个一个验证
	 *
	 * @since 2022-04-19 22:13:07
	 */
	public static int ways1(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr, 0, m);
	}

	public static int process(int[] arr, int index, int m) {
		if (index == arr.length) {
			for (int i = 1; i < index; i++) {
				if (arr[i - 1] > arr[i] + m) {
					return 0;
				}
			}
			return 1;
		}
		int ans = 0;
		for (int i = index; i < arr.length; i++) {
			swap(arr, index, i);
			ans += process(arr, index + 1, m);
			swap(arr, index, i);
		}
		return ans;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	/**
	 * 时间复杂度O(N * logN)
	 * 从左往右的动态规划 + 范围上二分
	 *
	 * @since 2022-04-19 22:00:13
	 */
	public static int ways2(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr);
		int all = 1;
		for (int i = 1; i < arr.length; i++) {
			all = all * (num(arr, i - 1, arr[i] - m) + 1);
		}
		return all;
	}

	/**
	 * arr[0..r]上返回>=t的数有几个, 二分的方法
	 * 找到 >=t 最左的位置a, 然后返回r - a + 1就是个数
	 *
	 * @since 2022-04-19 22:00:24
	 */
	public static int num(int[] arr, int r, int t) {
		int i = 0;
		int j = r;
		int m = 0;
		int a = r + 1;
		while (i <= j) {
			m = (i + j) / 2;
			if (arr[m] >= t) {
				a = m;
				j = m - 1;
			} else {
				i = m + 1;
			}
		}
		return r - a + 1;
	}

	/**
	 * 时间复杂度O(N * logV)
	 * 从左往右的动态规划 + IndexTree
	 *
	 * @since 2022-04-19 22:00:52
	 */
	public static int ways3(int[] arr, int m) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int num : arr) {
			max = Math.max(max, num);
			min = Math.min(min, num);
		}
		IndexTree itree = new IndexTree(max - min + 2);
		Arrays.sort(arr);
		int a = 0;
		int b = 0;

		// 当前的卷子数量。只有0位置。就是一套
		int all = 1;

		// 来到某个数，先计算位置。indexTree的下标识从 1开始的。所以要加1
		// 减去最小值，是为了节省空间。让count【也就是indexTree的辅助数组】的下标从1开始。
		itree.add(arr[0] - min + 1, 1);

		// 从1开始遍历
		for (int i = 1; i < arr.length; i++) {

			// 计算下标
			a = arr[i] - min + 1;

			// 每一套能增值的卷子数量
			b = i - (a - m - 1 >= 1 ? itree.sum(a - m - 1) : 0);

			// 两种情况求和
			all = all * (b + 1);

			// a位置的数加1
			itree.add(a, 1);
		}
		return all;
	}

	/**
	 * 注意开始下标是1，不是0
	 *
	 * @since 2022-04-19 22:00:44
	 */
	public static class IndexTree {

		private int[] tree;
		private int N;

		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			while (index > 0) {
				ret += tree[index];
				index -= index & -index;
			}
			return ret;
		}

		public void add(int index, int d) {
			while (index <= N) {
				tree[index] += d;
				index += index & -index;
			}
		}
	}

	// 为了测试
	public static int[] randomArray(int len, int value) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * (value + 1));
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int value = 20;
		int testTimes = 1000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * (N + 1));
			int[] arr = randomArray(len, value);
			int m = (int) (Math.random() * (value + 1));
			int ans1 = ways1(arr, m);
			int ans2 = ways2(arr, m);
			int ans3 = ways3(arr, m);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了!");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
			}
		}
		System.out.println("测试结束");
	}

}
