package org.study.coding.class36;

import java.util.HashMap;

public class MainTest02 {

    public static int gcd(int a, int b) {

        return b == 0 ? a : gcd(b, a % b);
    }

    public static int[] split(int[] arr) {
        HashMap<Integer, HashMap<Integer, Integer>> pre = new HashMap<>();
        int len = arr.length;
        int[] ans = new int[len];
        int zeroNum = 0;
        int oneNum = 0;
        for (int i = 0; i < len; i++) {
            if (arr[i] == 0) {
                zeroNum++;
            } else {
                oneNum++;
            }
            if (oneNum == 0 || zeroNum == 0) {
                ans[i] = i + 1;
            } else {
                int gcd = gcd(zeroNum, oneNum);
                int a = zeroNum / gcd;
                int b = oneNum / gcd;

                if (!pre.containsKey(a)) {
                    pre.put(a, new HashMap<>());
                }

                if (!pre.get(a).containsKey(b)) {
                    pre.get(a).put(b, 1);
                } else {
                    pre.get(a).put(b, pre.get(a).get(b) + 1);
                }
                ans[i] = pre.get(a).get(b);
            }

        }

        return ans;
    }


    public static void main(String[] args) {
        int[] arr = { 0, 1, 0, 1, 0, 1, 1, 0 };
        int[] ans = split(arr);
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
    }
}
