package org.study.system.class12;

/**
 * 二叉树工具类
 * @author phil
 * @date 2021/7/6 14:10
 */
public class BinaryTreeUtil {
    public static class Node {
        public int data;
        public Node left;
        public Node right;

        public Node(int data) {
            this.data = data;
        }
    }

    /**
     * 随机生成一颗二叉树
     * @date 2021-07-06 14:16:55
     */
    public static Node generateRandomBST(int maxLevel,int maxValue){
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue){
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }

        Node head = new Node((int) (Math.random() * maxValue));

        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);

        return head;
    }
}
