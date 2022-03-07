package org.study.coding.class04;

import java.util.Arrays;

public class MainTest03 {
    public static void main(String[] args) {
        int ans = new MainTest03().maxSum1(new int[][]{
                {1, 2, 3},
                {-4, -5, -6},
                {7, 8, 9}
        });
        int[] ans2 = new MainTest03().maxSum2(new int[][]{
                {1, 2, 3},
                {-4, -5, -6},
                {7, 8, 9}
        });
        System.out.println(ans);
        System.out.println(Arrays.toString(ans2));
    }

    public int maxSum1(int[][] nums) {
        if (nums == null || nums.length == 0 || nums[0] == null || nums[0].length == 0) {
            return 0;
        }
        int rowNum = nums.length;
        int colNum = nums[0].length;
        int ans = Integer.MIN_VALUE;

        for (int row1 = 0; row1 < rowNum; row1++) {

            int[] sum = new int[colNum];
            for (int row2 = row1; row2 < rowNum; row2++) {

                for (int col = 0; col < colNum; col++) {
                    sum[col] += nums[row2][col];
                }

                ans = Math.max(ans, maxSubArray(sum));
            }
        }

        return ans;
    }

    public int[] maxSum2(int[][] matrix) {

        int rowNum = matrix.length;
        int colNum = matrix[0].length;
        int max = Integer.MIN_VALUE;

        int cur;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        for (int row1 = 0; row1 < rowNum; row1++) {

            int[] sum = new int[colNum];

            for (int row2 = row1; row2 < rowNum; row2++) {

                cur = 0;
                int begin = 0;

                for (int col = 0; col < colNum; col++) {
                    sum[col] += matrix[row2][col];
                    cur += sum[col];
                    if (max < cur) {
                        max = cur;
                        a = row1;
                        b = begin;
                        c = row2;
                        d = col;
                    }
                    if (cur < 0) {
                        cur = 0;
                        begin = col + 1;
                    }
                }
            }
        }

        return new int[]{a, b, c, d};
    }

    private int maxSubArray(int[] nums) {

        int pre = nums[0];
        int ans = pre;
        for (int index = 1; index < nums.length; index++) {
            pre = Math.max(pre + nums[index], nums[index]);
            ans = Math.max(pre, ans);
        }
        return ans;
    }
}
