package org.study.coding.class16;

import java.util.Arrays;
import java.util.HashSet;

public class MainTest02 {

    public static void main(String[] args) {

        int times = 100000;
        for (int i = 0; i < times; i++) {
            int[] nums = Code02_SmallestUnFormedSum.generateArray(10, 100);
            int ans1 = new MainTest02().unformedSum1(nums);
            int ans2 = Code02_SmallestUnFormedSum.unformedSum1(nums);
            if (ans1 != ans2) {
                System.out.println("第 " + i + "次测试出错啦。。。");
                System.out.println(Arrays.toString(nums));
                System.out.println("ans1:" + ans1);
                System.out.println("ans2:" + ans2);
                break;
            }
        }
        System.out.println("================================================================");

        for (int i = 0; i < times; i++) {
            int[] nums = Code02_SmallestUnFormedSum.generateArray(10, 100);
            nums[0] = 1;
            int ans1 = new MainTest02().unformedSum3(nums);
            int ans2 = Code02_SmallestUnFormedSum.unformedSum3(nums);
            if (ans1 != ans2) {
                System.out.println("第 " + i + "次测试出错啦。。。");
                System.out.println(Arrays.toString(nums));
                System.out.println("ans1:" + ans1);
                System.out.println("ans2:" + ans2);
                break;
            }
        }

        System.out.println("Finish!!!");





    }

    public int unformedSum1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 1;
        }
        int min = Integer.MAX_VALUE;
        for (Integer num : nums) {
            min = Math.min(min, num);
        }

        HashSet<Integer> hashSet = new HashSet<>();
        process(nums, 0, 0, hashSet);


        for (int i = min + 1; i < Integer.MAX_VALUE; i++) {
            if (!hashSet.contains(i)) {
                return i;
            }
        }
        return 0;
    }


    public void process(int[] nums, int index, int sum, HashSet<Integer> hashSet) {
        if (index == nums.length) {
            hashSet.add(sum);
            return;
        }

        process(nums, index + 1, sum + nums[index], hashSet);
        process(nums, index + 1, sum, hashSet);
    }

    public int unformedSum3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int range = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > range + 1) {
                return range + 1;
            } else {
                range += nums[i];
            }
        }
        return range + 1;
    }
}
