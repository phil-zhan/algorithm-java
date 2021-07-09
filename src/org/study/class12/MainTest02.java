package org.study.class12;

import java.util.ArrayList;

/**
 * 判断一棵树是否是搜索二叉树
 * 在二叉树中，左树上的值都小于头结点、右树上的值都大于头节点
 * @author phil
 * @date 2021/7/6 14:50
 */
public class MainTest02 {
    private static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int data){
            this.data = data;
        }
    }


    private static boolean isSearchBinaryTree2(Node head){
        if (null == head) {
            return true;
        }

        return process(head).isSearchBinaryTree;
    }

    private static Info process(Node curNode){
        if (null == curNode) {
            return null;
        }

        Info leftInfo = process(curNode.left);
        Info rightInfo = process(curNode.right);

        boolean isSearchBinaryTree = true;

        int curTreeMaxValue = curNode.data;
        int curTreeMinValue = curNode.data;

        if (null != leftInfo) {
            curTreeMaxValue = Math.max(curTreeMaxValue, leftInfo.curTreeMaxValue);
            curTreeMinValue = Math.min(curTreeMinValue, leftInfo.curTreeMinValue);
        }

        if (null != rightInfo) {
            curTreeMaxValue = Math.max(curTreeMaxValue, rightInfo.curTreeMaxValue);
            curTreeMinValue = Math.min(curTreeMinValue, rightInfo.curTreeMinValue);
        }

        if (null != leftInfo && !leftInfo.isSearchBinaryTree) {
            isSearchBinaryTree = false;
        }

        if (null != rightInfo && !rightInfo.isSearchBinaryTree) {
            isSearchBinaryTree = false;
        }

        if (null != leftInfo && leftInfo.curTreeMaxValue >= curNode.data) {
            isSearchBinaryTree = false;
        }

        if (null != rightInfo && rightInfo.curTreeMinValue <= curNode.data) {
            isSearchBinaryTree = false;
        }

        return new Info(isSearchBinaryTree,curTreeMaxValue,curTreeMinValue);
    }

    private static class Info{
        public boolean isSearchBinaryTree;
        public int curTreeMaxValue;
        public int curTreeMinValue;

        public Info(boolean isSearchBinaryTree,int curTreeMaxValue,int curTreeMinValue){
            this.isSearchBinaryTree = isSearchBinaryTree;
            this.curTreeMaxValue = curTreeMaxValue;
            this.curTreeMinValue = curTreeMinValue;
        }
    }



    public static boolean isSearchBinaryTree1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).data <= arr.get(i - 1).data) {
                return false;
            }
        }
        return true;
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isSearchBinaryTree1(head) != isSearchBinaryTree2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
