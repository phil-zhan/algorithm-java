package org.study.class12;

/**
 * 平衡二叉树
 * 在一个二叉树中，每一颗树的左树高度和右树高度相差的最大值不超过 1
 * 【每一个节点皆需要满足下面的三个条件】
 * 1）左树是平衡二叉树
 * 2）右树是平衡二叉树
 * 3）左右树的高度差不大于 1
 * @author phil
 * @date 2021/7/6 15:25
 */
public class MainTest03 {
    private static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int data){
            this.data = data;
        }
    }


    public static boolean isBalanced1(Node head) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }


    private static boolean isBalanceBinaryTree2(Node head){
        if (null == head) {
            return true;
        }

        return process2(head).isBalanceBinaryTree;
    }


    private static Info process2(Node curNode){
        if (null == curNode) {
            return new Info(true,0);
        }

        Info leftInfo = process2(curNode.left);
        Info rightInfo = process2(curNode.right);

        boolean isBalanceBinaryTree = Math.abs(leftInfo.height - rightInfo.height) <= 1;

        if (!leftInfo.isBalanceBinaryTree || !rightInfo.isBalanceBinaryTree) {
            isBalanceBinaryTree=false;
        }

        return new Info(isBalanceBinaryTree, Math.max(leftInfo.height, rightInfo.height) +1);
    }

    private static class Info{
        public boolean isBalanceBinaryTree;
        public int height;

        public Info(boolean isBalanceBinaryTree,int height){
            this.isBalanceBinaryTree = isBalanceBinaryTree;
            this.height = height;
        }
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanceBinaryTree2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
