package org.study.coding.class07;

/**
 * @author phil
 * @date 2022-03-24 15:32:48
 */
public class MainTest03 {

    public static void main(String[] args) {
        System.out.println(maximumGap(new int[]{1,3,5,2,4,8,9}));
        System.out.println(Code03_MaxGap.maximumGap(new int[]{1,3,5,2,4,8,9}));
    }

    private static int maximumGap(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        if (max == min) {
            return 0;
        }
        int len = nums.length;
        boolean[] hasNum = new boolean[len + 1];
        int[] mins = new int[len + 1];
        int[] maxs = new int[len + 1];

        int bid;
        for (int num : nums) {
            bid = bucket(num, len, min, max);
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], num) : num;
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], num) : num;
            hasNum[bid] = true;
        }


        int ans = 0;
        int lastMax = maxs[0];
        for (int i = 1; i <= len; i++) {
            if (hasNum[i]){
                ans = Math.max(ans,mins[i]-lastMax);
                lastMax = maxs[i];
            }
        }
        return ans;
    }

    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

}
