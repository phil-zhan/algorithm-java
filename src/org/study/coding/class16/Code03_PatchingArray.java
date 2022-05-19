package org.study.coding.class16;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/patching-array/
 * 给定一个已排序的正整数数组 nums，和一个正整数n 。从[1, n]区间内选取任意个数字补充到nums中，
 * 使得[1, n]区间内的任何数字都可以用nums中某几个数字的和来表示，请输出满足上述要求的最少需要补充的数字个数
 * <p>
 * 如果数组是空的情况下。
 * 要搞定一个 k
 * 那么需要 1、2、4、8、16... 有了这些数，就都能搞定。有了 16，就能搞定 1...31  .以此类推，能搞定的数刚好大于等于k就行
 * <p>
 * 如果数组里面有数的情况下
 * 【range = k：表示 1...k 范围上的任何一个数。都能由数组中的数加出来】
 * 要想使补充到数组中的数最少，就要最经济的使用数组中原有的数
 * 如果数组中有一个数 x 。在 i 位置。
 * 那么就要先搞定 range = [1...x-1]这个范围 【可以让前面搞定的范围大于等于x-1.那么就能顺利使用x这个数】。然后加一个x，就能顺利的推高 range
 * 如果当前的 range<x-1 .也就是比你顺利的搞定前面 x-1 的范围。那么只能添加数
 * 去到下一个位置。继续执行同样的操作
 * <p>
 * 如果数组用完了。还没能将range推到想要的位置。那么就看还差多少，剩下的都需要往数组中补充。补充的数从数组的最大值开始往后取
 * 每补充一个数，都推高一下range。直到将range推高到目标数为止
 *
 * @since 2022-03-17 11:04:15
 */
public class Code03_PatchingArray {

    /**
     * arr请保证有序，且正数  1~aim
     *
     * @since 2022-03-17 11:04:46
     */
    public static int minPatches(int[] arr, int aim) {

        // 缺多少个数字
        int patches = 0;

        // 已经完成了1 ~ range的目标
        long range = 1;
        Arrays.sort(arr);

        // 遍历数组
        for (int i = 0; i != arr.length; i++) {
            // arr[i]
            // 要求：1 ~ arr[i]-1 范围被搞定！
            while (arr[i] - 1 > range) {
                // arr[i]   1 ~ arr[i]-1

                // range + 1 是缺的数字
                range += range + 1;

                patches++;

                // range 变化，检查一下总目标
                if (range >= aim) {
                    return patches;
                }
            }
            // 要求被满足了！
            range += arr[i];
            if (range >= aim) {
                return patches;
            }
        }

        // 数组中的数用完了
        while (aim >= range + 1) {
            range += range + 1;
            patches++;
        }
        return patches;
    }

    /**
     * 嘚瑟
     * 用int类型代替long类型
     * 嘚瑟版本。战略性放弃
     *
     * @since 2022-03-17 11:04:59
     */
    public static int minPatches2(int[] arr, int K) {

        // 缺多少个数字
        int patches = 0;

        // 已经完成了1 ~ range的目标
        int range = 0;
        for (int i = 0; i != arr.length; i++) {
            // 1~range
            // 1 ~ arr[i]-1
            while (arr[i] > range + 1) {
                // arr[i] 1 ~ arr[i]-1

                if (range > Integer.MAX_VALUE - range - 1) {
                    return patches + 1;
                }

                // range + 1 是缺的数字
                range += range + 1;
                patches++;
                if (range >= K) {
                    return patches;
                }
            }
            if (range > Integer.MAX_VALUE - arr[i]) {
                return patches;
            }
            range += arr[i];
            if (range >= K) {
                return patches;
            }
        }
        while (K >= range + 1) {
            if (K == range && K == Integer.MAX_VALUE) {
                return patches;
            }
            if (range > Integer.MAX_VALUE - range - 1) {
                return patches + 1;
            }
            range += range + 1;
            patches++;
        }
        return patches;
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 31, 33};
        int n = 2147483647;
        System.out.println(minPatches(test, n));

    }

}
