package org.study.coding.class37;

public class MainTest03 {
    public static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(){}

        public TreeNode(int val){
            this.val = val;
        }
    }
    public static class Info{
        public TreeNode head;
        public TreeNode tail;

        public Info(TreeNode head,TreeNode tail){
            this.head = head;
            this.tail = tail;
        }
    }

    /**
     * 递归套路解决
     * @since 2022-06-06 15:46:06
     */
    public void flatten1(TreeNode head){
        process(head);
    }


    public Info process(TreeNode head){
        if (head == null){
            return null;
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        head.left = null;
        head.right = leftInfo == null ? null : leftInfo.head;

        TreeNode tail = leftInfo == null ? head : leftInfo.tail;

        tail.right = rightInfo == null ? null : rightInfo.head;
        tail = rightInfo == null ? tail : rightInfo.tail;
        return new Info(head, tail);
    }


    /**
     * morris 遍历方式解决
     * @since 2022-06-06 15:45:50
     */
    public void flatten2(TreeNode head){

    }
}
