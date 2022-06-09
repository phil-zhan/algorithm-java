package org.study.coding.class37;

public class MainTest05 {
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public TreeNode invertTree(TreeNode head) {
        if (head == null) {
            return null;
        }
        TreeNode left = head.left;

        head.left = invertTree(head.right);
        head.right = invertTree(left);

        return head;
    }
}
