package org.study.class10;

/**
 *
 * 手写链表相交。返回第一个相交的节点
 * @author phil
 * @date 2021/6/28 16:58
 */
public class MainTest {

    private static class Node{
        int data;
        Node next;
        public Node(int data){
            this.data = data;
        }
    }


    private static Node getIntersectNode(Node head1,Node head2){
        if (null == head1 || null == head2) {
            return null;
        }

        // 找到两个链表的第一个入环节点
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);

        // 两个都有环
        if (null != loop1 && null != loop2) {
            return bothLoop(head1,loop1,head2,loop2);
        }


        // 两个都无环
        if(null == loop1 && null == loop2){
            return noLoopNode(head1,head2);
        }


        // 否则返回 null。【也就是他们没交点】
        return null;
    }

    /**
     * 返回第一个入环节点
     * @date 2021-06-28 17:11:43
     */
    private static Node getLoopNode(Node head){
        if (null == head || null == head.next || null == head.next.next) {
            return null;
        }

        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast){
            // fast 走到了空，无环
            if (null == fast.next || null == fast.next.next) {
                return null;
            }

            // 满的走一步，快的走两步
            slow = slow.next;
            fast = fast.next.next;
        }

        // 快慢指针在环上相遇
        // 让快指针回到头。以后在一起 一步一步跳，再次相遇时，就是第一次入环节点
        fast = head;
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }

        // 快慢指针在第一个入环节点相遇
        return fast;

    }

    /**
     * 两个链表都是无环节点，返回第一个相交的节点
     * @date 2021-06-28 17:13:15
     */
    private static Node noLoopNode(Node head1,Node head2){

        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;

        // 两个链表长度的差值
        int n=0;
        while (null != cur1.next){
            n++;
            cur1 = cur1.next;
        }
        while (null != cur2.next) {
            n--;
            cur2 = cur2.next;
        }

        // 两个链表的最后一个节点不相等，这两个链表肯定不相交
        if(cur1 != cur2){
            return null;
        }

        // 差值为正。第一个链表比较长，将cur1赋值为长链表的头节点。cur2赋值为短链表的头节点
        cur1 = n > 0 ? head1:head2;
        cur2 = cur1 ==head1 ? head2:head1;

        // 长链表先跳他们的差值步
        n = Math.abs(n);
        while (n != 0 ){
            n--;
            cur1 = cur1.next;
        }

        // 两个一起往后跳，直至相等，就是第一个相交的节点
        while (cur1 != cur2){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        return cur1;
    }


    private static Node bothLoop(Node head1,Node loop1,Node head2,Node loop2){
        Node cur1;
        Node cur2;

        if(loop1 == loop2){

            cur1 = head1;
            cur2 = head2;
            // 情况2
            int n=0;
            while (cur1 != loop1){
                n++;
                cur1 = cur1.next;
            }

            while (cur2 != loop2){
                n--;
                cur2 = cur2.next;
            }

            cur1 = n>0?head1:head2;
            cur2 = cur1==head1?head2:head1;

            n = Math.abs(n);
            while (n!=0){
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }

            return cur1;
        }

        cur1 = loop1.next;
        while (cur1 != loop1){
            if(cur1 == loop2){

                // loop1在围绕链表1转圈的过程中遇到了loop2，有交点【情况3】
                return loop1;
            }
            cur1 = cur1.next;
        }
        // loop1绕链表转了一圈回到了自己，都没有遇到loop2。情况1【无交点】
        return null;
    }


    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6

        // 两个的6号节点相交
        System.out.println(getIntersectNode(head1, head2).data);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2

        // 两个的2号节点相交
        System.out.println(getIntersectNode(head1, head2).data);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6

        // 两个的4号节点相交
        System.out.println(getIntersectNode(head1, head2).data);

    }




}
