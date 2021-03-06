package org.study.coding.class12;

/**
 * 本题测试链接 : <a href="https://leetcode.com/problems/median-of-two-sorted-arrays/">https://leetcode-cn.com/problems/median-of-two-sorted-arrays/</a>
 * <p>
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数
 * 进阶，在两个都有序的数组中找整体第K小的数，可以做到O(log(Min(M,N)))
 * <p>
 * 1）两个有序且等长的数组 arr1、arr2. 请你找出并返回这两个正序数组的第中间小的数【最优解：O(logN)】
 * 2) 两个有序但不等长的数组. 请你找出并返回这两个正序数组排完序后的第K小的数【最优解：O(log(min(M,N)))  M和N是两个数组的长度。log级别，取两个长度的最小值】
 * <p>
 * 解法：
 * 问题1）
 * 在两个数组上进行二分
 * <p>
 * 偶数个的时候。累计还是偶数。求排完序之后的上中位数.
 * 设 arr1 = {0,1,2,3}  数字代表第几个数
 * 设 arr2 = {0,1,2,3}  数字代表第几个数
 * 在这个例子中，总共8个数。我们要求的是第四位置的数
 * 判断 arr1[1] 和 arr2[1]  的大小  【也就是判断两个的第二位置数的大小关系】【要求的是第四，取一半】
 * <p>
 * ① 如果 arr1[1] = arr2[1]  那么，这两个数将占据排序后的三四位置的数。直接返回
 * ② 如果 arr1[1] > arr2[1]  那么，arr1[2]的位置将再无缘 四的位置。【因为前面四个位置，arr2至少占据2个，甚至更多。arr1也有两个比 arr1[2]小。拍完序之后，arr1[2]再无缘前四】
 * 也就是说，可能出现在四位置的，也就arr1[0,1],arr2[2,3], 这四个位置可能会出现在第四位置。
 * 再将 arr1[0,1],arr2[2,3] 理解为两个等长的有序数组。求他们两个整体四个数的第二小。就是整体的第四小。【前面也就占据了两个小的位置】
 * 执行重复操作。递归
 * ③ 如果 arr2[1] > arr1[1]  和步骤②类似，执行类似操作。即可得出答案
 * <p>
 * <p>
 * 奇数个的时候。累计还是偶数。求排完序之后的上中位数
 * 拿中间位置取比较【也就是判断两个的第三位置数的大小关系】【要求的是第五，拿中间位置取比较】
 * 设 arr1 = {0,1,2,3,4}  数字代表第几个数
 * 设 arr2 = {0,1,2,3,4}  数字代表第几个数
 * 在这个例子中，总共10个数。我们要求的是第五位置的数
 * 判断 arr1[2] 和 arr2[2]  的大小  。也就是判断两个的第三位置数的大小关系】【要求的是第五，拿中间位置取比较】
 * <p>
 * ① 如果 arr1[2] = arr2[2]  那么，这两个数将占据排序后第五、第六位置的数。直接返回
 * ② 如果 arr1[2] > arr2[2]  那么，arr1[2]的位置将再无缘 五的位置。【原理同前面】
 * 也就是说，可能出现在第五位置的，也就arr1[0,1],arr2[2,3,4], 这留个个位置可能会出现在第五位置。
 * 递归无法继续了【递归要求的是两个等长的】
 * 单独验证一下arr2[2] .也就是剩下的长的一个的第一个。
 * 如果 arr2[2] >= arr1[1] 。那么 arr2[2] 肯定就是第五小。
 * 如果不成立，直接淘汰掉 arr2[2]。剩下的两个等长的去走递归。求 arr1[0,1],arr2[3,4] 求第二小的数。【前面也就填了三个了】
 * <p>
 * 再将 arr1[0,1],arr2[2,3] 理解为两个等长的有序数组。求他们两个整体四个数的第二小。就是整体的第四小。【前面也就占据了两个小的位置】
 * 执行重复操作。递归
 * <p>
 * ③ 如果 arr1[2] > arr2[2]  和步骤②类似，执行类似操作。即可得出答案
 * <p>
 * <p>
 * <p>
 * 解法：
 * 问题2）
 * 两个数组arr1和arr2. 长度分别为 m 和 n，认为规定。 m<=n
 * <p>
 * 情况1：
 * 当 1 <= k <= m 时
 * 假设 k 等于3.
 * 直接考虑在arr1[0...2] 和 arr2[0...2]的等长数组上，拿排完序后的第中间位置的数【偶数取上中】。上一题的原型
 * <p>
 * 情况2：
 * 当 n < k <= m+n 时
 * 假设 m = 10，n = 17 要求 k = 23
 * 考虑哪些数不可能
 * 长数组 arr2[1...12] 全不可能。【就算把短数组的10个数全部插在长数组的前面，前12个也不可能称为第23小】
 * 短数组 arr1[1...5] 也不可能。因为就算段数组的全部都大于长数组，长数组只能撑起 17个，就算加上前5个，也只能到 22 个，到不了第 23 的位置
 * 这样一来，长数组就淘汰了 12 个【k-m-1】。短数组就淘汰了 5【k-n-1】个 。总共就淘汰了 17个【此时不能直接求剩下两个等长数组的上中位数。因为加起来也只能到底22位。还差一位】
 * 手动淘汰：
 * 比较一下长数组第13位【长数组淘汰的下一位。设为x】和短数组第10位【短数组最后一位.设为y】。
 * 如果 x >= y .那么 x就是第23小，直接返回。否者的话就直接淘汰掉 x 【x不可能成为第23位】
 * <p>
 * 再比较一下短数组第6位【剩下短数组的第一位。设为x】和长数组第17位【长数组最后一位.设为y】。
 * 如果 x >= y .那么 x就是第23小，直接返回。否者的话就直接淘汰掉 x 【x不可能成为第23位】
 * <p>
 * 接下来再拿
 * 长数组剩下的和段数组剩下的，再求一个上中位数。就是我们要的第23位
 * 【13 + 6 + 4 = 23】 13 是长数组淘汰的数。6是短数组淘汰的数。 4 是剩下的两个数组（8个数，求的上中位数）
 * <p>
 * 情况3：
 * 当 m < k <= n 时
 * 假设 m = 10，n = 17 要求 k = 15
 * 考虑哪些不可能
 * 长数组： [1...4] 不可能。就算短数组的 10 个数全在长数组的前面，前面4个也不会排到第 15位
 * 长数组： [16... 17] 也不可能。就算短数组的 10 个数全在长数组的后面面，最后两个数也不会排到第 15位
 * <p>
 * 短数组：全都可能。只需要长数组提供部分的支撑，就都可能
 * 此时剩下的两个数组。长数组【5...15】 共 11 个数。短数组只有 10 个数。没办法用上中位的原型
 * 单独考虑一下长数组的第5位。假设为 x 。短数组的最后一位假设为 y。
 * 【只能考虑长数组的5，而不是长数组的15。不然就算淘汰第15，最后也只能是 4 + 10 。只能得到第 14 位】【4是之前直接淘汰的。10，是两个数组剩下的20个，求上中位】
 * 如果 x >= y . 那么 x 就是第15位，直接返回
 * 否则淘汰调长数组第 5 位。
 * <p>
 * 剩下的两个数组都是长度为 10 的数组。求这20个数的第 10 小。加上前面淘汰的 5 个。就是第15位。
 *
 * @since 2022-03-13 11:03:26
 */
public class Code02_FindKthMinNumber {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // 可能存在长度为 0 的数组
        int size = nums1.length + nums2.length;
        boolean even = (size & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double) (findKthNum(nums1, nums2, size / 2) + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum(nums1, nums2, size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double) (nums1[(size - 1) / 2] + nums1[size / 2]) / 2;
            } else {
                return nums1[size / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double) (nums2[(size - 1) / 2] + nums2[size / 2]) / 2;
            } else {
                return nums2[size / 2];
            }
        } else {
            return 0;
        }
    }

    /**
     * 进阶问题 : 在两个都有序的数组中，找整体第K小的数,也就是找到合并后，下标是k-1的数
     * 可以做到O(log(Min(M,N)))
     *
     * @since 2022-03-13 05:05:32
     */
    public static int findKthNum(int[] arr1, int[] arr2, int kth) {

        // 长短数组定位
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int l = longs.length;
        int s = shorts.length;

        // arr1: 1 3 5 7 9 11 13
        // arr2: 1 3 5 7
        // kth: 3
        // kth: 9
        // kth: 6


        if (kth <= s) {
            // 1)
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
        }
        if (kth > l) {
            // 3)
            if (shorts[kth - l - 1] >= longs[l - 1]) {
                return shorts[kth - l - 1];
            }
            if (longs[kth - s - 1] >= shorts[s - 1]) {
                return longs[kth - s - 1];
            }
            return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
        }
        // 2)  s < k <= l
        if (longs[kth - s - 1] >= shorts[s - 1]) {
            return longs[kth - s - 1];
        }
        return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);


    }


    /**
     * A[s1...e1]
     * B[s2...e2]
     * 一定等长！
     * 返回整体的，上中位数！8（4） 10（5） 12（6） 也就是返回中位数中较小的那个
     * 1）两个有序且等长的数组 arr1、arr2. 请你找出并返回这两个正序数组的第中间小的数【最优解：O(logN)】
     * 每次都砍掉一半
     *
     * @since 2022-03-13 04:44:18
     */
    public static int getUpMedian(int[] A, int s1, int e1, int[] B, int s2, int e2) {
        int mid1 = 0;
        int mid2 = 0;
        while (s1 < e1) {
            // mid1 = s1 + (e1 - s1) >> 1
            // 奇数就是正好中间位置
            // 偶数的话，是下中间位置
            mid1 = (s1 + e1) / 2;
            mid2 = (s2 + e2) / 2;
            if (A[mid1] == B[mid2]) {
                return A[mid1];
            }
            // 两个中点一定不等！
            if (((e1 - s1 + 1) & 1) == 1) {
                // 奇数长度
                if (A[mid1] > B[mid2]) {
                    if (B[mid2] >= A[mid1 - 1]) {
                        return B[mid2];
                    }
                    e1 = mid1 - 1;
                    s2 = mid2 + 1;
                } else {
                    // A[mid1] < B[mid2]
                    if (A[mid1] >= B[mid2 - 1]) {
                        return A[mid1];
                    }
                    e2 = mid2 - 1;
                    s1 = mid1 + 1;
                }
            } else {
                // 偶数长度
                if (A[mid1] > B[mid2]) {
                    e1 = mid1;
                    s2 = mid2 + 1;
                } else {
                    e2 = mid2;
                    s1 = mid1 + 1;
                }
            }
        }

        // s1 == e1
        // s2 == e2
        // 两个数组都只有一个数，返回第一小。谁小返回谁
        return Math.min(A[s1], B[s2]);
    }

    public static void main(String[] args) {
        // 1 2 3 4 5 6
        System.out.println(getUpMedian(new int[]{1, 3, 5}, 0, 2, new int[]{2, 4, 6}, 0, 2));
    }

}
