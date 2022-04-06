package org.study.coding.class09;

/**
 * // 本题测试链接 : https://leetcode.com/problems/longest-increasing-subsequence
 * 最长递增子序列问题
 * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
 * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [10,9,2,5,3,7,101,18]
 * 输出：4
 * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
 * 示例 2：
 * <p>
 * 输入：nums = [0,1,0,3,2,3]
 * 输出：4
 * 示例 3：
 * <p>
 * 输入：nums = [7,7,7,7,7,7,7]
 * 输出：1
 * <p>
 * 动态规划（递归）：O(N^2)
 * 必须以 i 位置结尾的子序列，最长递增子序列是多长
 * 最后在所有答案中抓一个最大值
 * ① 来到 i 位置，找到其左边比 i 位置的数小的数【可能有多个，取长度最大的一个】，让其作为当前位置的倒数第二的数。用它的长度加1,。就能得到 i 位置的长度
 * ② i 位置也可以不往前找比自己小的数。那么 i 位置的长度就是 1
 * 两种情况求最大值
 * <p>
 * <p>
 * <p>
 * 最优解O(NlogN)
 * 准备一个dp数组和一个end数组【长度和原数组一样】
 * dp[i] 表示 arr[0...i] 的最长递增子序列
 * end[i] :表示就当前包含 i 位置在内。目前所有长度为 i+1 的递增子序列中，最小的结尾是 end[i]。每个end[i] 都代表一个自增子序列的最小值
 * 如果 end[0] = 3  :表示当前所有长度为 1 的递增子序列中，最小的结尾是 3
 * <p>
 * end中记录了当前所有的子序列长度。和对应长度的最小结尾值
 * 【也就是说，可能有多个子序列对应一个长度。我们记录在这个长度下的最小结尾数是多少。方便帮后面的撑前缀】
 * <p>
 * 也就是来到某个位置 index。 在end的有效区中进行二分，找到刚刚大于等于当前数   的数是 num。
 * 如果能找到，替换掉那个刚刚比它大的数【能找到，当前没资格成长为比其更大的长度。只能是和其一样】
 * 如果找不到，当前的数比前面任何一个长度的最小值都大，前面的长度能帮忙撑起一部分，让自己更长
 * 就说明当前数能扩充有效区【找不到，说明当前的数有资格成长为更长】
 * 用当前数替换掉 end 中的刚刚比当前数大的 那个数.也就是更新当前长度的最小值。
 * <p>
 * <p>
 * 重点：
 * 其实，来到 index 位置，就是去看一下，前面所有的长度，哪个长度能帮自己撑一下，让自己更长
 *
 * @since 2022-03-10 09:28:37
 */
public class Code03_LongestIncreasingSubsequence {

    /**
     * 最优解 O(NlogN)
     * @since 2022-04-06 14:45:51
     */
    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] ends = new int[nums.length];
        ends[0] = nums[0];

        // end数组的右边界
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            l = 0;
            r = right;

            // 在end数组上二分
            while (l <= r) {
                m = (l + r) / 2;
                if (nums[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            // while 循环结束。只会有两种情况
            // 1)当前数比end中的如何一个都打。也就是l一直右移，直到大于r的位置。此时前面的 l 个数都能给当前数撑起前缀
            // 2)在end中，存在比当前数大的数。就会存在r左移。那么在l>的时候，结束循环。此时的l位置就是刚好比当前数大的位置 。
            // 不管是情况1还是情况2. end[l] 都要设置为当前数

            // 检查是否能推高end的右边界
            right = Math.max(right, l);
            ends[l] = nums[i];
            max = Math.max(max, l + 1);
        }
        return max;
    }

}