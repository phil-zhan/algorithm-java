package org.study.coding.class07;

import java.util.HashSet;
import java.util.Set;

/**
 * @author phil
 * @date 2022-03-24 16:51:15
 */
public class MainTest04 {
    public static void main(String[] args) {
        int len = 100;
        int value = 500;
        int testTimes = 200000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = Code04_Power2Diffs.randomSortedArray(len, value);
            int ans1 = diff1(arr);
            int ans2 = diff2(arr);
            if (ans1 != ans2) {
                Code04_Power2Diffs.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }

    public static int diff1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            hashSet.add(num * num);
        }

        return hashSet.size();
    }

    public static int diff2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0;
        int right = nums.length - 1;
        int count = 0;
        int leftAbs = 0;
        int rightAbs = 0;
        while (left <= right) {
            count++;
            leftAbs = Math.abs(nums[left]);
            rightAbs = Math.abs(nums[right]);
            if (leftAbs < rightAbs) {
                while (right >= 0 && Math.abs(nums[right]) == rightAbs) {
                    right--;
                }
            } else if (leftAbs > rightAbs) {
                while (left < nums.length && Math.abs(nums[left]) == leftAbs) {
                    left++;
                }
            } else {
                while (left < nums.length && Math.abs(nums[left]) == leftAbs) {
                    left++;
                }
                while (right >= 0 && Math.abs(nums[right]) == rightAbs) {
                    right--;
                }
            }
        }

        return count;
    }
}
