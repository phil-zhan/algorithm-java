package org.study.system.class48;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 子序列
 *
 * @author phil
 * @since 2022-04-13 21:03:02
 */
public class Code31_78_Subsets {

    public static void main(String[] args) {
        List<List<Integer>> subsets = new Code31_78_Subsets().subsets(new int[]{1, 2, 3});
        System.out.println(subsets);
    }

    public List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> ans = new ArrayList<>();
        process(nums,0,new LinkedList<>(),ans);
        return ans;
    }


    public void process(int[] nums, int index, LinkedList<Integer> path, List<List<Integer>> ans) {
        if (index == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }

        process(nums, index + 1, path, ans);

        path.addLast(nums[index]);
        process(nums, index + 1, path, ans);
        path.removeLast();

    }
}
