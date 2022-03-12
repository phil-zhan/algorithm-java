package org.study.coding.class10;

/**
 * // 本题测试链接 : https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/
 * 给定一棵搜索二叉树头节点，转化成首尾相接的有序双向链表（节点都有两个方向的指针）
 *
 * 利用二叉树的递归套路。将以 head 为头的二叉树转化为双向链表
 * 返回转完之后的头、尾指针
 * 最后再调整整个链表首尾指针
 *
 * @since 2022-03-12 11:01:49
 */
public class Code04_BSTtoDoubleLinkedList {

    // 提交时不要提交这个类
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 提交下面的代码
    public static Node treeToDoublyList(Node head) {
        if (head == null) {
            return null;
        }
        Info allInfo = process(head);
        allInfo.end.right = allInfo.start;
        allInfo.start.left = allInfo.end;
        return allInfo.start;
    }

    public static class Info {
        public Node start;
        public Node end;

        public Info(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    public static Info process(Node X) {
        if (X == null) {
            return new Info(null, null);
        }

        // 左右都搞定后，将自己插在中间。搞定
        Info lInfo = process(X.left);
        Info rInfo = process(X.right);
        if (lInfo.end != null) {
            lInfo.end.right = X;
        }
        X.left = lInfo.end;
        X.right = rInfo.start;
        if (rInfo.start != null) {
            rInfo.start.left = X;
        }
        // 整体链表的头    lInfo.start != null ? lInfo.start : X
        // 整体链表的尾    rInfo.end != null ? rInfo.end : X
        return new Info(lInfo.start != null ? lInfo.start : X, rInfo.end != null ? rInfo.end : X);
    }

}