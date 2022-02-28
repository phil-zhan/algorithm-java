package org.study.system.class21;

/**
 * @author phil
 * @date 2021/8/9 13:16
 */
public class MainTest02 {

    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }


    public static int process(int[] arr, int index, int rest) {
        // 考虑剩下的面值小于0，当前方案不可取
        if (rest < 0) {
            return 0;
        }
        // 要的是正好
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }

        // 当前面值要或者不要
        return process(arr, index + 1, rest - arr[index]) + process(arr, index + 1, rest);

    }

    public static int dp(int[] arr, int aim) {
        int length = arr.length;
        if (aim == 0) {
            return 1;
        }

        int[][] dp = new int[length + 1][aim + 1];
        dp[length][0] = 1;

        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + ((rest - arr[index]) >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }


        return dp[0][aim];
    }


    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                /* break;*/
            }
        }
        System.out.println("测试结束");
    }

}
