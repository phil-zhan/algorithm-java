package org.study;

import org.study.class05.Code003_QuickSort_2;
import org.study.class30.Code01_MorrisTraversal;

import java.util.*;

/**
 * @author phil
 * @date 2022/1/7 14:26
 */
public class MainTest {


    public static void main(String[] args) {
        Node head = new Node(2);
        head.right = new Node(3);
        head.right.right = new Node(1);
//        head.left.left = new Node(1);
//        head.left.right = new Node(3);
//        head.right.left = new Node(5);
//        head.right.right = new Node(7);

//        new MainTest().morrisPre(head);
//        new MainTest().morrisMid(head);
        new MainTest().morrisPos(head);

        List<Integer> integers = new MainTest().postorderTraversal(head);
        System.out.println(integers);

    }


    public void morrisPre(Node head){

        System.out.println("下面是先序");
        if (head == null){
            return;
        }

        Node cur = head;
        Node mostRight;

        while (cur != null){
            if (cur.left != null){
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    mostRight.right = cur;
                    System.out.println(cur.value);
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;
            }else{
                System.out.println(cur.value);
            }
            cur = cur.right;
        }

    }


    public void morrisMid(Node head){

        System.out.println("下面是中序\n");
        if (head == null){
            return;
        }

        Node cur = head;
        Node mostRight;

        while (cur != null){
            if (cur.left != null){
                mostRight = cur.left;
                while (mostRight.right!= null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }
                mostRight.right = null;
            }

            System.out.println(cur.value);
            cur = cur.right;
        }
    }


    public void morrisPos(Node head){

        System.out.println("下面是后序");
        if (head == null){
            return;
        }

        Node cur = head;
        Node mostRight;

        while (cur != null){
            if (cur.left != null){
                mostRight = cur.left;
                while (mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }

                if (mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                }else{
                    mostRight.right = null;
                    printRightEdge(cur.left);
                    cur = cur.right;
                }
            }else{
                cur= cur.right;
            }
        }

        printRightEdge(head);
    }
    
    

    public void printRightEdge(Node head){

        if (head == null){
            return;
        }


        // 链表反转
        Node tail = reverseEdge(head);
        Node cur = tail;

        // 打印
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }

        // 打印完成后，再反转回去
        reverseEdge(tail);

    }

    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }


    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }


    public List<Integer> postorderTraversal(Node root) {
        // morris
        List<Integer> ans = new ArrayList<>();
        if(root == null){
            return ans;
        }

        Node cur = root;
        Node mostRight;

        while(cur != null){
            if(cur.left != null){
                mostRight = cur.left;
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;

                    continue;
                }else{
                    mostRight.right = null;
                    collectLeft(cur.left,ans);
                }
            }

            cur = cur.right;
        }

        collectLeft(root,ans);

        return ans;
    }

    public void collectLeft(Node head,List<Integer> list){
        if(head == null){
            return;
        }
        Node tail = reverse(head);

        Node cur = tail;
        while(cur != null){
            list.add(cur.value);
            cur = cur.right;
        }

        reverse(tail);
    }

    public Node reverse(Node head){
        if(head == null || head.right == null){
            return head;
        }
        Node pre = null;
        Node cur = head;
        Node post;

        while(cur != null){
            post = cur.right;
            cur.right = pre;
            pre = cur;
            cur = post;
        }

        return pre;
    }

}
