package org.study.coding.class03;

import java.util.*;

/**
 * @author phil
 * @since 2022-0306 08:26:49
 */
public class MainTest08 {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }

    }

    public List<Node> distanceKNodes(Node root, Node target, int K) {

        HashMap<Node, Node> parent = new HashMap<>();
        parent.put(root, null);
        createParentMap(root, parent);

        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();

        queue.offer(target);
        visited.add(target);

        int level = 0;
        List<Node> ans = new ArrayList<>();

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size-- > 0) {
                Node cur = queue.poll();
                if (level == K) {
                    ans.add(cur);
                }
                if (parent.get(cur) != null && !visited.contains(parent.get(cur))) {
                    queue.offer(parent.get(cur));
                    visited.add(parent.get(cur));
                }
                if (cur.left != null && !visited.contains(cur.left)) {
                    queue.offer(cur.left);
                    visited.add(cur.left);
                }

                if (cur.right != null && !visited.contains(cur.right)) {
                    queue.offer(cur.right);
                    visited.add(cur.right);
                }

            }

            level++;
            if (level > K) {
                break;
            }
        }
        return ans;
    }

    private void createParentMap(Node root, HashMap<Node, Node> parent) {

        if (root == null) {
            return;
        }
        if (root.left != null) {
            parent.put(root.left, root);
            createParentMap(root.left, parent);
        }
        if (root.right != null) {
            parent.put(root.right, root);
            createParentMap(root.right, parent);
        }

    }


    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = new MainTest08().distanceKNodes(root, target, K);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }

    }
}
