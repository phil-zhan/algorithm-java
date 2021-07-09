package org.study.class11;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的按层遍历
 * @author phil
 * @date 2021/7/1 9:10
 */
public class Code01_LevelTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }


    /**
     * 水平遍历树。【也就是按层遍历】
     * 1）先将头节点入队
     * 2）循环队列，只要队列不为空，就弹出元素，并且将弹出元素的左、有子节点分别入队
     *
     * @date 2021-07-01 10:09:06
     */
    public static void level(Node head) {
        if (head == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();

        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
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
