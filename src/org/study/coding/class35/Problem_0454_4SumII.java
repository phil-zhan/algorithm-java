package org.study.coding.class35;

import java.util.HashMap;

/**
 * 454. 四数相加 II
 * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
 *
 * 0 <= i, j, k, l < n
 * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
 *
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
 * 输出：2
 * 解释：
 * 两个元组如下：
 * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
 * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
 * 示例 2：
 *
 * 输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
 * 输出：1
 *
 * 解法：
 * A数组挑一个，B数组挑一个，有多少种组合。
 * 将所有的组合存在表里面去
 * key: 可以组成的和
 * value：是一个list。哪些组合的 和 是key【可能有多对】
 *
 * 再生产C数组和D数组的组合表。【和A、B数组的类似】
 *
 *
 * 遍历其中一个表。如果当前遍历到的key是 a。去看看另外一个表里面，是不是存在一个 -a 。
 * 如果存在，就将两个
 *
 *
 * @since 2022-04-24 21:31:12
 */
public class Problem_0454_4SumII {

	public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {

		// key：是和
		// value: 是达到这个和的组合数
		HashMap<Integer, Integer> map = new HashMap<>();
		int sum = 0;
		for (int i = 0; i < nums1.length; i++) {
			for (int j = 0; j < nums2.length; j++) {
				sum = nums1[i] + nums2[j];
				if (!map.containsKey(sum)) {
					map.put(sum, 1);
				} else {
					map.put(sum, map.get(sum) + 1);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < nums3.length; i++) {
			for (int j = 0; j < nums4.length; j++) {
				sum = nums3[i] + nums4[j];
				if (map.containsKey(-sum)) {
					ans += map.get(-sum);
				}
			}
		}
		return ans;
	}

}
