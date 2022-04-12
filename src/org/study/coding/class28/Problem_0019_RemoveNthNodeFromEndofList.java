package org.study.coding.class28;

/**
 * 删除链表的倒数第 N 个结点
 * 解法：双指针
 * 先数够n个，然后准备两个指针，L和R
 * 一起向右移动
 * 当R来到最后的时候，L位置就是需要删除的位置。
 *
 * 因为我们是要删除，也就是要将被删除节点的前一个节点的next指针指向要删除节点的后一个节点
 * 所以考虑先数n+1个
 * 这样当R来到最后的时候，将L位置的next指向 next.next就好
 *
 * @since 2022-04-11 23:19:41
 */
public class Problem_0019_RemoveNthNodeFromEndofList {

    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            n--;
            if (n == -1) {
                pre = head;
            }
            if (n < -1) {
                pre = pre.next;
            }
            cur = cur.next;
        }
        if (n > 0) {
            return head;
        }
        if (pre == null) {
            return head.next;
        }
        pre.next = pre.next.next;
        return head;
    }

}
