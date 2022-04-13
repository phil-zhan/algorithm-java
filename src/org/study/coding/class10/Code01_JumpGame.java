package org.study.coding.class10;


public class Code01_JumpGame {

    /**
     * // 本题测试链接 : https://leetcode.com/problems/jump-game-ii/
     *
     * 给你一个非负整数数组nums ，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。假设你总是可以到达数组的最后一个位置。
     * <p>
     * 解法：
     * ①在每个位置，枚举所有的可能，然后根据当前的选择，跳到下一个位置。最后抓一个最大步数
     *
     *
     * ②准备几个变量【O(N)】
     * step：目前为止，跳了几步
     * current：如果不增加步数，0步以内最远能到哪。也就是step步内，最远能跳到哪    【是和step伴生的】
     * next：如果允许多跳一步（step+1步），最远能跳到哪。【也就是此时的位置，加上当前能获得的最大步数】
     *
     * 遍历数组
     * 当step步内，跳不到当前位置。也就是current小于当前位置时。将step++。同时将next的值拷贝给current。
     * 每次遍历，都注意更新next。 【也就是此时的位置，加上当前能获得的最大步数，再和当前next去PK】
     * next = Math.max(next, i + nums[i]) 。
     *
     * @since 2022-03-11 07:17:18
     */
    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int step = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return step;
    }


    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int step = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; i++) {
            if (cur<i && next < i){
                return false;
            }
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return cur>=(nums.length-1);
    }

    public static void main(String[] args) {
        System.out.println(new Code01_JumpGame().canJump(new int[]{0,2,1}));
    }

}
