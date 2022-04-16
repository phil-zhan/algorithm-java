package org.study.coding.class30;

/**
 * 合并两个有序数组
 * 给你两个按 非递减顺序 排列的整数数组nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
 * <p>
 * 就是归并排序的合并过程，谁小拷贝谁
 *
 * @since 2022-04-14 21:31:03
 */
public class Problem_0088_MergeSortedArray {

    /**
     * 从后面开始，谁大拷贝谁
     * @since 2022-04-14 21:46:36
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int index = nums1.length;
        while (m > 0 && n > 0) {
            if (nums1[m - 1] >= nums2[n - 1]) {
                nums1[--index] = nums1[--m];
            } else {
                nums1[--index] = nums2[--n];
            }
        }
        while (m > 0) {
            nums1[--index] = nums1[--m];
        }
        while (n > 0) {
            nums1[--index] = nums2[--n];
        }
    }

}
