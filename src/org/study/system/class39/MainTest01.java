package org.study.system.class39;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author phil
 * @date 2021/10/15 17:15
 */
public class MainTest01 {

    public static int max1(int[] arr, int m) {

        HashSet<Long> set = new HashSet<>();
        process1(arr,0,0,set);
        int max = 0;

        for (Long sum:set) {
            max = (int) Math.max(max,sum % m);
        }
        return max;
    }

    public static void process1(int[] arr,int index,long sum,HashSet<Long> set){
        if (index == arr.length){
            set.add(sum);
            return;
        }
        process1(arr,index+1,sum,set);
        process1(arr,index+1,sum+arr[index],set);
    }


    public static int max2(int[] arr, int m) {
        int len = arr.length;

        int sum = 0;
        for (int j : arr) {
            sum += j;
        }

        // dp[i][j] : 0到i的范围上随意选择，能否组装成 j 的累加和
        boolean[][] dp = new boolean[len][sum+1];
        for (int i = 0; i < len; i++) {
            dp[i][0] = true;
        }

        dp[0][arr[0]] = true;
        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i-1][j];
                if (j-arr[i] >= 0){
                    dp[i][j] |= dp[i-1][j-arr[i]];
                }
            }
        }


        int ans = 0;
        for (int i = 0; i <= sum; i++) {
            if (dp[len-1][i]){
                ans = Math.max(ans,i % m);
            }
        }

        return ans;
    }

    public static int max3(int[] arr, int m) {
        int length = arr.length;
        boolean[][] dp = new boolean[length][m];
        for (int i = 0; i < length; i++) {
            dp[i][0] = true;
        }

        dp[0][arr[0] % m] = true;

        for (int i = 1; i < length; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                if (cur > j) {
                    dp[i][j] |= dp[i - 1][m + j - cur];
                } else {
                    dp[i][j] |= dp[i - 1][j - cur];
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[length - 1][i]) {
                ans = i;
            }
        }

        return ans;
    }

    public static int max4(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int mid = (arr.length - 1) / 2;
        TreeSet<Integer> sortSet1 = new TreeSet<>();
        process4(arr, 0, 0, mid, m, sortSet1);
        TreeSet<Integer> sortSet2 = new TreeSet<>();
        process4(arr, mid + 1, 0, arr.length - 1, m, sortSet2);
        int ans = 0;
        for (Integer leftMod : sortSet1) {
            ans = Math.max(ans, leftMod + sortSet2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    // 从index出发，最后有边界是end+1，arr[index...end]
    public static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
        if (index == end + 1) {
            sortSet.add(sum % m);
        } else {
            process4(arr, index + 1, sum, end, m, sortSet);
            process4(arr, index + 1, sum + arr[index], end, m, sortSet);
        }
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = Code01_SubsquenceMaxModM.generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
