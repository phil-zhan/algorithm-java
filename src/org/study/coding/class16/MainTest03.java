package org.study.coding.class16;

public class MainTest03 {


    public static void main(String[] args) {

        int[] test = {1, 2, 31, 33};
        int n = 2147483647;
        System.out.println(new MainTest03().minPatches(test, n));
    }

    public int minPatches(int[] nums, int aim) {
        // nums :有序的正整数
        int patches = 0;
        long range = 1;

        for (int num : nums) {
            // 当前数 nums[i]
            while (range < num - 1) {
                range += range + 1;
                patches++;
                if (range >= aim) {
                    return patches;
                }
            }

            range += num;
            if (range >= aim) {
                return patches;
            }
        }

        while (range < aim) {
            range += range + 1;
            patches++;
        }
        return patches;
    }
}
