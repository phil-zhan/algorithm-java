package org.study.coding.class07;

/**
 * 测试链接 : https://leetcode.com/problems/maximum-gap/
 * <p>
 * 给定一个数组arr，返回如果排序之后（注意是如果排序），相邻两数的最大差值。要求时间复杂度O(N)，不能使用非基于比较的排序
 *
 * @since 2022-03-08 07:26:46
 */
public class Code03_MaxGap {

    /**
     * 整个数组，假设 9 个数
     * 最小值假设为 0
     * 最大值假设为 99
     * 在整个范围上，将整个范围10等分【个数加1 等分】【从最小值到最大值的范围等分为个数加1份】
     * 没办法整数等分的时候，就带小数等分
     * 也就有10个桶
     * 遍历数组，将对应的数字发到对应的桶。【每个桶，只用保留当前桶的最大值和最小值，其他数一律不要】
     * 因为桶数比元素个数多一个，中间必定存在空桶
     * <p>
     * 如果排完序的话，相邻两个数，可能进入同一个桶，也可能来自跨桶的【前一个桶的最大值和后一个桶的最小值也可能相邻】
     * 空桶的左、右两侧都必定存在非空桶。且左侧的桶的最大值和右侧桶的最小值的差值，必定大于桶的大小
     * 【因为是将数的最小值到最大值的范围去等分，所以空桶不可能出现在最左侧和最右侧】
     * <p>
     * 因此，我们完全不用去考虑来自桶内部的相邻数，因为来自桶内部的相邻数的差值【排完序后的】，肯定都小于桶的大小【我们要的是排完序之后，差值最大的缺口】
     * 最后遍历桶，找出相邻两个 有数桶 的最小值和最大值的差值【前一个桶的最大值和后一个桶的最小值】。最后再抓一个最大的，就是答案
     * 不用考虑桶内的情况，只需要考虑跨桶的情况。
     * <p>
     * 为什么要都找？而不是只找空桶两侧
     * 空桶两侧不一定是最佳解【img_Code03_1.png】
     * <p>
     * 既然答案可能存在空桶也可能在非空桶，为什么还要设计空桶？？
     * 设置一个空桶。不是为了在空桶的两侧找答案。而是为了杀死桶内存在答案的情况。
     *
     * @since 2022-03-08 07:28:14
     */
    public static int maximumGap(int[] nums) {

        if (nums == null || nums.length < 2) {
            return 0;
        }
        int len = nums.length;

        //抓到最小值和最大值
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // 只有一种数
        if (min == max) {
            return 0;
        }

        // hasNum[i] 表示 i 号桶内是否有数字
        boolean[] hasNum = new boolean[len + 1];


        // maxs[i] 表示 i 号桶内的最大值
        int[] maxs = new int[len + 1];

        // mins[i] 表示 i 号桶内的最小值
        int[] mins = new int[len + 1];


        // 桶号
        int bid;
        for (int num : nums) {

            // 填桶
            bid = bucket(num, len, min, max);

            mins[bid] = hasNum[bid] ? Math.min(mins[bid], num) : num;

            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], num) : num;

            hasNum[bid] = true;
        }


        // 抓答案
        int res = 0;

        // 上一个桶的最大值
        int lastMax = maxs[0];
        for (int i = 1; i <= len; i++) {
            // 当前桶是空，直接跳过
            if (hasNum[i]) {
                res = Math.max(res, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return res;
    }

    /**
     * 当前的数是 num
     * 范围是 min ~ max
     * 分成 len + 1 份
     * 返回应该进哪个桶
     *
     * @since 2022-03-08 08:00:12
     */
    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

}
