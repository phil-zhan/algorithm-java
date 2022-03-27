package org.study.coding.class22;

/**
 * 本题测试链接 : https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
 * 1. 给定数组nums由正整数组成，找到三个互不重叠的子数组的最大和。每个子数组的长度为k，我们要使这3*k个项的和最大化。返回每个区间起始索引的列表（索引从 0 开始）。
 * 如果有多个结果，返回字典序最小的一个。
 * <p>
 * <p>
 * 考虑 0...i 范围上选子数组【连续】，怎么能让其累加和最大
 * 先求一个辅助数组 help
 * help[i] : 子数组必须以i结尾的情况下，最大的累加和是多少
 * 1）：只包含 i 位置的数
 * 2）：i位置决定向左扩
 * help[i] = Math.max(nums[i],help[i-1]+nums[i]);
 * <p>
 * 定义一个dp：
 * dp1[i]表示 [0...i] 范围上选子数组，其最大累加和是多少
 * 1）：子数组不以 i 位置结尾 dp1[i] = dp1[i-1]
 * 2)：子数组必须以 i 位置结尾，那么就从 i 位置开始往前推，看看最大能推多远 dp1[i] = help[i]
 * 两种可能性，抓一个max 。 dp1[i] = Math.max(dp1[i-1],help[i])
 * <p>
 * <p>
 * <p>
 * <p>
 * 生成dp1：dp1[i]：表示 0...i范围上，只选一个子数组，怎么能最好
 * 再从右往左遍历。生成一个dp2：dp2[i]表示i...n-1范围上，怎么能最好.
 * <p>
 * <p>
 * <p>
 * <p>
 * 考虑 i...n-1 范围上选子数组【连续】，怎么能让其累加和最大
 * 也需要一个辅助数组help2
 * help2[i]:表示必须以 i 开头的情况下，子数组最大累加和是多大。【只包含自己i位置的数。i位置的数向右扩。两种情况抓一个最大值】【具体过程和前面的help类似】
 * 再来求dp2
 * 1）dp2的子数组不以i开头 dp2[i] = dp2[i+1]
 * 2）dp2的子数组以i开头 dp2[i] = help2[i]
 * dp2[i] = Math.max(dp[i+1],help2[i])
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 定义一个dp3
 * dp3[i]：表示 0...i 范围上，必须选一个长度为 k 的子数组，怎么累加和尽量大
 * dp3[0...k-2] 是没有意义的。数量不够k个
 * 1）：该子数组就是最后k个。以 i 位置结尾，往前推k个
 * 2）：该子数组不以i位置结尾，就是前面 i-1 位置怎么选k个，能让长度为 k 的子数组累加和最大
 * <p>
 * <p>
 * <p>
 * <p>
 * 定义一个dp4
 * dp4[i]：表示 i...n-1 范围上，必须选一个长度为 k 的子数组，怎么累加和尽量大
 * dp4[n-k+1...n-1] 是没有意义的。数量不够k个
 * 1）：该子数组就是前k个。以 i 位置开始，往后推k个
 * 2）：该子数组不以i位置开始，就是后面 i+1 位置怎么选k个，能让长度为 k 的子数组累加和最大
 * <p>
 * <p>
 * <p>
 * <p>
 *
 * 固定中间的一个数组范围，L...R 假设表示中间的数组。长度必须是K
 * 遍历数组。L从K开始【左边至少要留K个数给左数组】，到 R= L+k-1 【表示中间的数组窗口】
 * 左边数组【0...L-1】和右边数组【R+1...N-1】，在自己的范围上，只选一个子数组，怎么能获得累加和最大
 * 只有滑动中间的数组窗口。左边数组和右边数组可以通过查表【dp1和dp2】得到。中间数组的累加和也可以通过前缀和数组得到。
 * 每滑动一次，都求一下三个数组累加和的和。看看能不能把答案推高。
 * 最后就能得到正确答案
 *
 * @since 2022-03-24 07:41:25
 */
public class Code01_MaximumSumof3NonOverlappingSubarrays {

    public static int[] maxSumArray1(int[] arr) {
        int N = arr.length;
        int[] help = new int[N];
        // help[i] 子数组必须以i位置结尾的情况下，累加和最大是多少？
        help[0] = arr[0];
        for (int i = 1; i < N; i++) {
            int p1 = arr[i];
            int p2 = arr[i] + help[i - 1];
            help[i] = Math.max(p1, p2);
        }
        // dp[i] 在0~i范围上，随意选一个子数组，累加和最大是多少？
        int[] dp = new int[N];
        dp[0] = help[0];
        for (int i = 1; i < N; i++) {
            int p1 = help[i];
            int p2 = dp[i - 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp;
    }


    public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int N = nums.length;

        // range[i] = m：从 i 位置开始的 k个长度，累加和是 m
        int[] range = new int[N];

        // left[i] ： 0...i 范围上选k长度的数组，怎么选才能让累加和最大
        int[] left = new int[N];
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        range[0] = sum;
        left[k - 1] = 0;
        int max = sum;
        for (int i = k; i < N; i++) {
            sum = sum - nums[i - k] + nums[i];
            range[i - k + 1] = sum;
            left[i] = left[i - 1];
            if (sum > max) {
                max = sum;
                left[i] = i - k + 1;
            }
        }




        sum = 0;
        for (int i = N - 1; i >= N - k; i--) {
            sum += nums[i];
        }
        max = sum;
        // right[i] ： i...n-1 范围上选k长度的数组，怎么选才能让累加和最大
        int[] right = new int[N];
        right[N - k] = N - k;
        for (int i = N - k - 1; i >= 0; i--) {
            sum = sum - nums[i + k] + nums[i];
            right[i] = right[i + 1];
            if (sum >= max) {
                max = sum;
                right[i] = i;
            }
        }



        int a = 0;
        int b = 0;
        int c = 0;
        max = 0;

        // 中间一块的起始点 (0...k-1)选不了 i == N-1
        for (int i = k; i < N - 2 * k + 1; i++) {
            int part1 = range[left[i - 1]];
            int part2 = range[i];
            int part3 = range[right[i + k]];
            if (part1 + part2 + part3 > max) {
                max = part1 + part2 + part3;
                a = left[i - 1];
                b = i;
                c = right[i + k];
            }
        }

        // 返回最好的划分点
        return new int[]{a, b, c};
    }

}
