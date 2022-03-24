package org.study.coding.class22;


/**
 * // 本题测试链接 : https://leetcode.com/problems/trapping-rain-water/
 * @since 2022-03-23 21:26:47
 */
public class Code02_TrappingRainWater {

	public static int trap(int[] height) {
		if (height == null || height.length < 2) {
			return 0;
		}
		int N = height.length;
		int L = 1;
		int leftMax = height[0];
		int R = N - 2;
		int rightMax = height[N - 1];
		int water = 0;
		while (L <= R) {
			if (leftMax <= rightMax) {
				water += Math.max(0, leftMax - height[L]);
				leftMax = Math.max(leftMax, height[L++]);
			} else {
				water += Math.max(0, rightMax - height[R]);
				rightMax = Math.max(rightMax, height[R--]);
			}
		}
		return water;
	}

}
