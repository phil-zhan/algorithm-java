package org.study.coding.class07;

/**
 * @author phil
 * @date 2022-03-23 17:40:45
 */
public class MainTest01 {

    public static void main(String[] args) {
        int maxSize = 50;
        int range = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * maxSize) + 2;
            int[] arr = Code01_MaxAndValue.randomArray(size, range);
            int ans1 = maxAndValue1(arr);
            int ans2 = maxAndValue2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }

    private static int maxAndValue1(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                max = Math.max(max, nums[i] & nums[j]);
            }
        }

        return max;
    }

    private static int maxAndValue2(int[] nums) {
        int ans = 0;
        int considerLen = nums.length;
        for (int bit = 30; bit >= 0; bit--) {
            int tem = considerLen;
            int left = 0;
            while (left < considerLen) {
                if ((nums[left] & (1 << bit)) == 0) {
                    swap(nums, left, --considerLen);
                } else {
                    left++;
                }
            }
            if (considerLen == 2) {
                return nums[0] & nums[1];
            } else if (considerLen < 2) {
                considerLen = tem;
            } else {
                ans |= (1 << bit);
            }
        }
        return ans;
    }

    private static void swap(int[] nums, int i1, int i2) {
        if (nums[i1] == nums[i2]) {
            return;
        }
        nums[i1] = nums[i1] ^ nums[i2];
        nums[i2] = nums[i1] ^ nums[i2];
        nums[i1] = nums[i1] ^ nums[i2];
    }
}
