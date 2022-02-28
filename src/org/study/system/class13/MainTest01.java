package org.study.system.class13;

import java.util.LinkedList;

/**
 * 是否是完全二叉树
 *
 * @author phil
 * @date 2021/7/7 12:53
 */
public class MainTest01 {
    
    private static class Node {
        public int data;
        public Node left;
        public Node right;
        public Node(int data){
            this.data = data;
        }
    }



    public static boolean isCompleteBinaryTree1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            if (
                // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
                    (leaf && (l != null || r != null)) || (l == null && r != null)

            ) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }


    private static boolean isCompleteBinaryTree2(Node head){
        if (null == head) {
            return true;
        }
        
        
        return process(head).isComplete;
    }
    
    private static Info process(Node curNode){
        if (null == curNode) {
            return new Info(true,true,0);
        }
        
        Info leftInfo = process(curNode.left);
        Info rightInfo = process(curNode.right);
        
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        
        boolean isComplete = false;
        
        if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height){
            isComplete = true;
        }else if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height+1){
            isComplete = true;
        }else if(leftInfo.isFull && rightInfo.isComplete && leftInfo.height == rightInfo.height){
            isComplete = true;
        }else if(leftInfo.isComplete && rightInfo.isFull && leftInfo.height == rightInfo.height +1){
            isComplete = true;
        }
        
        return new Info(isFull,isComplete,Math.max(leftInfo.height, rightInfo.height) +1);
    }
    
    private static class Info{
        public boolean isFull;
        public boolean isComplete;
        public int height;
        public Info(boolean isFull,boolean isComplete,int height){
            this.isFull = isFull;
            this.isComplete = isComplete;
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
            if (isCompleteBinaryTree1(head) != isCompleteBinaryTree2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
