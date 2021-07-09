package org.study.class10;

import java.util.Stack;

/**
 * 非递归遍历二叉树
 *
 * 先序
 * 先将头节点压栈
 * 1）从栈中弹出栈顶元素，【打印】
 * 2）有右节点就将右节点压入，有左压入左。【先右后左】【没有就跳过】
 * 3）重复①和②
 * 打印出来的就是 【头、左、右】
 *
 *
 * 后序
 * 先将头节点压栈
 * 1）从栈中弹出栈顶元素，记为cur，
 * 2）检查cur是否 有左节点就将左节点压入，有右压入右。【先左后右】【没有就跳过】
 * 3）将cur压入另外一个栈
 * 4）重复①、②、③
 * 5）将另外一个栈中的元素依次打印，就变成了 【左、右、头】 后序
 *
 * 中序
 * 先将头节点压入栈
 * 1）将头节点cur的左节点也压入栈，直至为空，转第二步【将从头节点开始的所有左节点全部入栈】【也就是将左边界全部入栈】
 * 2）从栈中弹出节点打印，如果弹出的节点的右孩子不为空，就让当前节点的右孩子为cur，回第一步.【当前节点没有右孩子，直接弹出下一个节点】
 *
 * @author phil
 * @date 2021/6/28 14:01
 */
public class Code03_UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 非递归方式实现先序
     * @date 2021-06-30 13:40:43
     */
    public static void pre(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    /**
     * 非递归方式实现中序
     * @date 2021-06-30 13:41:05
     */
    public static void min(Node cur) {
        System.out.print("in-order: ");
        if (cur != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 两个栈实现后序二叉树的遍历
     * @date 2021-06-30 13:53:29
     */
    public static void pos1(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {
            Stack<Node> s1 = new Stack<Node>();
            Stack<Node> s2 = new Stack<Node>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop(); // 头 右 左
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            // 左 右 头
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    /**
     * 一个栈实现二叉树的后序遍历
     * @date 2021-06-30 13:53:51
     */
    public static void pos2(Node h) {
        System.out.print("pos-order: ");
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node c = null;
            while (!stack.isEmpty()) {
                c = stack.peek();
                if (c.left != null && h != c.left && h != c.right) {
                    stack.push(c.left);
                } else if (c.right != null && h != c.right) {
                    stack.push(c.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    h = c;
                }
            }
        }
        System.out.println();
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
        pos2(head);
        System.out.println("========");
    }
}
