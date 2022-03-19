package org.study.coding.class16;

/**
 * 给定一个环形链表头节点head，和一个正数m，从头开始，每次数到m就杀死当前节点，然后被杀节点的下一个节点从1开始重新数，周而复始直到只剩一个节点，返回最后的节点
 * Leetcode题目：https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
 *
 * 飞一次杀完之后，节点的编号都会发生变化。杀完后，从被杀的下一个节点开始，重新编号
 * 实现一个功能。能通过被杀后该节点的编号，推出，在该轮之前，当前节点的编号是什么？
 * 最后只剩下一个节点的时候，是要返回活下来的节点的。
 * 那么最后一个节点的编号肯定是1.由此倒推回去
 * 在剩下两个节点的时候，该活下来的节点的编号是什么
 * 。。。
 * 在剩下n个节点（一个节点也不杀的时候）。该活下来的节点的编号是什么。
 * 就能得到在原链表中，谁能活下来
 *
 *
 * 链表是循环链表
 * 报数和编号的关系【加谁总共有 i 个节点】
 * 报数是一直往上加的。编号是循环的
 * 如果传入的报数。算出节点编号。利用函数 Y = X%i 来算推【i是一个常量。总节点数】
 * 因为我们要的图形都是从点 （1,1） 开始 斜率为45度的斜线线段。
 * 可以利用函数的平移来得到。平移后的函数为 Y=(x-1)%i + 1
 *
 * 报数     编号
 *  1       1
 *  2       2
 *  .       .
 *  .       .
 *  .       .
 *  i       i
 *  i+1     1
 *  i+2     2
 *  .       .
 *  .       .
 *  .       .
 *  2i      i
 *  2i+1    1
 *
 *
 *  杀之前的编号 = (杀之后的编号-1+S) % i + 1  【画图。通过找图形的规律。利用图形的平移来得到】
 *  S：(m-1)%i + 1  也就是通过每次第几个的时候才杀人。来推出编号的报数
 *
 *  y = (x+(m-1)%i)%i+1 【x是杀后的编号。y是杀前的编号】
 *  y = (x+m-1)%i+1 【x是杀后的编号。y是杀前的编号】【简化版】
 *
 * @since 2022-03-17 08:37:44
 */
public class Code05_JosephusProblem {

    /**
     * 提交直接通过
     * 给定的编号是0~n-1的情况下，数到m就杀
     * 返回谁会活？
     *
     * @since 2022-03-18 02:01:26
     */
    public int lastRemaining1(int n, int m) {
        return getLive(n, m) - 1;
    }

    /**
     * 课上题目的设定是，给定的编号是1~n的情况下，数到m就杀
     * 返回谁会活？
     * 该函数返回的是啥之前的编号
     *
     * @since 2022-03-18 02:01:39
     */
    public static int getLive(int n, int m) {
        if (n == 1) {
            return 1;
        }
        return (getLive(n - 1, m) + m - 1) % n + 1;
    }

    /**
     * 提交直接通过
     * 给定的编号是0~n-1的情况下，数到m就杀
     * 返回谁会活？
     * 这个版本是迭代版
     *
     * @since 2022-03-18 02:01:51
     */
    public int lastRemaining2(int n, int m) {
        int ans = 1;
        int r = 1;
        while (r <= n) {
            ans = (ans + m - 1) % (r++) + 1;
        }
        return ans - 1;
    }

    /**
     * 以下的code针对单链表，不要提交
     *
     * @since 2022-03-18 02:02:07
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        while (head != last) {
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    public static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node cur = head.next;

        // tmp -> list size
        int size = 1;
        while (cur != head) {
            size++;
            cur = cur.next;
        }

        // tmp -> service node position
        int live = getLive(size, m);
        while (--live != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    public static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = head1;
        printCircularList(head1);
        head1 = josephusKill1(head1, 3);
        printCircularList(head1);

        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = head2;
        printCircularList(head2);
        head2 = josephusKill2(head2, 3);
        printCircularList(head2);

    }

}