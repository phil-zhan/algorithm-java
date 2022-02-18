package org.study.class48;

/**
 * @author phil
 * @date 2022/2/17 17:23
 */
public class Code21_112_HasPathSum {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(1);


        boolean can = new Code21_112_HasPathSum().hasPathSum(root, 1);
        System.out.println(can);
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return process(root, targetSum);
    }

    public boolean process(TreeNode root, int targetSum) {


        // 当前节点是叶节点
        if (root != null && root.left == null && root.right == null && root.val == targetSum) {
            return true;
        }

        if (root == null) {
            return false;
        }
        boolean leftInfo = process(root.left, targetSum - root.val);
        boolean rightInfo = process(root.right, targetSum - root.val);

        return leftInfo || rightInfo;
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
