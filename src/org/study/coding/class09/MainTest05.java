package org.study.coding.class09;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author phil
 * @date 2022-04-06 16:26:55
 */
public class MainTest05 {

    public static void main(String[] args) {
        int max = 1000000;
        int maxStepSum = stepSum(max);
        HashMap<Integer, Integer> ans = generateStepSumNumberMap(max);
        System.out.println("测试开始");
        for (int num = 0; num <= maxStepSum; num++) {
            if (isStepSum(num) ^ ans.containsKey(num)) {
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
                System.out.println("num:" + num);
                System.out.println("!!!!!!!!!!Oops!!!!!!!!!!");
            }
        }
        System.out.println("测试结束");
    }


    public static HashMap<Integer, Integer> generateStepSumNumberMap(int maxNum) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int num = 0; num <= maxNum; num++) {
            hashMap.put(stepSum(num), num);
        }
        return hashMap;
    }

    public static boolean isStepSum(int num) {
        int left = 0;
        int right = num;
        int mid = 0;
        int cur = 0;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            cur = stepSum(mid);
            if (cur == num) {
                return true;
            } else if (cur > num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return false;
    }

    public static int stepSum(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num;
            num /= 10;
        }
        return sum;
    }
}
