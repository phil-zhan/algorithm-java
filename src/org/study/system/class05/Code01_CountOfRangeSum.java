package org.study.system.class05;

/**
 * // 这道题直接在leetcode测评：
 * // https://leetcode.com/problems/count-of-range-sum/
 *
 *
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 *
 * 示例 1：
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 *
 *
 * 示例 2：
 * 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
 *
 * 提示：
 * 1 <= nums.length <= 104
 * -231 <= nums[i] <= 231 - 1
 * -3 * 104 <= lower <= upper <= 3 * 104
 *  
 * 提示：
 * 最直观的算法复杂度是 O(n2) ，请在此基础上优化你的算法。
 *
 *
 *
 *
 *
 *
 * 实现思路：
 * 假设 0~i 位置
 *
 *
 * @author phil
 * @date 2021/5/13 17:04
 */

public class Code01_CountOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >> 1);
        return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
                + merge(sum, L, M, R, lower, upper);
    }

    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // [windowL, windowR)
        for (int i = M + 1; i <= R; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }
            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }
        long[] help = new long[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        return ans;
    }

}

