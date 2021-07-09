package org.study.class11;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author phil
 * @date 2021/7/1 10:51
 */
public class MainTest1 {

    private static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int data){
            this.data = data;
        }
    }

    private static void level(Node head){
        if (null == head) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.data);
            if (null != cur.left) {
                queue.add(cur.left);
            }
            if (null != cur.right) {
                queue.add(cur.right);
            }
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

        level(head);
        System.out.println("========");
    }

}
