package org.study.coding.class01;

import java.util.Arrays;

public class Code01_CordCoverMaxPoint {

	/**
	 * 解法1：
	 * 考虑以每个位置为绳子的终点，其前面能覆盖多少数
	 * @since 2022-02-28 10:01:56
	 */
	public static int maxPoint1(int[] arr, int L) {
		int res = 1;
		for (int i = 0; i < arr.length; i++) {
			int nearest = nearestIndex(arr, i, arr[i] - L);
			res = Math.max(res, i - nearest + 1);
		}
		return res;
	}

	/**
	 * 利用二分法去处理。找到【0...R】范围上。找到当前线段能覆盖的最左 的下标
	 * @since 2022-02-28 10:02:49
	 */
	public static int nearestIndex(int[] arr, int R, int value) {
		int L = 0;
		int index = R;
		while (L <= R) {
			int mid = L + ((R - L) >> 1);
			if (arr[mid] >= value) {
				index = mid;
				R = mid - 1;
			} else {
				L = mid + 1;
			}
		}
		return index;
	}

	/**
	 * 解法2：
	 * 利用固定绳子长度的窗口去滑动处理。窗口不会退。O(N)
	 * @since 2022-02-28 10:04:56
	 */
	public static int maxPoint2(int[] arr, int L) {

		// 下标指针
		int left = 0;
		int right = 0;
		int N = arr.length;
		int max = 0;
		while (left <= right) {
			while (right < N && arr[right] - arr[left] <= L) {
				right++;
			}
			max = Math.max(max, right - (left++));
		}
		return max;
	}

	// for test
	public static int test(int[] arr, int L) {
		int max = 0;
		for (int i = 0; i < arr.length; i++) {
			int pre = i - 1;
			while (pre >= 0 && arr[i] - arr[pre] <= L) {
				pre--;
			}
			max = Math.max(max, i - pre);
		}
		return max;
	}

	// for test
	public static int[] generateArray(int len, int max) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * max);
		}
		Arrays.sort(ans);
		return ans;
	}

	public static void main(String[] args) {
		int len = 100;
		int max = 1000;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int L = (int) (Math.random() * max);
			int[] arr = generateArray(len, max);
			int ans1 = maxPoint1(arr, L);
			int ans2 = maxPoint2(arr, L);
			int ans3 = test(arr, L);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
