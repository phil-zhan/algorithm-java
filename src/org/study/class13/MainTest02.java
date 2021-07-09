package org.study.class13;

import java.util.ArrayList;

/**
 * 求最大二叉搜索字树的头
 *
 * @author phil
 * @date 2021/7/7 13:10
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


    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).data <= arr.get(i - 1).data) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static Node maxSubBinarySearchTree1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBinarySearchTree1(head.left);
        Node rightAns = maxSubBinarySearchTree1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }


    private static Node maxSubBinarySearchTree2(Node head){
        if (null == head) {
            return null;
        }

        return process(head).maxSearchHead;
    }

    private static Info process(Node curNode){
        if (null == curNode) {
            return null;
        }

        Info leftInfo = process(curNode.left);
        Info rightInfo = process(curNode.right);
        
        Node maxSearchHead = null;
        int maxSearchSize = 0;
        int maxValue = curNode.data;
        int minValue = curNode.data;

        if (null != leftInfo) {
            maxSearchHead = leftInfo.maxSearchHead;
            maxSearchSize = leftInfo.maxSearchSize;
            maxValue = Math.max(maxValue, leftInfo.maxValue);
            minValue = Math.min(minValue, leftInfo.minValue);
        }

        if (null != rightInfo) {
            maxValue = Math.max(maxValue,rightInfo.maxValue);
            minValue = Math.min(minValue,rightInfo.minValue);
            
            
            if(rightInfo.maxSearchSize > maxSearchSize){
                maxSearchSize = rightInfo.maxSearchSize;
                maxSearchHead = rightInfo.maxSearchHead;
            }
        }
        
        boolean leftMeet = null == leftInfo || (leftInfo.maxSearchHead == curNode.left && leftInfo.maxValue < curNode.data);
        boolean rightMeet = null == rightInfo || (rightInfo.maxSearchHead == curNode.right && rightInfo.minValue > curNode.data);
        
        if(leftMeet && rightMeet){
            maxSearchSize = (null == leftInfo?0:leftInfo.maxSearchSize) + (null == rightInfo ? 0:rightInfo.maxSearchSize) +1;
            maxSearchHead = curNode;
        }
        
        return new Info(maxSearchHead,maxSearchSize,maxValue,minValue);
    }

    private static class Info{
        public Node maxSearchHead;
        public int maxSearchSize;
        public int maxValue;
        public int minValue;

        public Info(Node maxSearchHead,int maxSearchSize,int maxValue,int minValue){
            this.maxSearchHead = maxSearchHead;
            this.maxSearchSize = maxSearchSize;
            this.maxValue = maxValue;
            this.minValue = minValue;
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
                if (maxSubBinarySearchTree1(head) != maxSubBinarySearchTree2(head)) {
                    System.out.println("Oops!");
                }
            }
            System.out.println("finish!");
        }
    }
}
