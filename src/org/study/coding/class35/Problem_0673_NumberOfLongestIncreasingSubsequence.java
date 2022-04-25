package org.study.coding.class35;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * 673. 最长递增子序列的个数
 * 给定一个未排序的整数数组 nums ， 返回最长递增子序列的个数 。
 *
 * 注意 这个数列必须是 严格 递增的。
 *
 *
 *
 * 示例 1:
 *
 * 输入: [1,3,5,4,7]
 * 输出: 2
 * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
 * 示例 2:
 *
 * 输入: [2,2,2,2,2]
 * 输出: 5
 * 解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输出5。
 *
 * 解法：
 * 和求最长递增子序列差不多。不同的是，需要记录每个长度的个数
 * 这里要求返回的是有几个长度能达到最长
 * 而不是返回最长的长度
 *
 *
 * 来到一个数x。如果它能到达的最大长度是len。 那么以x结尾的，长度为len的个数，就是 所有长度为 len-1 的所有结尾中，小于x的一共有几个
 * 假设当前数是8.其能达到的最大长度是6，要知道以8结尾，长度为6的子序列有多少个？
 * 就得去看长度为5的，所有结尾中，小于8的一共有多少个。
 * 长度为5的所有结尾中，小于8的有多少个，那么长度为6的，以8结尾的子序列就有多少个
 *
 *
 * @since 2022-04-24 21:42:56
 */
public class Problem_0673_NumberOfLongestIncreasingSubsequence {

	/**
	 * 好理解的方法，时间复杂度O(N^2)
	 *
	 * @since 2022-04-24 21:43:08
	 */
	public static int findNumberOfLIS1(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int n = nums.length;
		int[] lens = new int[n];
		int[] cnts = new int[n];
		lens[0] = 1;
		cnts[0] = 1;
		int maxLen = 1;
		int allCnt = 1;
		for (int i = 1; i < n; i++) {
			int preLen = 0;
			int preCnt = 1;
			for (int j = 0; j < i; j++) {
				if (nums[j] >= nums[i] || preLen > lens[j]) {
					continue;
				}
				if (preLen < lens[j]) {
					preLen = lens[j];
					preCnt = cnts[j];
				} else {
					preCnt += cnts[j];
				}
			}
			lens[i] = preLen + 1;
			cnts[i] = preCnt;
			if (maxLen < lens[i]) {
				maxLen = lens[i];
				allCnt = cnts[i];
			} else if (maxLen == lens[i]) {
				allCnt += cnts[i];
			}
		}
		return allCnt;
	}

	/**
	 * 优化后的最优解，时间复杂度O(N*logN)
	 *
	 * @since 2022-04-24 21:43:17
	 */
	public static int findNumberOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}

		// dp[i] : 长度为i的 以 大于等于key 结尾的有value个【将结尾大于等于key的子序列个数，都累加到最小的一个上面】
		// 方便查询
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();


		int len = 0;

		// 当前长度的子序列数量
		int cnt = 0;
		for (int num : nums) {
			// num之前的长度，num到哪个长度len+1
			len = search(dp, num);
			// cnt : 最终要去加底下的记录，才是应该填入的value
			if (len == 0) {

				// 就是当前数自己
				cnt = 1;
			} else {
				TreeMap<Integer, Integer> p = dp.get(len - 1);

				// ceilingKey : 刚刚大于等于key的value被返回
				cnt = p.firstEntry().getValue() - (p.ceilingKey(num) != null ? p.get(p.ceilingKey(num)) : 0);
			}
			if (len == dp.size()) {
				dp.add(new TreeMap<>());
				dp.get(len).put(num, cnt);
			} else {
				dp.get(len).put(num, dp.get(len).firstEntry().getValue() + cnt);
			}
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

	/**
	 * 二分查找，返回>=num最左的位置
	 *
	 * @since 2022-04-24 21:43:25
	 */
	public static int search(ArrayList<TreeMap<Integer, Integer>> dp, int num) {
		int l = 0, r = dp.size() - 1, m = 0;
		int ans = dp.size();
		while (l <= r) {
			m = (l + r) / 2;
			if (dp.get(m).firstKey() >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
