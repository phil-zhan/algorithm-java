package org.study.coding.class14;

public class MainTest04 {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }


    public int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        return bs(head, 1, mostLeftLevel(head, 1));
    }

    public int bs(Node node, int level, int height) {
        if (level == height) {
            return 1;
        }

        if (mostLeftLevel(node.right, level + 1) == height) {
            return (1 << (height - level)) + bs(node.right, level + 1, height);
        } else {
            return (1 << (height - level - 1)) + bs(node.left, level + 1, height);
        }
    }

    public int mostLeftLevel(Node head, int level) {

        while (head != null) {
            level++;
            head = head.left;
        }

        return level - 1;
    }
}
