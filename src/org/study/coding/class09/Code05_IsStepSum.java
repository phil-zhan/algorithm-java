package org.study.coding.class09;

import java.util.HashMap;

/**
 * 定义何为step sum？比如680，680 + 68 + 6 = 754，680的step sum叫754。
 * 给定一个正数num，判断它是不是某个数的step sum
 * <p>
 * 解法：
 * 如果x大于y，那么x的step sum 只可能大于 y 的 step sum。【打表证明】
 * 采用二分的方式去找答案
 * 假设所给的数是num，如果num是某个数的步骤和。那么这个数肯定在 [1...num] 之间。
 * 如果在这个区间都找不到，那么,那么num就不是某个数的步骤和
 * <p>
 * 在[1...num]区间上去进行二分，找答案，如果找到某个数的步骤和是 num。就 return true。
 * 当区间都只有一个数时，其步骤和都还不是 num 。那么num不是某个数的步骤和。return false。
 *
 * @since 2022-03-09 08:51:13
 */
public class Code05_IsStepSum {


    public static boolean isStepSum(int stepSum) {
        int L = 0;
        int R = stepSum;
        int M = 0;
        int cur = 0;
        while (L <= R) {
            M = L + ((R - L) >> 1);
            cur = stepSum(M);
            if (cur == stepSum) {
                return true;
            } else if (cur < stepSum) {
                L = M + 1;
            } else {
                R = M - 1;
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

    // for test
    public static HashMap<Integer, Integer> generateStepSumNumberMap(int numMax) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= numMax; i++) {
            map.put(stepSum(i), i);
        }
        return map;
    }

    // for test
    public static void main(String[] args) {
        int max = 1000000;
        int maxStepSum = stepSum(max);
        HashMap<Integer, Integer> ans = generateStepSumNumberMap(max);
        System.out.println("测试开始");
        for (int i = 0; i <= maxStepSum; i++) {
            if (isStepSum(i) ^ ans.containsKey(i)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }

}
