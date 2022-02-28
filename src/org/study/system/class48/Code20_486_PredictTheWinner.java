package org.study.system.class48;

/**
 * @author phil
 * @date 2022/2/17 14:31
 */
public class Code20_486_PredictTheWinner {

    public static void main(String[] args) {
        boolean win = new Code20_486_PredictTheWinner().PredictTheWinner(new int[]{1, 5, 2});
        System.out.println(win);
    }

    public boolean PredictTheWinner(int[] nums) {

        int play1 = pre(0, nums.length - 1, nums);
        int play2 = post(0, nums.length - 1, nums);

        return play1 >= play2;
    }


    public int pre(int left, int right, int[] nums) {
        if (left == right) {
            return nums[left];
        }

        return Math.max(nums[left] + post(left + 1, right, nums), nums[right] + post(left, right - 1, nums));
    }

    public int post(int left, int right, int[] nums) {
        if (left == right) {
            return 0;
        }
        return Math.min(pre(left + 1, right, nums), pre(left, right - 1, nums));
    }

}
