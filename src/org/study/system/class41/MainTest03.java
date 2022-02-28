package org.study.system.class41;

/**
 * @author phil
 * @since 2022-0227 22:03:00
 */
public class MainTest03 {

    public static void main(String[] args) {
        int ans1 = new MainTest03().mergeStones1(new int[]{2, 3, 4, 5, 76, 2, 1, 3, 5, 7});
        int ans2 = new MainTest03().mergeStones2(new int[]{2, 3, 4, 5, 76, 2, 1, 3, 5, 7});
        int ans3 = Code03_StoneMerge.min2(new int[]{2, 3, 4, 5, 76, 2, 1, 3, 5, 7});
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(ans3);
    }


    public int mergeStones1(int[] stones) {

        // [L...R]可以合并k次数。最小代价是多少
        // min([L...R-1],)
        int[] sum = new int[stones.length];
        sum[0] = stones[0];
        for (int i = 1; i < stones.length; i++) {
            sum[i] = sum[i - 1] + stones[i];
        }
        return process(sum, 0, stones.length - 1);
    }

    public int process(int[] sum, int left, int right) {
        if (left == right) {
            return 0;
        }

        int nextAns = Integer.MAX_VALUE;
        for (int leftEnd = left; leftEnd < right; leftEnd++) {
            nextAns = Math.min(nextAns, process(sum, left, leftEnd) + process(sum, leftEnd + 1, right));
        }

        return nextAns + sum[right] - (left == 0 ? 0 : sum[left - 1]);
    }


    public int mergeStones2(int[] stones) {
        int len = stones.length;

        int[] sum = new int[len];
        sum[0] = stones[0];
        for (int i = 1; i < len; i++) {
            sum[i] = sum[i - 1] + stones[i];
        }

        int[][] dp = new int[len][len];

        for (int i = len-2; i >=0; i--) {
            for (int j = i + 1; j < len; j++) {
                int nextAns = Integer.MAX_VALUE;
                for (int leftEnd = i; leftEnd < j; leftEnd++) {
                    nextAns = Math.min(nextAns, dp[i][leftEnd] + dp[leftEnd + 1][j]);
                }

                dp[i][j] = nextAns + sum[j] - (i == 0 ? 0 : sum[i - 1]);
            }
        }

        return dp[0][stones.length - 1];
    }


}
