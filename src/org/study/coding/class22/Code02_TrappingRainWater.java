package org.study.coding.class22;


/**
 * 2. 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水
 * Leetcode题目：https://leetcode.com/problems/trapping-rain-water/
 * <p>
 * <p>
 * 只考虑 i 位置上边能留下几格水。【最后将每个位置上边能留下来的水格数都相加】
 * 在不考虑 i位置的情况下，左边 0...i-1 范围的最大值 和 右边 i+1...n-1 范围的最大值。决定了当前i位置能留下的雨水格数。肯定是左右两个最大值较小的那一个决定
 * water[i] = min【 max(nums[0...i-1]) , max(nums[i+1...n-1]) 】 - nums[i]
 * <p>
 * 如果左右两边的最大值的较小的一个没有当前数大，那么，当前位置就接不了水
 * 再和 0求一个max。不要让接的水量小于0
 * water[i] = max( water[i],0 )
 * <p>
 * 简单办法。
 * 利用两个辅助数组
 * max1[i] :表示0...i 范围上的最大值
 * max2[i] :表示i...n-1 范围上的最大值
 * <p>
 * <p>
 * 最优解：
 * 0位置和n-1位置，是留不下任何水的。没有求的必要
 * 利用双指针 L 和 R
 * L指针从1开始。
 * R指针从n-2开始
 * 利用两个变量记录L左边的最大值和R右边的最大值
 * leftMax = nums[0]
 * rightMax = nums[n-1]
 * <p>
 * 对比两个最大值。谁小就先结算谁那边的水量
 * <p>
 * 如果 leftMax > rightMax 那么就先求右边的水量。因为左边此时的最大值是 leftMax.后续左边的最大值只会大于或等于 leftMax。不会小于 leftMax
 * 此时 R 位置的水量由 rightMax 决定
 * 因为对于 R 位置来说。 leftMax 可能会被推高。但是不会被压低。而 rightMax 是已经确定的了
 * <p>
 * <p>
 * 同理
 * 如果 leftMax < rightMax 那么就先求左边的水量。因为右边此时的最大值是 rightMax.后续右边的最大值只会大于或等于 rightMax。不会小于 rightMax
 * 此时 L 位置的水量由 leftMax 决定
 * 因为对于 L 位置来说。 rightMax 可能会被推高。但是不会被压低。而 leftMax 是已经确定的了
 * <p>
 * 相等的时候怎么办？
 * 可以一起结算。也可以结算左边或右边。无所谓
 * <p>
 * 注意：
 * 走过的位置，不要忘记更新对应max
 *
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
