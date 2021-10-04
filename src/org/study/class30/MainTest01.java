package org.study.class30;

/**
 * @author phil
 * @date 2021/10/3 15:44
 */
public class MainTest01 {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void process(Node head) {
        if (null == head) {
            return;
        }
        // System.out.println(head.value);
        process(head.left);
        // System.out.println(head.value);
        process(head.right);
        System.out.println(head.value);
    }


    public static void morris(Node head) {
        if (null == head) {
            return;
        }

        Node cur = head;
        Node mostRight;
        while (null != cur) {
            mostRight = cur.left;
            if (null != mostRight) {
                while (null != mostRight.right && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (null == mostRight.right) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }

            cur = cur.right;

        }
    }

    public static void morrisPre(Node head) {
        if (null == head) {
            return;
        }

        Node cur = head;
        Node mostRight;
        while (null != cur) {
            mostRight = cur.left;
            if (null != mostRight) {
                while (null != mostRight.right && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }

                if (null == mostRight.right) {

                    System.out.println(cur.value);

                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }else{
                System.out.println(cur.value);
            }

            cur = cur.right;

        }
    }

    public static void morrisIn(Node head) {
        if (null == head) {
            return;
        }

        Node cur = head;
        Node mostRight;
        while (null != cur) {
            mostRight = cur.left;
            if (null != mostRight) {
                while (null != mostRight.right && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (null == mostRight.right) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {

                    mostRight.right = null;
                }
            }

            // 左边遍历完成或者是没有左
            System.out.println(cur.value);

            cur = cur.right;

        }
    }

    /**
     * morris 改为后序
     * @date 2021-10-03 13:39:16
     */
    public static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;

                    // 第二次到大
                    // 逆序打印其左树的右边界
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }

        // 遍历完成，打印整棵树的右边界
        printEdge(head);
        System.out.println();
    }


    public static void printEdge(Node head){
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null){
            System.out.println(cur.value);
            cur = cur.right;
        }
        reverseEdge(tail);
    }

    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }


    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        process(head);

        // morrisPre(head);
        // morrisIn(head);
        morrisPos(head);
    }
}
