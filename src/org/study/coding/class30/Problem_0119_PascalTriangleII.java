package org.study.coding.class30;

import java.util.ArrayList;
import java.util.List;

/**
 * 119. 杨辉三角 II
 * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 * 【空间复杂度O(1)】【不能准备额外的空间容器】
 *
 * 解法：只要第某行。考虑用杨辉三角形的原始版本。用动态规划中的空间压缩技巧
 *
 * @since 2022-04-16 10:35:53
 */
public class Problem_0119_PascalTriangleII {

	public List<Integer> getRow(int rowIndex) {
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i <= rowIndex; i++) {

			// 每一个位置，依赖的是它的上一个位置和左上角的位置。考虑从右往左填
			for (int j = i - 1; j > 0; j--) {
				// 【考虑中间的位置】
				ans.set(j, ans.get(j - 1) + ans.get(j));
			}

			// 填上最后一个位置的1【第一个位置的1，就沿用上一行的，不需要动】
			ans.add(1);
		}
		return ans;
	}

}
