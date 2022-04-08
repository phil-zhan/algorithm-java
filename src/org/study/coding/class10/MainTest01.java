package org.study.coding.class10;

/**
 * @author phil
 * @date 2022-04-06 17:22:19
 */
public class MainTest01 {
    public static void main(String[] args) {
        // 正常答案都是2
        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));
        System.out.println(jump(new int[]{2, 3, 0, 1, 4}));
    }

    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int step = 0;
        int current = 0;
        int next = 0;
        for (int index = 0; index < nums.length; index++) {
            if (current < index) {
                step++;
                current = next;
            }
            next = Math.max(next, index + nums[index]);
        }
        return step;
    }
}
