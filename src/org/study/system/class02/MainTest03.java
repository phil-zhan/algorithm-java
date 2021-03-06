package org.study.system.class02;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author phil
 */
public class MainTest03 {

    public static void main(String[] args) {
        int maxKinds = 3;
        int range = 200;
        int testTimes = 1000000;
        int max = 9;

        System.out.println("测试开始！");
        for (int i = 0; i < testTimes; i++) {

            //
            int a = (int) (Math.random() * max) + 1;
            int b = (int) (Math.random() * max) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);

            // k、m都是1到9之间的随机数，且保证k<m
            if (k == m) {
                m++;
            }
            int[] arr = generateRandomArray(maxKinds, range, k, m);

            int answer1 = test(arr, k, m);
            int answer2 = Code03_OnlyKTimesNum.getOnlyKTimesNum(arr, k, m);

            if (answer1 != answer2) {
                // PrintUtil.printArr(arr);
                break;
            }
        }
        System.out.println("测试结束");


        System.out.println("=============================================");
        int[] arr = {6,6,2,2,2,2,3,3,3,3,5,5,5,5};
        int answer = new MainTest03().getOnlyKTimesNum(arr,2,4);
        System.out.println(answer);
        System.out.println("=============================================");

    }


    /**
     * 经典统计词频
     *
     * @date 2021-03-12 15:06:06
     */
    public static int test(int[] arr, int k, int m) {
        Map<Integer, Integer> map = new HashMap<>();

        // 经典做法
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }

        return -1;
    }

    /**
     * 对数器
     *
     * @date 2021-03-12 15:25:55
     */
    public static int[] generateRandomArray(int maxKinds, int range, int k, int m) {
        int kTiemsNum = randomNum(range);

        // 随机种数,
        int numKinds = (int) (Math.random() * maxKinds) + 3;

        int[] arr = new int[k + (numKinds - 1) * m];

        // 将只出现k次的数填入数组
        int index = 0;
        for (; index < k; index++) {
            arr[index] = kTiemsNum;
        }

        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(kTiemsNum);
        while (numKinds != 0) {

            // 保证每一种数都是新的，确保不和之前的数重复
            int curNum = 0;
            do {
                curNum = randomNum(range);
            } while (set.contains(curNum));

            set.add(curNum);

            numKinds--;

            // 将当前数填入数组，填m次
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }

        }
        // 将数组打乱
        for (int i = 0; i < arr.length; i++) {
            // i位置的数随机的和j位置的数做交换
            // 只会在 0到n-1之间选一个
            int j = (int) (Math.random() * arr.length);

            // 随机交换
            int tem = arr[i];
            arr[i] = arr[j];
            arr[j] = tem;
        }


        // 返回
        return arr;
    }

    /**
     * 生成 -range ~ range 之间的一个随机数
     *
     * @date 2021-03-12 15:28:47
     */
    public static int randomNum(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }


    public int getOnlyKTimesNum(int[] nums, int k, int m) {
        int[] tmp = new int[32];
        for (int num : nums) {
            for (int i = 0; i <=31; i++) {
                if ((num & (1<<i)) != 0){
                    tmp[i]++;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] % m != 0){
                ans |= (1 << i);
            }
        }

        return ans;

    }
}
