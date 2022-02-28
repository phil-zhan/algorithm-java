package org.study.system.class30;

/**
 * @author phil
 * @date 2021/10/4 10:05
 */
public class MainTest02 {
    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int x) {
            val = x;
        }
    }

    public static int minHeight1(Node head) {
        if (null == head) {
            return 0;
        }

        return process(head);
    }

    public static int process(Node node) {
        if (node.left == null && node.right == null) {
            return 1;
        }

        int leftHeight = Integer.MAX_VALUE;
        if (null != node.left) {
            leftHeight = process(node.left);
        }

        int rightHeight = Integer.MAX_VALUE;
        if (null != node.right) {
            rightHeight = process(node.right);
        }

        return 1+Math.min(leftHeight, rightHeight);
    }


    public static int minHeight2(Node head) {
        if (null == head) {
            return 0;
        }

        Node cur = head;
        Node mostRight = null;
        int curLevel = 0;
        int mostMinHeight = Integer.MAX_VALUE;

        while (null != cur) {
            mostRight = cur.left;

            if (mostRight != null) {
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                    rightBoardSize++;
                }
                if (mostRight.right == null) {

                    mostRight.right = cur;
                    cur = cur.left;
                    curLevel ++;
                    continue;
                } else {
                    // 刚刚从 mostRight 跳过来。在此计算 mostRight 的最低高度
                    if(mostRight.left == null){
                        mostMinHeight = Math.min(curLevel,mostMinHeight);
                    }

                    curLevel -= rightBoardSize;

                    mostRight.right = null;
                    // 至此，左树的遍历已经完成了
                }
            }else{
                curLevel++;
            }

            cur = cur.right;
        }


        // 遍历结束后，判断整棵树的最右节点的深度与之前的深度谁最小
        int finalRight = 1;
        cur = head;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null) {
            mostMinHeight = Math.min(mostMinHeight, finalRight);
        }

        return mostMinHeight;

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
        int treeLevel = 10;
        int nodeMaxValue = 5;
        int testTimes = 10000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(treeLevel, nodeMaxValue);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}
