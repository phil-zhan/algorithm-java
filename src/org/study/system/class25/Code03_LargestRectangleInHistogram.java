package org.study.system.class25;

import java.util.Stack;

// 测试链接：https://leetcode.com/problems/largest-rectangle-in-histogram
public class Code03_LargestRectangleInHistogram {

	/**
	 * 求出以每个位置为高，看看左右两边能扩多远【能往左扩：左边的值必须大于等于当前值。能往右扩：右边的值必须大于等于当前值】
	 * 利用单调栈，找出当前值的左边（右边）离它最近且小于它的值。再往中间缩一个位置。就能得到当前位置为高的长方形的面积
	 * @date 2021-08-27 12:40:46
	 */
	public static int largestRectangleArea1(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int maxArea = 0;
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < height.length; i++) {
			while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
				int j = stack.pop();
				int k = stack.isEmpty() ? -1 : stack.peek();
				int curArea = (i - k - 1) * height[j];
				maxArea = Math.max(maxArea, curArea);
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop();
			int k = stack.isEmpty() ? -1 : stack.peek();
			int curArea = (height.length - k - 1) * height[j];
			maxArea = Math.max(maxArea, curArea);
		}
		return maxArea;
	}

	public static int largestRectangleArea2(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int N = height.length;
		int[] stack = new int[N];
		int si = -1;
		int maxArea = 0;
		for (int i = 0; i < height.length; i++) {
			while (si != -1 && height[i] <= height[stack[si]]) {
				int j = stack[si--];
				int k = si == -1 ? -1 : stack[si];
				int curArea = (i - k - 1) * height[j];
				maxArea = Math.max(maxArea, curArea);
			}
			stack[++si] = i;
		}
		while (si != -1) {
			int j = stack[si--];
			int k = si == -1 ? -1 : stack[si];
			int curArea = (height.length - k - 1) * height[j];
			maxArea = Math.max(maxArea, curArea);
		}
		return maxArea;
	}

}
