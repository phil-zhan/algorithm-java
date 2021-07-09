package org.study.class12;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断一棵树是否树完全二叉树
 * @author phil
 * @date 2021/7/6 13:40
 */
public class MainTest01 {

    private static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int data){
            this.data = data;
        }
    }



    private static boolean isCompleteBinaryTree1(Node head){
        if (null == head) {
            return true;
        }

        boolean doubleFull = false;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if(doubleFull && (null != cur.left || null!=cur.right)){
                // 在前面已经遇到过不双全的节点的情况下，还遇到非叶子节点。肯定就不是完全二叉树
                return false;
            }

            if (null != cur.right && null == cur.left) {
                // 当前节点有右节点，但是无左节点，毫无疑问不是完全二叉树
                return false;
            }

            if (null == cur.left || null == cur.right) {
                // 当前节点只有左节点，没有右节点
                doubleFull = true;
            }
            // 继续遍历
            if (null != cur.left) {
                queue.add(cur.left);
            }
            if (null != cur.right) {
                queue.add(cur.right);
            }

        }

        // 没有在遍历中被弹回去。肯定就是完全二叉树
        return true;
    }

    private static boolean isCompleteBinaryTree2(Node head){
        if (null == head) {
            return true;
        }

        return process(head).isComplete;
    }

    private static Info process(Node cur) {
        if(null == cur){
            return new Info(true,true,0);
        }

        Info leftInfo = process(cur.left);
        Info rightInfo = process(cur.right);

        // 左边是满二叉树 && 右边是满二叉树 && 左右高度相等
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;

        boolean isComplete = false;

        if (isFull) {
            isComplete = true;
        }else {
            if(leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height){
                isComplete = true;
            }

            if(leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height+1){
                isComplete = true;
            }

            if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height +1){
                isComplete = true;
            }

        }

        int height = Math.max(leftInfo.height,rightInfo.height)+1;

        return new Info(isFull,isComplete,height);

    }

    private static class Info{
        // 当前树是否是满二叉树
        public boolean isFull;

        // 当前树是否是完全二叉树
        public boolean isComplete;

        // 当前树的高度
        public int height;

        public Info(boolean isFull,boolean isComplete,int height){
            this.isFull = isFull;
            this.isComplete = isComplete;
            this.height = height;

        }
    }


    /**
     * 随机生成一颗二叉树
     * @date 2021-07-06 14:16:55
     */
    public static Node generateRandomBST(int maxLevel, int maxValue){
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue){
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
            if (isCompleteBinaryTree2(head) != isCompleteBinaryTree1(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
