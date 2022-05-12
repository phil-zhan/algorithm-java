package org.study.system.class01;

import java.util.Arrays;

/**
 * @author phil
 * @date 2021-03-10 09:23:00
 */
public class Code02_InsertSort {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
        new Code02_InsertSort().sort(nums);

        System.out.println(Arrays.toString(nums));
    }

    public void sort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        for (int end = 1; end < nums.length; end++) {
            for (int pre = end - 1; pre >= 0 && nums[pre] > nums[pre + 1]; pre--) {
                nums[pre] = nums[pre] ^ nums[pre + 1];
                nums[pre + 1] = nums[pre] ^ nums[pre + 1];
                nums[pre] = nums[pre] ^ nums[pre + 1];
            }
        }
    }
}
