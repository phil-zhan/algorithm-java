package org.study.system.class10;

import java.util.Stack;

/**
 * 手写非递归实现二叉树遍历
 *
 * @author phil
 * @date 2021/6/30 16:28
 */
public class MainTest3 {


    private static class Node{
        public int data;
        public Node left;
        public Node right;
        public Node(int data){
            this.data = data;
        }
    }


    /**
     * 非递归实现先序
     * @date 2021-06-30 16:37:42
     */
    private static void pre(Node head){
        if (null == head) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            head = stack.pop();
            System.out.println(head.data + "\t");
            if(null != head.right){
                stack.push(head.right);
            }

            if (null != head.left) {
                stack.push(head.left);
            }
        }
    }

    private static void min(Node head){
        if (null == head) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (null != head) {
                stack.push(head);
                head = head.left;
            }else{
                head = stack.pop();
                System.out.println(head.data);
                head = head.right;
            }
        }
    }

    private static void pos1(Node head){
        if (null == head) {
            return;
        }

        Stack<Node> stack1 = new Stack();
        Stack<Node> stack2 = new Stack();

        stack1.push(head);
        while (!stack1.isEmpty()) {
            head = stack1.pop();
            stack2.push(head);
            if (head.left != null) {
                stack1.push(head.left);
            }
            if(head.right != null){
                stack1.push(head.right);
            }
        }

        // 顺序打印stack2
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop().data);
        }

    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        min(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        //pos2(head);
        System.out.println("========");
    }
}
