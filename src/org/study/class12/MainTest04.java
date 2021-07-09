package org.study.class12;

/**
 * 是否是满二叉树
 *
 * @author phil
 * @date 2021/7/6 15:37
 */
public class MainTest04 {
    private static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int data){
            this.data = data;
        }
    }



    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }

    public static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
    }

    public static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
    }


    private static boolean isFull2(Node head){
        if (null == head) {
            return true;
        }

        Info headInfo = process(head);

        return (1 << headInfo.height)-1 == headInfo.nodes;
    }

    private static Info process(Node curNode) {
        if (null == curNode) {
            return new Info(0,0);
        }

        Info leftInfo = process(curNode.left);
        Info rightInfo = process(curNode.right);


        return new Info(Math.max(leftInfo.height,rightInfo.height)+1, leftInfo.nodes+rightInfo.nodes+1);
    }

    private static class Info{
        public int height;
        public int nodes;
        public Info(int height,int nodes){
            this.height= height;
            this.nodes = nodes;
        }
    }




    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
