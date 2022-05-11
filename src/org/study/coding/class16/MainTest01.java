package org.study.coding.class16;

import java.util.HashMap;
import java.util.HashSet;

public class MainTest01 {

    public static void main(String[] args) {
        int N = 20;
        int M = 10;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * (N + 1));
            int[] arr = Code01_IsSum.randomArray(size, M);
            int sum = (int) (Math.random() * ((M << 1) + 1)) - M;
            boolean ans1 = new MainTest01().isSum1(arr, sum);
            boolean ans2 = new MainTest01().isSum2(arr, sum);
            boolean ans3 = Code01_IsSum.isSum3(arr, sum);
            boolean ans4 = new MainTest01().isSum4(arr, sum);
            if (ans1 ^ ans2 || ans3 ^ ans4 || ans1 ^ ans3) {
                System.out.println("出错了！");
                System.out.print("arr : ");
                for (int num : arr) {
                    System.out.print(num + " ");
                }
                System.out.println();
                System.out.println("sum : " + sum);
                System.out.println("方法一答案 : " + ans1);
                System.out.println("方法二答案 : " + ans2);
                System.out.println("方法三答案 : " + ans3);
                System.out.println("方法四答案 : " + ans4);
                break;
            }
        }
        System.out.println("测试结束");
    }

    public boolean isSum1(int[] nums, int sum) {
        if (sum == 0) {
            return true;
        }
        if (nums == null || nums.length == 0) {
            return false;
        }

        return process1(nums, nums.length - 1, sum);
    }

    public boolean process1(int[] nums, int index, int sum) {

        if (sum == 0) {
            return true;
        }
        if (index == -1) {
            return false;
        }
        return process1(nums, index - 1, sum) || process1(nums, index - 1, sum - nums[index]);
    }


    public boolean isSum2(int[] nums, int sum) {
        if (sum == 0) {
            return true;
        }
        if (nums == null || nums.length == 0) {
            return false;
        }

        return process2(nums, nums.length - 1, sum, new HashMap<>());
    }

    public boolean process2(int[] nums, int index, int sum, HashMap<Integer, HashMap<Integer, Boolean>> dp) {
        if (dp.containsKey(index) && dp.get(index).containsKey(sum)) {
            return dp.get(index).get(sum);
        }

        boolean ans = false;
        if (sum == 0) {
            ans = true;
        } else if (index != -1) {
            ans = process2(nums, index - 1, sum, dp) || process2(nums, index - 1, sum - nums[index], dp);
        }

        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(sum, ans);

        return ans;
    }

    public static boolean isSum3(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        int min = 0;
        int max = 0;
        for (int num : arr) {
            min += Math.min(num, 0);
            max += Math.max(num, 0);
        }
        if (sum < min || sum > max) {
            return false;
        }

        int N = arr.length;
        boolean[][] dp = new boolean[N][max - min + 1];
        dp[0][-min] = true;
        dp[0][arr[0] - min] = true;
        for (int i = 1; i < N; i++) {
            for (int j = min; j <= max; j++) {
                dp[i][j - min] = dp[i - 1][j - min];
                int next = j - min - arr[i];
                dp[i][j - min] |= (next >= 0 && next <= max - min && dp[i - 1][next]);
            }
        }
        return dp[N - 1][sum - min];
    }

    public boolean isSum4(int[] nums, int sum) {
        if (sum == 0) {
            return true;
        }
        if (nums == null || nums.length == 0) {
            return false;
        }
        if (nums.length == 1) {
            return nums[0] == sum;
        }
        HashSet<Integer> leftSet = new HashSet<>();
        HashSet<Integer> rightSet = new HashSet<>();
        int mid = nums.length >> 1;

        // 0...mid-1
        process4(nums, 0, mid - 1, 0, leftSet);

        // mid...n-1
        process4(nums, mid, nums.length - 1, 0, rightSet);

        for (Integer left : leftSet) {
            if (rightSet.contains(sum - left)) {
                return true;
            }
        }
        return false;
    }

    public void process4(int[] nums, int index, int end, int preSum, HashSet<Integer> sumSet) {
        if (index == end + 1) {
            sumSet.add(preSum);
            return;
        }
        process4(nums, index + 1, end, preSum + nums[index], sumSet);
        process4(nums, index + 1, end, preSum, sumSet);
    }
}
