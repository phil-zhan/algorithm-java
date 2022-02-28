package org.study.system.class05;

/**
 * @author phil
 * @date 2022/1/6 12:24
 */
public class MainTest01 {

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 2, 4, 4, 3, 2, 3, 4, -7, 6, 7, 8, 33, 44, 2, 2, 3, 4, 543, 43, 55, 2, 2, 3, 4, -100, 7, 8, 9, 4, 3, 2};
        int count = new MainTest01().countRangeSum(nums, 10, 20);
        System.out.println(count);

    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        return processCountRangeSum(sum, 0, sum.length - 1, lower, upper);
    }

    private int processCountRangeSum(long[] sum, int left, int right, int lower, int upper) {
        if (left >= right) {
            return sum[left] >= lower && sum[left] <= upper ? 1 : 0;
        }

        int mid = left + ((right - left) >> 1);
        return processCountRangeSum(sum, left, mid, lower, upper) +
                processCountRangeSum(sum, mid + 1, right, lower, upper) +
                mergeCountRangeSum(sum, left, mid, right, lower, upper);
    }

    private int mergeCountRangeSum(long[] sum, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        int windowLeft = left;
        int windowRight = left;
        for (int i = mid + 1; i <= right; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            while (windowLeft <= mid && sum[windowLeft] < min) {
                // 左边必须要踩上去，等于的时候，也不要再走了
                windowLeft++;
            }
            while (windowRight <= mid && sum[windowRight] <= max) {
                // 右边要尽可能踩下去，等于的时候也要尽可能的往下踩
                windowRight++;
            }

            // 到此处 sum[windowLeft] 是大于mid的最左边的数
            // 到此处 sum[windowRight] 是大于max的最左边的数
            // 也就是一个左闭右开的场景
            ans += windowRight - windowLeft;
        }


        long[] help = new long[right - left + 1];
        int index = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            if (sum[p1] <= sum[p2]) {
                help[index++] = sum[p1++];
            } else {
                help[index++] = sum[p2++];
            }
        }

        while (p1 <= mid) {
            help[index++] = sum[p1++];
        }
        while (p2 <= right) {
            help[index++] = sum[p2++];
        }

        System.arraycopy(help, 0, sum, left, help.length);

        return ans;
    }
}
