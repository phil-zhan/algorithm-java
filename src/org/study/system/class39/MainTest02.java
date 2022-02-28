package org.study.system.class39;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author phil
 * @date 2021/10/18 16:26
 */
public class MainTest02 {


    public static int ways1(int[] arr, int bag) {
        return process1(arr, 0, bag);
    }

    public static int process1(int[] arr, int index, int rest) {
        if (rest < 0) {
            return -1;
        }
        if (index == arr.length) {
            return 1;
        }

        int next1 = process1(arr, index + 1, rest);

        int next2 = process1(arr, index + 1, rest - arr[index]);

        return next1 + (next2 == -1 ? 0 : next2);
    }

    public static int ways2(int[] arr, int bag) {
        int length = arr.length;
        int[][] dp = new int[length + 1][bag + 1];
        for (int i = 0; i <= bag; i++) {
            dp[length][i] = 1;
        }
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 0; j <= bag; j++) {
                dp[i][j] = dp[i + 1][j] + ((j - arr[i]) >= 0 ? dp[i + 1][j - arr[i]] : 0);
            }
        }

        return dp[0][bag];
    }


    public static int process2(int[] arr, int index, int end, long sum, int bag, TreeMap<Long, Long> treeMap) {
        if (sum > bag) {
            return 0;
        }
        if (index > end) {
            if (sum == 0) {
                return 0;
            } else {
                // 存表
                if (treeMap.containsKey(sum)) {
                    treeMap.put(sum, treeMap.get(sum) + 1);
                } else {
                    treeMap.put(sum, 1L);
                }
                return 1;
            }
        }

        int next = process2(arr, index + 1, end, sum, bag, treeMap);
        next += process2(arr, index + 1, end, sum + arr[index], bag, treeMap);
        return next;
    }


    public static int ways4(int[] arr, int bag) {
        if (null == arr || arr.length == 0) {
            return 0;
        }

        if (arr.length == 1) {
            return arr[0] > bag ? 1 : 2;
        }

        int mid = (arr.length - 1) >> 1;

        // 左半
        TreeMap<Long, Long> leftTreeMap = new TreeMap<>();
        int ways = process2(arr, 0, mid, 0, bag, leftTreeMap);

        // 右半
        TreeMap<Long, Long> rightTreeMap = new TreeMap<>();
        ways += process2(arr, mid + 1, arr.length - 1, 0, bag, rightTreeMap);


        // 计算小于等于
        TreeMap<Long, Long> rightPre = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : rightTreeMap.entrySet()) {
            pre += entry.getValue();
            rightPre.put(entry.getKey(), pre);
        }


        // 统计交叉
        for (Map.Entry<Long, Long> entry : leftTreeMap.entrySet()) {
            long leftSum = entry.getKey();
            long leftSumOfWays = entry.getValue();

            Long floor = rightPre.floorKey(bag - leftSum);
            if (floor != null) {
                ways += (leftSumOfWays * (rightPre.get(floor)));
            }
        }

        // 将一个也没选的也考虑进去
        return ways + 1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 5, 4, 3, 2, 3, 4, 3, 4,
                5, 6, 4, 5, 6, 4, 4, 3, 2, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int w = 8;

        // 暴力递归
        long start1 = System.currentTimeMillis();
        System.out.println(ways1(arr, w));
        long start2 = System.currentTimeMillis();
        System.out.println("暴力递归"+(start2-start1));


        // 动态规划
        long start3 = System.currentTimeMillis();
        System.out.println(ways2(arr, w));
        long start4 = System.currentTimeMillis();
        System.out.println("动态规划"+(start4-start3));

        // 考虑性能，分治+暴力递归
        long start5 = System.currentTimeMillis();
        System.out.println(ways4(arr, w));
        long start6 = System.currentTimeMillis();
        System.out.println("分治+暴力递归"+(start6-start5));

        System.out.println(Code02_SnacksWays.ways3(arr, w));

    }
}
