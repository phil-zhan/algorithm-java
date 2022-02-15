package org.study.class48;

/**
 * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
 * @author phil
 * @date 2022/2/14 13:45
 */
public class Code04_104_MaxDepth {

    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        return 1+Math.max(leftDepth,rightDepth);
    }



    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
