package org.study.coding.class16;

import java.util.Arrays;
import java.util.HashSet;

public class Code02_SmallestUnFormedSum {

    /**
     * 给定一个正数数组arr，
     * 返回arr的子集不能累加出的最小正数
     * 1）正常怎么做？
     * 2）如果arr中肯定有1这个值，怎么做？
     * <p>
     * 1）正常解法：
     * 上题的动态规划搞完。
     * dp[i][j]：0...i 自由选择，能不能搞定 j 这个累加和 。
     * 完成dp表后。看最后一行。看[0...n-1] 自由选择，能不能搞定1，能不能搞定2... 能不能搞定 max
     * 第一个不能搞定的数，就是ans
     * <p>
     * 2）
     * 先将arr排序
     * 因为是含有1的正数数组。排完序之后，左边第0位置的数，肯定是1
     * 定义一个变量range
     * 如果range=k ：表示从1...k的正数。都能用数组的数累加出来
     * <p>
     * range初始值等于1。
     * 如果1位置的数也是1.那么range扩充到2
     * 如果2位置的数是2，那么range扩充到4
     * <p>
     * 来到普遍的 i 位置。【假设arr[i] = 17 】
     * 如果在 i-1 位置的时候，range等于100，那么在 i 位置的时候，就能把 range 扩充到 117
     * 在 i-1 的位置。能搞定[1...100],也就意味着[84...100],都是能被搞定的。那么都加一个17，也就能搞定 117 .成功推高range
     * 如果之前的range是100。此时的 arr[i] 等于101，也是能搞定的，能将range推到201
     * <p>
     * 但是如果之前的range是100.此时的arr[i] > 101.就推不下去了。因为101是永远无法搞定的
     * <p>
     * 抽象化：
     * 当前来到 i 位置
     * 前面 i-1 个位置的 range = a
     * arr[i] = b
     * 如果 b<= a+1的话。 range 就能被推高到 a+b
     * 如果 b>= a+2 。那么 a+1 是永远无法被搞定的。可以直接返回了
     *
     *
     *
     * 当然，直接解的时候，也可以遍历右边数组，看arr中是否含有1.不含1的话，直接返回 1
     * 含有1的haul，就按照上面第二种可能求解
     *
     * @since 2022-03-17 10:24:25
     */
    public static int unformedSum1(int[] arr) {
        // 正常解法，递归版本

        if (arr == null || arr.length == 0) {
            return 1;
        }
        HashSet<Integer> set = new HashSet<Integer>();
        process(arr, 0, 0, set);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != arr.length; i++) {
            min = Math.min(min, arr[i]);
        }
        for (int i = min + 1; i != Integer.MIN_VALUE; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return 0;
    }

    public static void process(int[] arr, int i, int sum, HashSet<Integer> set) {
        if (i == arr.length) {
            set.add(sum);
            return;
        }
        process(arr, i + 1, sum, set);
        process(arr, i + 1, sum + arr[i], set);
    }


    /**
     * 正常解法：dp版本
     * @since 2022-03-17 11:01:17
     */
    public static int unformedSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        int sum = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            min = Math.min(min, arr[i]);
        }
        // boolean[][] dp ...
        int N = arr.length;
        boolean[][] dp = new boolean[N][sum + 1];
        // arr[0..i] 0
        for (int i = 0; i < N; i++) {

            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j] || ((j - arr[i] >= 0) ? dp[i - 1][j - arr[i]] : false);
            }
        }
        for (int j = min; j <= sum; j++) {
            if (!dp[N - 1][j]) {
                return j;
            }
        }
        return sum + 1;
    }



    // 已知arr中肯定有1这个数
    public static int unformedSum3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // O (N * logN)
        Arrays.sort(arr);
        int range = 1;
        // arr[0] == 1
        for (int i = 1; i != arr.length; i++) {
            if (arr[i] > range + 1) {

                // 中途遇到了搞定不了的
                return range + 1;
            } else {
                range += arr[i];
            }
        }

        // 中途没有遇到搞定不了的。数组用完了。range+1就是搞定不了的
        return range + 1;
    }

    public static int[] generateArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) + 1;
        }
        return res;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 27;
        int max = 30;
        int[] arr = generateArray(len, max);
        printArray(arr);
        long start = System.currentTimeMillis();
        System.out.println(unformedSum1(arr));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        System.out.println("======================================");

        start = System.currentTimeMillis();
        System.out.println(unformedSum2(arr));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");
        System.out.println("======================================");

        System.out.println("set arr[0] to 1");
        arr[0] = 1;
        start = System.currentTimeMillis();
        System.out.println(unformedSum3(arr));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + " ms");

    }
}
