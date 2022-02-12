package org.study.class23;

/**
 * @author phil
 * @date 2021/8/18 18:44
 */
public class MainTest01 {

    public static int right(int[] arr) {
        if (null == arr || arr.length < 2) {
            return 0;
        }

        int sum = 0;

        for (int ele : arr) {
            sum += ele;
        }

        return process(arr, 0, sum >> 1);
    }


    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        }

        int p1 = process(arr, index + 1, rest);
        int p2 = Integer.MIN_VALUE;

        if (arr[index] <= rest) {
            p2 = arr[index] + process(arr, index + 1, rest - arr[index]);
        }

        return Math.max(p1, p2);
    }


    public static int dp(int[] arr) {
        if (null == arr || arr.length < 2) {
            return 0;
        }
        int length = arr.length;
        int sum = 0;
        for (int ele : arr) {
            sum += ele;
        }
        sum = sum >> 1;
        int[][] dp = new int[length + 1][sum + 1];
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = Integer.MIN_VALUE;

                if (arr[index] <= rest) {
                    p2 = arr[index] + dp[index + 1][rest - arr[index]];
                }

                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];
    }

    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
