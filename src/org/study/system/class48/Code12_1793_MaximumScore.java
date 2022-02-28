package org.study.system.class48;

/**
 * @author phil
 * @date 2022/2/16 10:32
 */
public class Code12_1793_MaximumScore {

    public static void main(String[] args) {
        int max = new Code12_1793_MaximumScore().maximumScore(new int[]{5,5,4,5,4,1,1,1}, 0);
        System.out.println(max);
    }

    public int maximumScore(int[] nums, int k) {
        int len = nums.length;
        int[] stack = new int[len];

        int max = 0;
        int si = -1;
        for (int i = 0; i < len; i++) {
            while (si != -1 && nums[i] <= nums[stack[si]]) {

                // 考虑出栈
                // 左边 nums[stack[si-1]]
                // 右边 nums[i]
                // 最小值 nums[si]
                int cur = stack[si--];
                int left = si == -1 ? -1 : stack[si];
                if (left < k && k < i) {
                    max = Math.max(max, (i - left -1 ) * nums[cur]);
                }
            }
            stack[++si] = i;


        }

        while (si != -1) {
            // 考虑出栈
            // 左边 nums[stack[si-1]]
            // 右边 nums[i]
            // 最小值 nums[si]
            int cur = stack[si--];
            int right = nums.length;
            int left = si == -1 ? -1 : stack[si];

            if (left < k && k <right) {
                max = Math.max(max, (right - left-1) * nums[cur]);
            }
        }

        return max;
    }
}
