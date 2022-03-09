package org.study.coding.class05;

public class MainTest01 {

    // 不用提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        return process(0, preorder.length - 1, preorder);
    }

    private TreeNode process(int left, int right, int[] preorder) {
        if (left > right) {
            return null;
        }
        int firstBig = left + 1;
        while (firstBig <= right) {
            if (preorder[firstBig] > preorder[left]) {
                break;
            }
            firstBig++;
        }

        TreeNode head = new TreeNode(preorder[left]);
        head.left = process(left + 1, firstBig - 1, preorder);
        head.right = process(firstBig, right, preorder);

        return head;
    }
}
