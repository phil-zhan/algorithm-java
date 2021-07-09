package org.study.class09;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author phil
 * @date 2021/6/24 9:23
 */
public class MainTest {
    private static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }


    private static Node midOrUpMidNode(Node head){
        if (null==head || null==head.next || null==head.next.next) {

            return head;
        }
        Node slow = head.next;
        Node fast = head.next.next;

        while (null != fast.next && null != fast.next.next) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private static Node midOrDownMidNode(Node head){
        if (null==head||null==head.next) {
            return head;
        }

        Node slow = head.next;
        Node fast = head.next;
        while (null != fast.next && null != fast.next.next){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private static Node rights1(Node head){
        if (null==head) {
            return null;
        }

        Node cur = head;
        List<Node> list = new LinkedList<>();
        while (null != cur) {
            list.add(cur);
            cur = cur.next;
        }

        return list.get((list.size()-1)/2);
    }

    private static Node rights2(Node head){
        if (null == head) {
            return null;
        }

        Node cur = head;
        List<Node> list = new LinkedList<>();
        while (null != cur) {
            list.add(cur);
            cur = cur.next;
        }

        return list.get(list.size()/2);
    }


    private static class CopyNode{
        private int data;
        private CopyNode next;
        private CopyNode random;

        public CopyNode(int data){
            this.data=data;
        }
    }

    public static void main(String[] args) {
        Node test = new Node(1);
        test.next = new Node(2);
        test.next.next = new Node(3);
        test.next.next.next = new Node(4);
        test.next.next.next.next = new Node(5);
        test.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next = new Node(8);
        test.next.next.next.next.next.next.next.next = new Node(9);
        test.next.next.next.next.next.next.next.next.next = new Node(10);
        test.next.next.next.next.next.next.next.next.next.next = new Node(11);
        test.next.next.next.next.next.next.next.next.next.next.next = new Node(12);
        test.next.next.next.next.next.next.next.next.next.next.next.next = new Node(13);
        test.next.next.next.next.next.next.next.next.next.next.next.next.next = new Node(14);


        System.out.println("\n\n======================\n");
        Node ans1 = rights1(test);
        Node ans2 = midOrUpMidNode(test);

        System.out.println(ans1);
        System.out.println(ans1.value);
        System.out.println(ans2);
        System.out.println(ans2.value);



        System.out.println("\n\n======================\n");
        Node ans3 = rights2(test);
        Node ans4 = midOrDownMidNode(test);

        System.out.println(ans3);
        System.out.println(ans3.value);
        System.out.println(ans4);
        System.out.println(ans4.value);
    }


    private static CopyNode copyListWithRand1(CopyNode head){
        Map<CopyNode,CopyNode> map = new HashMap<>();


        CopyNode cur = head;
        while (null != cur){
            map.put(cur,new CopyNode(cur.data));
            cur = cur.next;
        }

        cur = head;
        while (cur != null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);

            cur = cur.next;
        }

        return map.get(head);
    }

    private static CopyNode copyListWithRand2(CopyNode head){

        CopyNode cur = head;
        CopyNode next = null;
        while (null!=cur){
            next = cur.next;
            cur.next = new CopyNode(cur.data);
            cur.next.next=next;
            cur=next;
        }

        cur = head;
        CopyNode curCopy = null;
        while (cur != null) {
            // cur 老
            // cur.next 新 copy
            next = cur.next.next;
            curCopy = cur.next;
            curCopy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }

        // head head.next
        CopyNode res = head.next;
        cur = head;
        // split
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }
}
