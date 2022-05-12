package org.study.system.class02;

public class MainTest02 {

    public int[] getOddTimesNum(int[] nums){
        int eor = 0;

        for (int num:nums){
            eor ^= num;
        }

        int oneOnly = 0;
        for (int i = 0; i < nums.length; i++) {
            int mostRightOne = nums[i] & (-nums[i]);
            if ((mostRightOne & eor) != 0){
                oneOnly = nums[i];
                break;
            }
        }

        return new int[]{oneOnly,oneOnly ^ eor};
    }
}
