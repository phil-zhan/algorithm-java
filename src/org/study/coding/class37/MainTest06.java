package org.study.coding.class37;

public class MainTest06 {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public int rob(TreeNode root) {

        Info rootInfo = process(root);
        return Math.max(rootInfo.yes, rootInfo.no);
    }

    public Info process(TreeNode head) {
        if (head == null) {
            return new Info(0, 0);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int yes = head.val + leftInfo.no + rightInfo.no;
        int no = Math.max(leftInfo.yes, leftInfo.no) + Math.max(rightInfo.yes, rightInfo.no);

        return new Info(yes, no);
    }

    public static class Info {
        public int yes;
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }
}
