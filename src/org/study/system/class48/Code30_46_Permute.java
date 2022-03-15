package org.study.system.class48;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author phil
 * @since 2022-0316 00:03:19
 */
public class Code30_46_Permute {

    public static void main(String[] args) {
        System.out.println(new Code30_46_Permute().permute(new int[]{1, 2}));
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        process(nums, 0, ans, new LinkedList<>());
        return ans;
    }

    public void process(int[] nums, int index, List<List<Integer>> ans, LinkedList<Integer> path) {
        if (index == nums.length) {
            ans.add(new ArrayList<>(path));
            return;
        }
        for (int num : nums) {
            if (path.contains(num)) {
                continue;
            }
            path.addLast(num);
            process(nums, index + 1, ans, path);
            path.removeLast();
        }

    }
}
