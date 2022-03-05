package org.study.coding.class03;

import java.util.Arrays;

/**
 * @author phil
 * @since 2022-0305 11:06:23
 */
public class MainTest04 {

    public int maxPairNum(int[] arr, int k) {
        if (arr == null || arr.length < 2 || k < 0) {
            return 0;
        }
        Arrays.sort(arr);
        int left = 0;
        int right = 0;
        int ans = 0;
        int len = arr.length;
        boolean[] used = new boolean[len];
        while (left < len && right < len) {
            if (used[left]) {
                left++;
            } else if (left >= right) {
                right++;
            } else {
                // 不止一个数，而且都没用过！
                int distance = arr[right] - arr[left];
                if (distance > k) {
                    left++;
                } else if (distance < k) {
                    right++;
                } else {
                    ans++;
                    used[right++] = true;
                    left++;
                }
            }

        }
        return ans;
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int maxK = 5;
        int testTime = 1000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * (maxLen + 1));
            int[] arr = Code04_MaxPairNumber.randomArray(N, maxValue);
            int[] arr1 = Code04_MaxPairNumber.copyArray(arr);
            int[] arr2 = Code04_MaxPairNumber.copyArray(arr);
            int k = (int) (Math.random() * (maxK + 1));
            int ans1 = Code04_MaxPairNumber.maxPairNum1(arr1, k);
            int ans2 = new MainTest04().maxPairNum(arr2, k);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                Code04_MaxPairNumber.printArray(arr);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

}
