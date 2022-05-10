package org.study.coding.class14;

import java.util.Arrays;

public class MainTest06 {

    public static void main(String[] args) {
        MainTest06 mainTest06 = new MainTest06();
        int[] nums = new int[]{1, 2, 3, 4, 5,7};

        System.out.println(Arrays.toString(nums));

        System.out.println(mainTest06.firstMissingPositive(nums));
    }

    public int firstMissingPositive(int[] nums) {
        int left = 0;
        int right = nums.length;
        while (left != right) {
            if (nums[left] == left + 1) {
                left++;
            } else if (nums[left] <= left || nums[left] > right || nums[nums[left] - 1] == nums[left]) {
                swap(nums, left, --right);
            } else {
                swap(nums, left, nums[left] - 1);
            }
        }

        return left + 1;
    }


    public void swap(int[] nums, int i1, int i2) {
        if (nums[i1] == nums[i2]) {
            return;
        }
        nums[i1] = nums[i1] ^ nums[i2];
        nums[i2] = nums[i1] ^ nums[i2];
        nums[i1] = nums[i1] ^ nums[i2];
    }
}
