package org.study.coding.class04;

import java.util.Arrays;

public class MainTest04 {

    public static void main(String[] args) {

        int testTimes = 1000000;
        System.out.println("begin test!");
        for (int i = 0; i < testTimes; i++) {
            int[] nums = new MainTest04().randomArray(10, 100000);

            int ans1 = new MainTest04().subSqeMaxSumNoNext(nums);
            int ans2 = new MainTest04().subSqeMaxSumNoNext2(nums);
            int ans3 = Code04_SubArrayMaxSumFollowUp.subSqeMaxSumNoNext(nums);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                System.out.println("ans1:" + ans1);
                System.out.println("ans2:" + ans2);
                System.out.println("ans3:" + ans3);
                System.out.println(Arrays.toString(nums));
                System.out.println("Oops!");
            }
        }

        System.out.println("end test!");
    }

    public int subSqeMaxSumNoNext(int[] nums) {

        return process(nums, nums.length - 1);
    }


    public int process(int[] nums, int index) {
        if (index < 0) {
            return Integer.MIN_VALUE;
        }

        int p1 = process(nums, index - 1);
        int p2 = index < 2 ? Integer.MIN_VALUE : nums[index] + process(nums, index - 2);

        return Math.max(Math.max(p1, p2), nums[index]);
    }


    /**
     * nums[index...len] 随机选
     * 也可以考虑从前到后
     *
     * @since 2022-03-07 16:01:08
     */
    public int subSqeMaxSumNoNext2(int[] nums) {

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        int ans = Integer.MIN_VALUE;
        for (int index = 0;index< nums.length;index++) {

            int p1 = first;
            int p2 = index < 2 ?Integer.MIN_VALUE:nums[index] + second;
            ans = Math.max(Math.max(p1, p2), nums[index]);

            second = first;
            first = ans;
        }

        return ans;

    }

    public int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] nums = new int[len];

        for (int i = 0; i < len; i++) {
            nums[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }

        return nums;
    }
}
