package org.study.coding.class04;

public class MainTest02 {
    public int maxSubArray(int[] nums) {

        int pre = nums[0];
        int ans = pre;
        for (int index = 1; index < nums.length; index++) {
            pre = Math.max(pre + nums[index], nums[index]);
            ans = Math.max(pre, ans);
        }
        return ans;
    }
}
