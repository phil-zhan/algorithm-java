package org.study.system.class10;

/**
 * 递归遍历二叉树
 * @author phil
 * @date 2021/6/28 14:01
 */
public class Code02_RecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void f(Node head) {

        // 第几次经过该节点打印，就是什么
        // 1、2、3次，分别对应先中后续


        if (head == null) {
            return;
        }
        // 1
        f(head.left);
        // 2
        f(head.right);
        // 3
    }

    /**
     * // 先序打印所有节点
     * @date 2021-06-29 13:25:54
     */
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    /**
     * // 中序打印所有节点
     * @date 2021-06-29 13:25:54
     */
    public static void min(Node head) {
        if (head == null) {
            return;
        }
        min(head.left);
        System.out.println(head.value);
        min(head.right);
    }

    /**
     * // 后序打印所有节点
     * @date 2021-06-29 13:25:54
     */
    public static void pos(Node head) {
        if (head == null) {
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
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
