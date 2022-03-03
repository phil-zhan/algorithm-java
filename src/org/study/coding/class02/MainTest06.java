package org.study.coding.class02;

/**
 * @author phil
 * @since 2022-0303 21:56:34
 */
public class MainTest06 {

    public int findUnsortedSubarray(int[] nums){
        if (nums == null || nums.length < 2){
            return 0;
        }

        int len = nums.length;

        int right = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            if (nums[i] < max){
                right = i;
            }
            max = Math.max(nums[i],max);
        }

        int left = len;
        int min = Integer.MAX_VALUE;
        for (int i = len-1; i >=0 ; i--) {
            if (nums[i] > min){
                left = i;
            }
            min = Math.min(min, nums[i]);
        }

        return Math.max(0, right - left + 1);
    }
}
