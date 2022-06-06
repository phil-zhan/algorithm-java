package org.study.coding.class36;

import java.util.Arrays;

public class MainTest07 {

    public static int pick(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int sum = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            sum = (sum << 1) + nums[i];
        }

        return sum;
    }

    public static void main(String[] args) {
        System.out.println(pick(new int[]{1, 2, 3}));
    }
}
