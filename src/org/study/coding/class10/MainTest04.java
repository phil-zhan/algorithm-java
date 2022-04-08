package org.study.coding.class10;

import org.study.coding.class20.Code01_PreAndInArrayToPosArray;

import java.util.HashSet;

/**
 * @author phil
 * @date 2022-04-08 09:24:07
 */
public class MainTest04 {

    public static void main(String[] args) {
        System.out.println("test begin");
        int maxLevel = 5;
        int value = 1000;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomTree(value, maxLevel);
            Node node = treeToDoublyList(head);
            System.out.println();
        }
        System.out.println("test end");
    }

    public static Node treeToDoublyList(Node head) {
        if (head == null) {
            return null;
        }

        Info allInfo = process(head);
        allInfo.end.right = allInfo.start;
        allInfo.start.left = allInfo.end;

        return allInfo.start;
    }

    public static Info process(Node curNode) {
        if (curNode == null) {
            return new Info(null, null);
        }
        Info leftInfo = process(curNode.left);
        Info rightInfo = process(curNode.right);

        if (leftInfo.end != null) {
            leftInfo.end.right = curNode;
            curNode.left = leftInfo.end;
        }

        if (rightInfo.start != null) {
            rightInfo.start.left = curNode;
            curNode.right = rightInfo.start;
        }

        return new Info(leftInfo.start != null ? leftInfo.start : curNode, rightInfo.end != null ? rightInfo.end : curNode);
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static class Info {
        public Node start;
        public Node end;

        public Info(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    public static Node generateRandomTree(int value,int maxLevel){
        HashSet<Integer> hasValue = new HashSet<>();
        return createTree(value, 1, maxLevel, hasValue);
    }

    public static Node createTree(int value, int level, int maxLevel, HashSet<Integer> hasValue) {
        if (level > maxLevel) {
            return null;
        }
        int cur = 0;
        do {
            cur = (int) (Math.random() * value) + 1;
        } while (hasValue.contains(cur));
        hasValue.add(cur);
        Node head = new Node(cur);
        head.left = createTree(value, level + 1, maxLevel, hasValue);
        head.right = createTree(value, level + 1, maxLevel, hasValue);
        return head;
    }


}
