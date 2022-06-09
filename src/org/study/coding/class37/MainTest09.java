package org.study.coding.class37;

import java.util.HashMap;

public class MainTest09 {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

    }

    public int pathSum(TreeNode root, int targetSum) {
        HashMap<Integer, Integer> preSumMap = new HashMap<>();
        preSumMap.put(0, 1);

        return process(root, targetSum, 0, preSumMap);
    }

    public int process(TreeNode head, int targetSum, int preSum, HashMap<Integer, Integer> map) {
        if (head == null) {
            return 0;
        }
        int ans = 0;
        preSum += head.val;
        if (map.containsKey(preSum - targetSum)) {
            ans = map.get(preSum - targetSum);
        }

        map.put(preSum, map.getOrDefault(preSum, 0) + 1);

        ans += process(head.left, targetSum, preSum, map);
        ans += process(head.right, targetSum, preSum, map);

        if (map.get(preSum) == 1) {
            map.remove(preSum);
        } else {
            map.put(preSum, map.get(preSum) - 1);
        }

        return ans;

    }
}
