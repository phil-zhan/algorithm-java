package org.study.system.class04;

/**
 * @author phil
 * @date 2021/3/26 14:35
 * 逆序对问题
 * <p>
 * 问题描述：
 * 在一个数组中，找出后面的数比前面的数小的数有多少对
 * 如：【3,1,0,5,3】
 * 3,1  3,0  1,0  5,3
 * </p>
 * <p>
 * <p>
 * 实现思路：
 * 利用递归排序的思路。
 * 在merge合并的时候，从右边开始拷贝，谁大拷贝谁
 * 同样大的拷贝右边的
 * 拷贝的时候，右边的数小，就当当前逆序对的数加1，
 * 直到右边的全部考完
 * </p>
 */
public class Code04_ReversePairs {

    public static void main(String[] args) {
        int pairs = new Code04_ReversePairs().reversePairs(new int[]{1,3,2,3,1});
        System.out.println(pairs);
    }

    public int reversePairs(int[] nums) {
        if (null == nums || nums.length < 2) {
            return 0;
        }

        return reversePairsProcess(nums, 0, nums.length - 1);
    }

    private int reversePairsProcess(int[] nums, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);


        return reversePairsProcess(nums, left, mid) + reversePairsProcess(nums, mid + 1, right) + reversePairsMerge(nums, left, mid, right);
    }

    private int reversePairsMerge(int[] nums, int left, int mid, int right) {
        int[] help = new int[right - left + 1];
        int index = right - left + 1;
        int p1 = mid;
        int p2 = right;
        int curNum = 0;

        while (p1 >= left && p2 >= mid + 1) {
            if (nums[p2] >= nums[p1]) {
                help[--index] = nums[p2--];
            } else {
                help[--index] = nums[p1--];
                curNum += p2 - mid;
            }
        }

        while (p2 >= mid + 1) {
            help[--index] = nums[p2--];
        }

        while (p1 >= left) {
            help[--index] = nums[p1--];
        }

        System.arraycopy(help, 0, nums, left, help.length);

        return curNum;
    }
}
