package org.study.coding.class30;

/**
 * 108. 将有序数组转换为二叉搜索树
 * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
 * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
 * <p>
 * 解法：
 * 考虑将L...R范围上的数组转换成搜索二叉树【高度平衡】
 * 最佳的就是中点位置做头
 *
 * @author phil
 * @since 2022-04-16 10:11:03
 */
public class Problem_0108_ConvertSortedArrayToBinarySearchTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        return process(nums, 0, nums.length - 1);
    }

    public static TreeNode process(int[] nums, int L, int R) {
        if (L > R) {
            return null;
        }
        if (L == R) {
            return new TreeNode(nums[L]);
        }

        // 左右自己去处理
        int M = (L + R) / 2;
        TreeNode head = new TreeNode(nums[M]);
        head.left = process(nums, L, M - 1);
        head.right = process(nums, M + 1, R);
        return head;
    }

}
