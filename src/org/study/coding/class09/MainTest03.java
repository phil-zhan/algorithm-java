package org.study.coding.class09;

import java.util.Arrays;

/**
 * @author phil
 * @date 2022-04-06 14:48:09
 */
public class MainTest03 {

    public static void main(String[] args) {

        int testTimes = 1000000;
        int maxLen = 100;
        int maxVal = 50000;
        for (int i = 0; i < testTimes; i++) {
            int[] nums = randomIntArray(maxLen,maxVal);
            int ans1 = Code03_LongestIncreasingSubsequence.lengthOfLIS(nums);
            int ans2 = lengthOfLIS(nums);

            if (ans1 != ans2){
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                System.out.println(Arrays.toString(nums));
                System.out.println("ans1:"+ans1);
                System.out.println("ans2:"+ans2);
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
            }
        }
        System.out.println("!!!!!!!!!!Nice!!!!!!!!!!");
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] end = new int[nums.length];
        end[0] = nums[0];
        int endRight = 0;
        int left = 0;
        int right = 0;
        int mid = 0;
        int max = 1;

        for (int index = 1; index < nums.length; index++) {
            left = 0;
            right = endRight;
            while (left <= right) {
                mid = left + ((right - left) >> 1);
                if (nums[index] > end[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            endRight = Math.max(left, endRight);
            end[left] = nums[index];
            max = Math.max(max, left + 1);

        }
        return max;
    }

    public static int[] randomIntArray(int maxLen, int maxVal) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] nums = new int[len];

        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * (maxVal+1));
        }
        return nums;
    }
}
