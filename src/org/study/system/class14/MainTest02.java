package org.study.system.class14;

import java.util.PriorityQueue;

/**
 * 拆分黄金最少钱。按照哈夫曼树去差分最省
 * @author phil
 * @date 2021/7/8 16:19
 */
public class MainTest02 {


    // 纯暴力！
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价
    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    private static int lessMoney2(int[] arrs){

        // 优先级队列就是小跟堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int arr:arrs) {
            queue.add(arr);
        }
        int sum = 0;
        int cur;
        while (queue.size()>1){

            // 取出两个最小的，求和
            cur = queue.poll() + queue.poll();
            sum += cur;

            // 将求和后的结果放回到小跟堆中
            queue.add(cur);

        }

        return sum;
    }



    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
