package org.study.class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author phil
 * @date 2021/7/1 23:59
 */
public class MainTest2 {


    private static class Node{
        public int data;
        public Node left;
        public Node right;

        public Node(int data){
            this.data = data;
        }
    }


    private static Queue<String> preSerial(Node head){
        Queue<String> answer = new LinkedList<>();

        pres(head,answer);

        return answer;

    }




    private static void pres(Node head, Queue<String> answer) {
        if (null == head) {
            answer.add(null);
        }else{
            answer.add(String.valueOf(head.data));
            pres(head.left,answer);
            pres(head.right,answer);
        }
    }

    private static Node buildByPreQueue(Queue<String> preQueue){
        if (null == preQueue || preQueue.size()==0) {
            return null;
        }

        return buildPre(preQueue);
    }

    private static Node buildPre(Queue<String> preQueue) {

        String data = preQueue.poll();
        if (null == data) {
            return null;
        }
        Node head = new Node(Integer.parseInt(data));
        head.left = buildPre(preQueue);
        head.right = buildPre(preQueue);

        return head;
    }


    private static Queue<String> posSerial(Node head){
        Queue<String> answer = new LinkedList<>();

        poss(head,answer);

        return answer;
    }

    private static void poss(Node head, Queue<String> answer) {
        if (null == head) {
            answer.add(null);
        }else {
            poss(head.left,answer);
            poss(head.right,answer);
            answer.add(String.valueOf(head.data));
        }
    }

    private static Node buildByPosQueue(Queue<String> posQueue){
        if (null == posQueue || posQueue.size()==0) {
            return null;
        }

        return buildPos(posQueue);
    }

    private static Node buildPos(Queue<String> posQueue) {

        // posQueue:左右中 -> 中右左
        Stack<String> stack = new Stack<>();
        while (!posQueue.isEmpty()) {
            stack.push(posQueue.poll());
        }

        return build(stack);
    }

    private static Node build(Stack<String> stack) {
        String data = stack.pop();
        if (null == data) {
            return null;
        }

        Node head = new Node(Integer.parseInt(data));
        head.right = build(stack);
        head.left = build(stack);

        return head;
    }

    private static Queue<String> levelSerial(Node head){
        Queue<String> answer = new LinkedList<>();
        if (null == head) {
            answer.add(null);
            return answer;
        }
        // 用于支持按层遍历
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()){
            Node node = queue.poll();
            if(null == node.left){
                answer.add(null);
            }else {
                answer.add(String.valueOf(node.left.data));
                queue.add(node.left);
            }

            if(null == node.right){
                answer.add(null);
            }else {
                answer.add(String.valueOf(node.right.data));
                queue.add(node.right);
            }
        }


        return answer;
    }


    private static Node buildByLevelQueue(Queue<String> levelQueue){
        if (null == levelQueue || levelQueue.size() ==0) {
            return null;
        }
        Node head = generate(levelQueue.poll());
        Queue<Node> queue = new LinkedList<>();

        if(null != head){
            queue.add(head);
        }

        Node curNode;
        while (!queue.isEmpty()) {
            curNode = queue.poll();
            curNode.left = generate(levelQueue.poll());
            curNode.right = generate(levelQueue.poll());

            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
        }


        return head;
    }

    private static Node generate(String data){
        if (null == data) {
            return null;
        }

        return new Node(Integer.parseInt(data));
    }

}
