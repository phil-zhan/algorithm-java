package org.study.coding.class08;

/**
 * 给定n个非负整数a1，a2，...an，每个数代表坐标中的一个点(i,ai)。在坐标内画n条垂直线
 * 垂直线i的两个端点分别为(i,ai)和(i, 0)，找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水
 * 本题测试链接 : https://leetcode.com/problems/container-with-most-water/
 * @since 2022-03-09 07:18:10
 */
public class Code02_ContainerWithMostWater {

	// 暴力解
	public static int maxArea1(int[] h) {
		int max = 0;
		int N = h.length;

		// h[i]
		for (int i = 0; i < N; i++) {

			// h[j]
			for (int j = i + 1; j < N; j++) {
				max = Math.max(max, Math.min(h[i], h[j]) * (j - i));
			}
		}
		return max;
	}

	/**
	 * 利用左右指针，
	 * L指向0位置
	 * R指向最后一个位置
	 * arr[L] 和 arr[R],谁小谁做高，计算出一个面积，去和答案PK
	 * 谁小就结算谁，如果结算的是L，结算后，L右移。如果结算的是R，结算后，R左移。
	 *
	 * 因为是谁小结算谁。当前的L位置或许能推的比R位置远。但是那个更远位置的答案，在前面就结算过了
	 * 【能推到R位置，那么R位置的右边的数，肯定在当前L的左边，存在有比他们都大的数，才能滑动到向左R】
	 *
	 * 一样大的情况，选谁都行
	 *
	 * 我们只关注，会不会把答案推高的可能性。不是求出每个数的准确答案
	 *
	 * @since 2022-03-09 07:23:50
	 */
	public static int maxArea2(int[] h) {
		int max = 0;
		int l = 0;
		int r = h.length - 1;
		while (l < r) {
			max = Math.max(max, Math.min(h[l], h[r]) * (r - l));
			if (h[l] > h[r]) {
				r--;
			} else {
				l++;
			}
		}
		return max;
	}

}
