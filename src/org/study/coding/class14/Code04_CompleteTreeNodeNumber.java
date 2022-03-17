package org.study.coding.class14;

/**
 * 给定一个棵完全二叉树，返回这棵树的节点个数，要求时间复杂度小于O(树的节点数)
 * <p>
 * 完全二叉树
 * 就算是不满的节点，也是从左到右的。空节点只会在右边
 * <p>
 * 解法：
 * 遍历最左边的边路，就能得到树的高度 假设为h
 * 从头节点开始。找到右树上的最左节点 。假设为 x。【在遍历的过程中，记录x的所在层数h1】
 * 如果x的所在层数不等于树的总高度h。这说明右树是满的。且右树的高度为 h1 【可以计算出右树的节点数 2^n-1】
 * x为头的总节点数。就是 2^n-1 + 1 + （左树的节点数）
 * <p>
 * 如果x的所在层数不等于树的总高度h。这说明右树是满的.可以用公式算出右树的节点数。递归算以左节点为头的树的节点数
 * 如果右树的节点所在的层数，也等于总节点的层数。这就说明左树是满的。可以用公式算出左树的节点数。递归算以右节点为头的树的节点数
 *
 * @since 2022-03-16 08:17:22
 */
public class Code04_CompleteTreeNodeNumber {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 请保证head为头的树，是完全二叉树
     *
     * O((logN)^2)
     *
     * @since 2022-03-16 08:33:29
     */
    public static int nodeNum(Node head) {
        if (head == null) {
            return 0;
        }
        return bs(head, 1, mostLeftLevel(head, 1));
    }

    /**
     * 当前来到node节点，node节点在level层，总层数是h
     * 返回node为头的子树(必是完全二叉树)，有多少个节点
     * h 是一个固定参数。也就是树的总高度
     *
     * @since 2022-03-16 08:33:36
     */
    public static int bs(Node node, int Level, int h) {

        // 只有一个头节点。高度为1
        if (Level == h) {
            return 1;
        }
        // 看看右树上的节点在第几层
        if (mostLeftLevel(node.right, Level + 1) == h) {
            // 到了h层。左树是满的。右边去递归
            // h - Level  也就是当前树的高度
            return (1 << (h - Level)) + bs(node.right, Level + 1, h);
        } else {
            // 到了h层。右树是满的。左边去递归
            return (1 << (h - Level - 1)) + bs(node.left, Level + 1, h);
        }
    }

    /**
     * 如果node在第level层，
     * 求以node为头的子树，最大深度是多少
     * node为头的子树，一定是完全二叉树
     *
     * @since 2022-03-16 08:33:47
     */
    public static int mostLeftLevel(Node node, int level) {
        while (node != null) {
            level++;
            node = node.left;
        }
        return level - 1;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        System.out.println(nodeNum(head));

    }

}
