package org.study.coding.class14;

import java.util.TreeSet;

public class MainTest02 {

    public static void main(String[] args) {

    }

    public int getMaxLessOrEqualK(int[] nums, int k) {
        int ans = 0;
        TreeSet<Integer> treeSet = new TreeSet<>();
        int sum = 0;
        treeSet.add(0);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (treeSet.ceiling(sum - k) != null) {
                ans = Math.max(ans, sum - treeSet.ceiling(sum - k));
            }

            treeSet.add(sum);
        }
        return ans;
    }
}
