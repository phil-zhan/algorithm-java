package org.study.coding.class30;

import java.util.ArrayList;
import java.util.List;

/**
 * 118. 杨辉三角
 * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
 * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
 *
 * 解法：
 * 第row层，第一个位置是1
 * 如果要填的位置i比上一行的个数大，就直接填1
 * 否则 row[i] = 上一行的i位置与上一行的i-1位置的和【将每一行的左边对齐了去看】
 *
 *
 * @since 2022-04-16 10:26:03
 */
public class Problem_0118_PascalTriangle {

	public static List<List<Integer>> generate(int numRows) {
		List<List<Integer>> ans = new ArrayList<>();

		// 生成初始链表。第一个元素都填上1
		for (int i = 0; i < numRows; i++) {
			ans.add(new ArrayList<>());
			ans.get(i).add(1);
		}

		// 去填每一行。从第一行开始
		for (int i = 1; i < numRows; i++) {

			// 每一行从1开始，不能到i
			for (int j = 1; j < i; j++) {
				ans.get(i).add(ans.get(i - 1).get(j - 1) + ans.get(i - 1).get(j));
			}
			ans.get(i).add(1);
		}
		return ans;
	}

}
