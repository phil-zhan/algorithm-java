package org.study.system.class12;

import java.util.ArrayList;

/**
 * 最大子节点距离数
 *
 * @author phil
 * @date 2021/7/6 15:51
 */
public class MainTest05 {
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

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }


    private static int maxSubBSTSize2(Node head){
        if (null == head) {
            return 0;
        }

        return process(head).maxBinarySearchTreeSubtreeSize;
    }


    private static Info process(Node curNode){
        if (null == curNode) {
            return null;
        }

        Info leftInfo = process(curNode.left);
        Info rightInfo = process(curNode.right);

        int curTreeMax = curNode.data;
        int curTreeMin = curNode.data;
        int allNodes = 1;

        if (null != leftInfo) {
            curTreeMax = Math.max(curTreeMax, leftInfo.curTreeMax);
            curTreeMin = Math.min(curTreeMin, leftInfo.curTreeMin);
            allNodes += leftInfo.allNodes;
        }


        if (null != rightInfo) {
            curTreeMax = Math.max(curTreeMax, rightInfo.curTreeMax);
            curTreeMin = Math.min(curTreeMin, rightInfo.curTreeMin);
            allNodes += rightInfo.allNodes;
        }

        int situation1 = -1;

        if (null != leftInfo) {
            situation1 = leftInfo.maxBinarySearchTreeSubtreeSize;
        }
        
        int situation2 = -1;

        if (null != rightInfo) {
            situation2 = rightInfo.maxBinarySearchTreeSubtreeSize;
        }
        int situation3 = -1;
        
        // 左边是搜索二叉树
        boolean leftSearch = null== leftInfo || leftInfo.allNodes == leftInfo.maxBinarySearchTreeSubtreeSize;
        // 右边是搜索二叉树
        boolean rightSearch = null == rightInfo || rightInfo.allNodes == rightInfo.maxBinarySearchTreeSubtreeSize;
        
        // 左右两边加当前节点也满足二叉树
        if(leftSearch && rightSearch){
            // 左边满足条件
            boolean leftMaxMeet = null == leftInfo || leftInfo.curTreeMax < curNode.data;
            boolean rightMinMeet = null == rightInfo || rightInfo.curTreeMin > curNode.data;

            if(leftMaxMeet && rightMinMeet){
                
                int leftNodes = null == leftInfo?0:leftInfo.allNodes;
                int rightNodes = null == rightInfo?0:rightInfo.allNodes;
                situation3 = leftNodes + rightNodes + 1;
            }
        }
        
        int maxBinarySearchTreeSubtreeSize = Math.max(Math.max(situation1,situation2),situation3);
        return new Info(maxBinarySearchTreeSubtreeSize,allNodes,curTreeMax,curTreeMin);
    }

    private static class Info{
        public int maxBinarySearchTreeSubtreeSize;
        public int allNodes;

        public int curTreeMax;
        public int curTreeMin;

        public Info(int maxBinarySearchTreeSubtreeSize,int allNodes,int curTreeMax,int curTreeMin){
            this.maxBinarySearchTreeSubtreeSize = maxBinarySearchTreeSubtreeSize;
            this.allNodes = allNodes;
            this.curTreeMax = curTreeMax;
            this.curTreeMin = curTreeMin;
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
