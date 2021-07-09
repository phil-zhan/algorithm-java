package org.study.class10;

/**
 * 手写递归实现
 * @author phil
 * @date 2021/6/30 15:27
 */
public class MainTest2 {

    private static class Node{
        public int data;
        public Node left;
        public Node right;
        
        public Node(int data){
            this.data = data;
        }
    }
    
    
    private static void pre(Node head){
        if (head == null) {
            return;
        }
        System.out.println(head.data);
        pre(head.left);
        pre(head.right);
    }

    private static void min(Node head){
        if(null == head){
            return;
        }

        min(head.left);
        System.out.println(head.data);
        min(head.right);
    }

    private static void pos(Node head){
        if(null == head){
            return;
        }

        pos(head.left);
        pos(head.right);

        System.out.println(head.data);
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
        pos(head);
        System.out.println("========");

    }
}
