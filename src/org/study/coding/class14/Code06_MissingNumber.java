package org.study.coding.class14;

/**
 * 测试链接：https://leetcode.com/problems/first-missing-positive/
 * <p>
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数【也就是找出大于0 且缺失的数】。
 * 你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 * 正整数: 1、2、3...
 * <p>
 * 字节的问法：【给你一个未排序的整数数组 nums ，请你找出大于K。且没有出现在无序数组中的最小的正整数】
 * 其实就是一个题。
 * 将原题所有数都减去一个K。找出缺失的最小正整数就OK
 * <p>
 * 考虑从左到右
 * 争取将 i 位置上放的是 i+1
 * 【1,2,3,4,5...】 数组元素
 * 【0,1,2,3,4...】 位置
 * 如果某个位置左不到了
 * 那么它就是缺失的最小正整数
 * <p>
 * 在 [L...R]范围上
 * 在左边设立一个有效区域【在该区域内，保证第 i 位置，放的是 i+1 的数】 L是有效区的右边界
 * 再在右边设立一个无效区域【将无用的数字放在这个区域】 R是无效区的左边界
 * <p>
 * 同时 R+1 还是当前最好的预期。
 * 【因为是按照元素值等于所在位置加1来组织的】
 * 【也就是说，当 L...R 范围，刚好被数组元素填满。那么没有在数组中出现的数，最佳选择就是R的位置】
 * <p>
 * 如果最终返回8，就说明在数组 1...7都是全的
 * 如果最终返回7，就说明在数组 1...6都是全的
 * <p>
 * 从左到右遍历
 * 来到index位置
 * 1） arr[index] <= L 该位置的数是无效数字
 * 2） arr[index] > R 该位置的数字也是垃圾
 * 3） arr[index] > L 且 <= R .就把它放到它该去的位置【arr[index]-1】.
 * 如果它该去的位置和它是一样的。那么它也是垃圾。如果不等，就将它放到它该去的位置
 * 如果 arr[index] 正好等于 L+1 。有效区向右扩
 * 如果该数是垃圾，就将其和垃圾区的前一个位置交换，垃圾区向左扩
 * <p>
 * 注意：右边过来的数，要再一次检查
 * ...
 *
 * @since 2022-03-16 08:56:25
 */
public class Code06_MissingNumber {

    public static int firstMissingPositive(int[] arr) {
        // l是盯着的位置【当前考察的位置】
        // 0 ~ L-1有效区
        int L = 0;
        int R = arr.length;
        while (L != R) {
            if (arr[L] == L + 1) {
                // 当前数有效
                L++;
            } else if (arr[L] <= L || arr[L] > R || arr[arr[L] - 1] == arr[L]) {
                // 垃圾的情况
                swap(arr, L, --R);
            } else {
                // arr[L] 在 [L...R]范围
                swap(arr, L, arr[L] - 1);
            }
        }
        return L + 1;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
