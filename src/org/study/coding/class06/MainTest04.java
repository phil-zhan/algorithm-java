package org.study.coding.class06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author phil
 * @date 2022-03-23 13:31:49
 */
public class MainTest04 {

    public static void main(String[] args) {
        int testTime = 150000;
        int maxSize = 12;
        int maxValue = 5;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = Code04_MostXorZero.generateRandomArray(maxSize, maxValue);
            int res = mostXor(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                Code04_MostXorZero.printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    public static int comparator(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        int[] eor = new int[nums.length];
        eor[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            eor[i] = eor[i - 1] ^ nums[i];
        }

        return process(eor, 1, new ArrayList<>());
    }

    private static int process(int[] eor, int index, ArrayList<Integer> parts) {
        int ans;
        if (index == eor.length) {
            parts.add(index);
            ans = eorZeroParts(eor, parts);
            parts.remove(parts.size() - 1);
        } else {
            int p1 = process(eor, index + 1, parts);
            parts.add(index);
            int p2 = process(eor, index + 1, parts);
            parts.remove(parts.size() - 1);
            ans = Math.max(p1, p2);
        }

        return ans;
    }

    private static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
        int left = 0;
        int count = 0;
        for (Integer right : parts) {
            if ((eor[right - 1] ^ (left == 0 ? 0 : eor[left - 1])) == 0) {
                count++;
            }
            left = right;
        }
        return count;
    }


    private static int mostXor(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];
        Map<Integer, Integer> hashMap = new HashMap<>();

        int eor = 0;

        hashMap.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            eor ^= nums[i];

            if (hashMap.containsKey(eor)) {
                int pre = hashMap.get(eor);
                dp[i] = pre == -1 ? 1 : dp[pre] + 1;
            }

            if (i > 0) {
                dp[i] = Math.max(dp[i], dp[i - 1]);
            }

            hashMap.put(eor, i);
        }

        return dp[nums.length - 1];
    }
}
