package org.study.coding.class25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 本题测试链接 : https://leetcode.com/problems/3sum/
 * 2. 给你一个包含 n 个整数的数组nums，判断nums中是否存在三个元素 a，b，c ，使得a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组
 * 注意：答案中不可以包含重复的三元组
 * <p>
 * 题意：
 * 只要三元组的字面值不一样，都算是不同的三元组。
 * <p>
 * <p>
 * 这是一个二和问题的加强。
 * 二和问题：
 * 在一个数组中，任选两个数，加起来等于k。返回有哪些不同的组合。
 * 这是一个经典题目。先排序。 利用双指针解决
 * L指针指向0. R指针指向n-1
 * arr[L] + arr[R] < k  =======> L++
 * arr[L] + arr[R] > k  =======> R--
 * arr[L] + arr[R] = k  =======> 收集答案 L++ 或 R-- 。也可以同时动【因为不要收集重复答案】
 * <p>
 * 当然，这种情况还是会收集到重复的【比如数组全是2，要求k等于4的时候】。该怎么去重呢？
 * 我们在指针移动的时候，不只是加加减减的跳一步，而是跳到下一个与当前数不一样的数
 * 在收集答案的时候，如果当前数与前一个数一样（L的左边或R的右边），就不收集答案
 * <p>
 * <p>
 * 解决了二元组的问题后。
 * 考虑去解决 [L...R] 范围上，返回累加和是k的所有二元组。这样就能顺利解决三元组【来到某个数的时候，在剩下的数组中，求k等于当前数的差值的问题】
 * 在遍历的时候，如果发现和前面的数一样，自动跳过。【当然，是在数组有序的情况下】
 *
 * @since 2022-04-05 22:12:02
 */
public class Code02_3Sum {


    /**
     * 三元组问题
     *
     * @since 2022-04-06 07:30:45
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int N = nums.length;
        List<List<Integer>> ans = new ArrayList<>();

        // 三元组最后一个数，是arr[i]   之前....二元组 + arr[i]
        for (int i = N - 1; i > 1; i--) {

            // 必须和前面的一个数不一样【也就是当前数的右边的数】
            if (i == N - 1 || nums[i] != nums[i + 1]) {
                List<List<Integer>> nexts = twoSum(nums, i - 1, -nums[i]);
                for (List<Integer> cur : nexts) {
                    cur.add(nums[i]);
                    ans.add(cur);
                }
            }
        }
        return ans;
    }


    /**
     * 二元组问题
     * <p>
     * nums[0...end]这个范围上，有多少个不同二元组，相加==target，全返回
     * {-1,5}     K = 4
     * {1, 3}
     * <p>
     * 这里为什么要考虑[0...end]范围？
     * 按照之前的想法，在处理三元组的时候，从左往右遍历，
     * 来到某个位置的时候，在剩余的数组中，找到所有累加和等于它差值的二元组。再将当前所塞在所有二元组的开头，就变成了文秘要的三元组
     * 而对于ArrayList来说，塞在开头，代价有点高。所以在处理三元组的时候，考虑从右往左遍历【就可以将遍历到的数塞在其对应二元组的后面了】
     *
     * @since 2022-04-06 07:20:41
     */
    public static List<List<Integer>> twoSum(int[] nums, int end, int target) {
        int L = 0;
        int R = end;
        List<List<Integer>> ans = new ArrayList<>();
        while (L < R) {
            if (nums[L] + nums[R] > target) {
                R--;
            } else if (nums[L] + nums[R] < target) {
                L++;
            } else {
                // nums[L] + nums[R] == target

                if (L == 0 || nums[L - 1] != nums[L]) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(nums[L]);
                    cur.add(nums[R]);
                    ans.add(cur);
                }
                L++;
            }
        }
        return ans;
    }

    public static int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0, right = 1;
        int result = 0;
        while (left < nums.length && right < nums.length) {
            if (left == right || nums[right] - nums[left] < k) {
                right++;
            } else if (nums[right] - nums[left] > k) {
                left++;
            } else {
                left++;
                result++;
                while (left < nums.length && nums[left] == nums[left - 1]) {
                    left++;
                }
            }
        }
        return result;
    }

}
