package org.study.class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author phil
 * @date 2021/7/2 10:21
 */
public class MainTest5 {

    private static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    private static int maxWidthNoMap(Node head){

        if (null == head) {
            return 0;
        }
        int max = 0;
        int curLevelNodes=0;
        Node curLevelEnd = head;
        Node nextLevelEnd = null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
           Node cur = queue.poll();

           curLevelNodes++;


           if(cur.left != null){
               queue.add(cur.left);
               nextLevelEnd = cur.left;
           }
           if(cur.right != null){
               queue.add(cur.right);
               nextLevelEnd = cur.right;
           }

            if (cur == curLevelEnd) {
                curLevelEnd = nextLevelEnd;
                max = Math.max(max,curLevelNodes);

                curLevelNodes = 0;
            }

        }

        return max;
    }

    private static int maxWidthUseMap(Node head){
        if (null == head) {
            return 0;
        }


        Queue<Node> queue = new LinkedList<>();
        queue.add(head);


        Map<Node, Integer> map = new HashMap<>() ;

        map.put(head,1);

        // 当前你正在统计哪一层
        int curLevel = 1;

        // 当前层curLevel层，宽度目前是多少
        int curLevelNodes = 0;

        int max = 0;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            // 当前节点所在的层数
            int curNodeLevel=map.get(cur);

            if (null != cur.left) {
                queue.add(cur.left);
                map.put(cur.left,curNodeLevel +1);
            }
            if (null != cur.right) {
                queue.add(cur.right);
                map.put(cur.right,curNodeLevel +1);
            }

            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            }else{
                max = Math.max(curLevelNodes,max);
                curLevelNodes=1;
                curLevel++;
            }
        }

        max = Math.max(max, curLevelNodes);
        return max;
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
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }
}
