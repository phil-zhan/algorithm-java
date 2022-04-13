package org.study.coding.class29;

/**
 * 搜索旋转排序数组
 *
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为[4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
 *
 * 解法：
 * 设置三个指针 L=0、R=n-1、M=(L+R)/2
 * 如果nums[L]、nums[R]、nums[M] 三个数不都相等，那么，在原数组上总能二分
 * 如果nums[L]、nums[R]、nums[M] 三个数都相等，那么，在原数组上就不能二分
 *  此时考虑将L++，直到这三个数不都相等，然后进行二分【在新的L...R 范围上】
 *  当L一直向右，直到等于M的时候，都还没有找到三个数都不相等的情况，那么就在 【M+1...R】 范围上进行二分
 *
 *
 *
 * @since 2022-04-12 21:03:57
 */
public class Problem_0033_SearchInRotatedSortedArray {

    /**
     * nums，原本是有序数组，旋转过，而且左部分长度不知道
     * 找num
     * num所在的位置返回
     *
     * @since 2022-04-12 21:03:01
     */
    public static int search(int[] nums, int target) {
        int L = 0;
        int R = nums.length - 1;
        int M = 0;
        while (L <= R) {
            // M = L + ((R - L) >> 1)
            M = (L + R) / 2;
            if (nums[M] == target) {
                return M;
            }
            // nums[M] != target
            // [L] == [M] == [R] != target 无法二分
            if (nums[L] == nums[M] && nums[M] == nums[R]) {
                while (L != M && nums[L] == nums[M]) {
                    L++;
                }
                // 1) L == M L...M 一路都相等
                // 2) 从L到M终于找到了一个不等的位置
                if (L == M) {
                    // L...M 一路都相等
                    L = M + 1;
                    continue;
                }
            }
            // ...
            // nums[M] != target
            // [L] [M] [R] 不都一样的情况, 如何二分的逻辑
            // 大体的思路就是避开断点所在的部分，先在另外一部分二分【如果非断点的部分将target包含了，就去非断点的部分二分。否在再去断点的部分二分（考虑下一轮）】
            if (nums[L] != nums[M]) {

                // L...M 一定有序【断电在后面部分】
                if (nums[M] > nums[L]) {

                    if (target >= nums[L] && target < nums[M]) {
                        // 此时考虑在左半部分二分
                        //  3  [L] == 1    [M]   = 5   L...M - 1
                        R = M - 1;
                    } else {
                        // 此时去右部分二分
                        // 9    [L] == 2    [M]   =  7   M... R
                        L = M + 1;
                    }
                } else {
                    // [L] > [M]    L....M  存在断点

                    if (target > nums[M] && target <= nums[R]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                }
            } else {
                // [L] [M] [R] 不都一样，  [L] === [M] -> [M]!=[R]
                if (nums[M] < nums[R]) {
                    if (target > nums[M] && target <= nums[R]) {
                        L = M + 1;
                    } else {
                        R = M - 1;
                    }
                } else {
                    if (target >= nums[L] && target < nums[M]) {
                        R = M - 1;
                    } else {
                        L = M + 1;
                    }
                }
            }
        }
        return -1;
    }

}
