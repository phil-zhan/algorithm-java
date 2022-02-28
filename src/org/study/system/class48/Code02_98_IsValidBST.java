package org.study.system.class48;

/**
 * https://leetcode-cn.com/problems/validate-binary-search-tree/
 *
 * @author phil
 * @date 2022/2/14 12:46
 */
public class Code02_98_IsValidBST {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);

        root.left = node1;
        root.right = node2;

        boolean validBST = new Code02_98_IsValidBST().isValidBST(root);
        System.out.println(validBST);
    }

    public boolean isValidBST(TreeNode root) {
        return process(root).isBST;
    }

    public Info process(TreeNode root) {
        if (root == null) {
            return null;
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        Info curInfo = new Info();


        if (leftInfo != null && rightInfo != null) {
            curInfo.minVal = Math.min(Math.min(leftInfo.minVal, rightInfo.minVal), root.val);
            curInfo.maxVal = Math.max(Math.max(leftInfo.maxVal, rightInfo.maxVal), root.val);
            curInfo.isBST = leftInfo.isBST && rightInfo.isBST && leftInfo.maxVal < root.val && rightInfo.minVal > root.val;
        } else if (leftInfo != null) {
            curInfo.minVal = Math.min(leftInfo.minVal, root.val);
            curInfo.maxVal = Math.max(leftInfo.maxVal, root.val);
            curInfo.isBST = leftInfo.isBST && leftInfo.maxVal < root.val;
        } else if (rightInfo != null) {
            curInfo.minVal = Math.min(rightInfo.minVal, root.val);
            curInfo.maxVal = Math.max(rightInfo.maxVal, root.val);
            curInfo.isBST = rightInfo.isBST && rightInfo.minVal > root.val;
        } else {
            curInfo.minVal = root.val;
            curInfo.maxVal = root.val;
            curInfo.isBST = true;
        }

        return curInfo;
    }

    public class Info {
        public int maxVal;
        public int minVal;
        public boolean isBST;

        public Info() {

        }

        public Info(int maxVal, int minVal, boolean isBST) {
            this.maxVal = maxVal;
            this.minVal = minVal;
            this.isBST = isBST;
        }
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
