package org.study.coding.class01;

public class MainTest07 {

    public int findTargetSumWays1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return precess(0, target, nums);
    }

    public int precess(int index, int rest, int[] nums) {
        if (index == nums.length) {
            return rest == 0 ? 1 : 0;
        }

        return precess(index + 1, rest - nums[index], nums) + precess(index + 1, rest + nums[index], nums);
    }

    /**
     * 有点问题
     * @since 2022-03-07 13:22:55
     */
    public int findTargetSumWays2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[][] dp = new int[nums.length + 1][target + 1];
        dp[nums.length][0] = 1;
        for (int index = nums.length - 1; index >=0; index--) {
            for (int rest = 0; rest <= target; rest++) {
                dp[index][rest] = (rest >= nums[index]) ? dp[index + 1][rest - nums[index]] : 0;
                dp[index][rest] += (rest + nums[index] <= target) ? dp[index + 1][rest + nums[index]] : 0;
            }
        }

        return dp[0][target];
    }
}
