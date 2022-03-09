package org.study.coding.class05;

public class MainTest02 {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public int sameNumber1(Node root) {
        if (root == null) {
            return 0;
        }

        return sameNumber1(root.left) + sameNumber1(root.right) + (same(root.left, root.right) ? 1 : 0);
    }

    public boolean same(Node head1, Node head2) {
        if (head1 == null ^ head2 == null) {
            return false;
        }
        if (head1 == null) {
            return true;
        }

        return head1.value == head2.value && same(head1.left, head2.left) && same(head1.right, head2.right);
    }

    public int sameNumber2(Node root) {
        if (root == null) {
            return 0;
        }
        return process(root, new Hash("SHA-256")).sameNum;
    }

    public Info process(Node head, Hash hash) {
        if (head == null) {
            return new Info(0, hash.hashCode("#,"));
        }
        Info leftInfo = process(head.left, hash);
        Info rightInfo = process(head.right, hash);
        return new Info(leftInfo.sameNum + rightInfo.sameNum + (leftInfo.hashStr.equals(rightInfo.hashStr) ? 1 : 0), hash.hashCode(head.value + "," + leftInfo.hashStr + rightInfo.hashStr));
    }

    public static class Info {
        public int sameNum;
        public String hashStr;

        public Info(int sameNum, String hashStr) {
            this.sameNum = sameNum;
            this.hashStr = hashStr;
        }
    }

}
