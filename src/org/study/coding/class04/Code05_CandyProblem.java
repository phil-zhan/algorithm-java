package org.study.coding.class04;

// 测试链接 : https://leetcode.com/problems/candy/
public class Code05_CandyProblem {

	/**
	 * 这是原问题的优良解
	 * 时间复杂度O(N)，额外空间复杂度O(N)
	 * 贪心 + 预处理结构
	 *
	 * 准备一个left数组。代表左边的坡度。【left[i],表示从左往右的情况下，i 号孩子最少需要多少糖】
	 * 从左往右，当前位置比左边的分数高，就+1个糖，不再比左边的大，就减1. 最低为1
	 *
	 * 准备一个 right 数组. 代表右边的坡度【right[i],表示从右往左的情况下，i 号孩子最少需要多少糖】
	 * 从右往左，当前位置比右边的分数高，就+1个糖，不再比右边的大，就减1. 最低为1
	 *
	 * 每个孩子的分糖数量，就是 Math.max(left[i],right[i])
	 * 因为左边和右边以较大坡为准。因为每个孩子要同时满足左右两边
	 *
	 * 进阶：在原来要求的基础上，增加一个要求，相邻的孩子间如果分数一样，分的糖果数必须一样，返回至少需要准备多少颗糖果
	 * 在原来的左右数组的基础上
	 * 重新设计左右数组
	 *
	 * 左数组
	 * 1）当前位置的分数比左边位置的高，就加1
	 * 2）当前位置的分数和左边位置的一样高，就继承
	 * 3）当前位置的分数比左边位置的一样低，就归1
	 *
	 * 右数组也类似
	 * 1）当前位置的分数比右边位置的高，就加1
	 * 2）当前位置的分数和右边位置的一样高，就继承
	 * 3）当前位置的分数比右边位置的一样低，就归1
	 *
	 * 每个位置还是取左右两种情况下的最大值
	 *
	 * @since 2022-03-03 11:09:19
	 */

	public static int candy1(int[] ratings) {
		if (ratings == null || ratings.length == 0) {
			return 0;
		}
		int N = ratings.length;
		int[] left = new int[N];
		for (int i = 1; i < N; i++) {
			if (ratings[i - 1] < ratings[i]) {
				left[i] = left[i - 1] + 1;
			}
		}
		int[] right = new int[N];
		for (int i = N - 2; i >= 0; i--) {
			if (ratings[i] > ratings[i + 1]) {
				right[i] = right[i + 1] + 1;
			}
		}
		int ans = 0;
		for (int i = 0; i < N; i++) {
			ans += Math.max(left[i], right[i]);
		}
		return ans + N;
	}


	/**
	 * // 这是原问题空间优化后的解
	 * // 时间复杂度O(N)，额外空间复杂度O(1)
	 *
	 * 优化方案：
	 * 一组一族的处理
	 * 每一个相邻的上坡和下坡组成一组
	 *
	 * 找到一组坡度以后。以最大的那个坡度为准，去给这一组孩子分糖
	 * 有相等的情况会比较麻烦。遇到相等的时候，认为上坡断掉，下坡遇到相等的情况也断掉
	 *
	 *
	 * 战略性性放弃
	 * @since 2022-03-03 11:19:42
	 */
	public static int candy2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int index = nextMinIndex2(arr, 0);
		int res = rightCands(arr, 0, index++);
		int lbase = 1;
		int next = 0;
		int rcands = 0;
		int rbase = 0;
		while (index != arr.length) {
			if (arr[index] > arr[index - 1]) {
				res += ++lbase;
				index++;
			} else if (arr[index] < arr[index - 1]) {
				next = nextMinIndex2(arr, index - 1);
				rcands = rightCands(arr, index - 1, next++);
				rbase = next - index + 1;
				res += rcands + (rbase > lbase ? -lbase : -rbase);
				lbase = 1;
				index = next;
			} else {
				res += 1;
				lbase = 1;
				index++;
			}
		}
		return res;
	}

	public static int nextMinIndex2(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] <= arr[i + 1]) {
				return i;
			}
		}
		return arr.length - 1;
	}

	public static int rightCands(int[] arr, int left, int right) {
		int n = right - left + 1;
		return n + n * (n - 1) / 2;
	}

	// 这是进阶问题的最优解，不要提交这个
	// 时间复杂度O(N), 额外空间复杂度O(1)
	public static int candy3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int index = nextMinIndex3(arr, 0);
		int[] data = rightCandsAndBase(arr, 0, index++);
		int res = data[0];
		int lbase = 1;
		int same = 1;
		int next = 0;
		while (index != arr.length) {
			if (arr[index] > arr[index - 1]) {
				res += ++lbase;
				same = 1;
				index++;
			} else if (arr[index] < arr[index - 1]) {
				next = nextMinIndex3(arr, index - 1);
				data = rightCandsAndBase(arr, index - 1, next++);
				if (data[1] <= lbase) {
					res += data[0] - data[1];
				} else {
					res += -lbase * same + data[0] - data[1] + data[1] * same;
				}
				index = next;
				lbase = 1;
				same = 1;
			} else {
				res += lbase;
				same++;
				index++;
			}
		}
		return res;
	}

	public static int nextMinIndex3(int[] arr, int start) {
		for (int i = start; i != arr.length - 1; i++) {
			if (arr[i] < arr[i + 1]) {
				return i;
			}
		}
		return arr.length - 1;
	}

	public static int[] rightCandsAndBase(int[] arr, int left, int right) {
		int base = 1;
		int cands = 1;
		for (int i = right - 1; i >= left; i--) {
			if (arr[i] == arr[i + 1]) {
				cands += base;
			} else {
				cands += ++base;
			}
		}
		return new int[] { cands, base };
	}

	public static void main(String[] args) {
		int[] test1 = { 3, 0, 5, 5, 4, 4, 0 };
		System.out.println(candy2(test1));

		int[] test2 = { 3, 0, 5, 5, 4, 4, 0 };
		System.out.println(candy3(test2));
	}

}
