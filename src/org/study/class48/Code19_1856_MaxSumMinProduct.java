package org.study.class48;

/**
 * @author phil
 * @date 2022/2/17 13:16
 */
public class Code19_1856_MaxSumMinProduct {

    public static void main(String[] args) {
        int max = new Code19_1856_MaxSumMinProduct().maxSumMinProduct(new int[]{2,5,4,2,4,5,3,1,2,4});
        // int max = new Code19_1856_MaxSumMinProduct().maxSumMinProduct(new int[]{1,2,3,2});

        System.out.println(max);
    }
    public int maxSumMinProduct(int[] nums) {

        int len = nums.length;
        long max = Integer.MIN_VALUE;

        long[] sums = new long[len];
        sums[0] = nums[0];
        for (int i = 1; i < len; i++) {
            sums[i] = sums[i - 1] + nums[i];
        }

        int[] stack = new int[len];
        int si = -1;

        for (int right = 0; right < len; right++) {
            while (si != -1 && nums[right] <= nums[stack[si]]) {
                // process
                max = Math.max(max, (long) nums[stack[si]] * (sums[right-1] - (si == 0 ? 0 : sums[stack[si - 1]])));
                si--;
            }

            stack[++si] = right;
        }

        // clear
        while (si != -1) {
            // process
            max = Math.max(max, (long) nums[stack[si]] * (sums[len-1] - (si == 0 ? 0 : sums[stack[si - 1]])));
            si--;
        }

        return (int) (max % 1000000007);
    }
}
