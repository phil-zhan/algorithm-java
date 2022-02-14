package org.study.class24;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author phil
 * @date 2021/8/20 9:46
 */
public class MainTest01 {

    // 暴力的对数器方法
    public static int[] right1(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int N = arr.length;
        int[] res = new int[N - w + 1];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < N) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);

            }
            res[index++] = max;
            L++;
            R++;
        }
        return res;
    }

    public static int[] right2(int[] arr, int w) {

        if (null == arr || w < 1 || arr.length < w) {
            return null;
        }

        int n = arr.length;
        int[] res = new int[n - w + 1];
        LinkedList<Integer> queueMax = new LinkedList<>();
        int index = 0;

        for (int right = 0; right < n; right++) {
            while (!queueMax.isEmpty() && arr[queueMax.peekLast()] <= arr[right]) {
                queueMax.pollLast();
            }

            queueMax.addLast(right);

            if (!queueMax.isEmpty() && queueMax.peekFirst() == right - w) {
                queueMax.pollFirst();
            }

            if (right >= w - 1) {
                res[index++] = arr[queueMax.peekFirst()];
            }

        }


        return res;
    }

    /**
     * https://leetcode-cn.com/problems/sliding-window-maximum/
     * 给你一个整数数组 nums，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k个数字。滑动窗口每次只向右移动一位。
     * <p>
     * 返回 滑动窗口中的最大值 。
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (null == nums || nums.length < k) {
            return null;
        }
        int len = nums.length;
        int[] ans = new int[len - k + 1];
        int index = 0;

        // 双端队列
        LinkedList<Integer> maxQueue = new LinkedList<>();

        for (int right = 0; right < nums.length; right++) {

            while (!maxQueue.isEmpty() && nums[right] >= nums[maxQueue.peekLast()]) {
                maxQueue.pollLast();
            }
            maxQueue.addLast(right);


            if (!maxQueue.isEmpty() && maxQueue.peekFirst() == right - k) {
                maxQueue.pollFirst();
            }

            if (right >= k - 1) {
                ans[index++] = nums[maxQueue.peekFirst()];
            }
        }

        return ans;

    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = right1(arr, w);
            int[] ans2 = right2(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");


        int[] ints = new MainTest01().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        System.out.println(Arrays.toString(ints));
    }
}
