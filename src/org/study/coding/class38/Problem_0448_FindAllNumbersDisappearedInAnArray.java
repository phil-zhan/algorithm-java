package org.study.coding.class38;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/
 *
 * 解法：
 * 下标循环怼
 * 争取 i 位置就放 i+1
 * 不要覆盖。
 *
 * 最后再遍历一遍。 i 位置不等于 i+1 的就是缺失的数字
 * 时间复杂度 O(N)
 *
 *
 * 方法二，可以用排序。时间复杂度是 O(NlogN)
 *
 *
 * @since 2022-06-02 20:41:36
 */
public class Problem_0448_FindAllNumbersDisappearedInAnArray {

	public static List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null || nums.length == 0) {
			return ans;
		}
		int N = nums.length;
		for (int i = 0; i < N; i++) {
			// 从i位置出发，去玩下标循环怼
			walk(nums, i);
		}
		for (int i = 0; i < N; i++) {
			if (nums[i] != i + 1) {
				ans.add(i + 1);
			}
		}
		return ans;
	}

	public static void walk(int[] nums, int i) {
		while (nums[i] != i + 1) { // 不断从i发货
			int nexti = nums[i] - 1;
			if (nums[nexti] == nexti + 1) {
				// 对应的位置已经是 i+1 。就停。表示从 i 位置开始的怼完了
				break;
			}
			swap(nums, i, nexti);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
