package org.study.coding.class31;

/**
 * 148. 排序链表
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
 * 链表的归并排序
 * 时间复杂度O(N*logN), 因为是链表所以空间复杂度O(1)
 * <p>
 * <p>
 * <p>
 * 解法：
 * 排序中，能做到这个时间复杂度的。只有 堆排序、归并排序和随机快排
 * 但是堆排序需要根据下标取值的。链表没有下标。故链表不存在堆排序
 * 归并排序：对数组来说。空间复杂度是需要O(N) 【merge过程的辅助数组】,
 * 但是链表不需要这个空间。只需要用两个指针分别指向两个有序链表的头部即可。
 * 开始的时候，谁小谁做头，做完头之后，当前指针后移，然后对比后移后的头和另外一个头，谁小谁链在之前的完整链表上
 * 最后将完整的链表头返回回去
 * <p>
 * <p>
 * <p>
 * 实现链表的归并排序
 * 这里不能用递归版本了。【一旦开了递归，空间复杂度就和递归的层数有关了】
 * 非递归的实现。
 * 这里有一个步长的概念。一开始步长 step=1 .
 * 从左到右，第一次长度为step为左部分，出去左部分之后，再数一个step长度，就是右部分
 * 将当前的左右部分去跑merge过程。
 * 跑完之后，在剩下的长度中，继续数出左部分和右部分。再实现merge过程。
 * 以此类推。直到数完这个要排序的链表（或数组）
 * 【不够步长的时候，如果没有右部分，就不用merge了。直接开始下一轮步长。如果有右部分，但是右部分长度不够，此时还是要去merge的。只是要注意merge的边界】
 * <p>
 * <p>
 * <p>
 * 跑完一遍之后，将步长翻倍。继续数步长，继续跑merge。
 * 一直循环。
 * 直到步长大于等于原链表（或数组）的长度。才停止
 * <p>
 * <p>
 * <p>
 * 实现链表的随机快排【没讲，战略性的放弃】
 *
 * @since 2022-04-17 15:54:06
 */
public class Problem_0148_SortList {

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }

    /**
     * 链表的归并排序
     * 时间复杂度O(N*logN), 因为是链表所以空间复杂度O(1)
     *
     * @since 2022-04-17 15:54:59
     */
    public static ListNode sortList1(ListNode head) {
        int N = 0;
        ListNode cur = head;
        while (cur != null) {
            N++;
            cur = cur.next;
        }
        ListNode h = head;
        ListNode teamFirst = head;
        ListNode pre = null;

        // 枚举步长。从1开始，每次翻倍
        for (int len = 1; len < N; len <<= 1) {
            while (teamFirst != null) {
                // 左组从哪到哪 ls le
                // 右组从哪到哪 rs re
                // 左 右 next
                ListNode[] hthtn = hthtn(teamFirst, len);
                // ls...le  rs...re -> merge去
                // 整体的头、整体的尾
                ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);
                if (h == teamFirst) {
                    h = mhmt[0];
                    pre = mhmt[1];
                } else {
                    pre.next = mhmt[0];
                    pre = mhmt[1];
                }
                teamFirst = hthtn[4];
            }
            teamFirst = h;
            pre = null;
        }
        return h;
    }

    /**
     * 计算左部分的头
     * 计算左部分的尾
     * 计算右部分的尾
     * 计算右部分的尾
     * 还有就是当前组的后一个节点是谁，也要返回。因为当前组完成之后，还要继续
     *
     * @since 2022-04-17 16:32:11
     */
    public static ListNode[] hthtn(ListNode teamFirst, int len) {

        // 左部分的开始
        ListNode ls = teamFirst;

        // 左部分的结尾
        ListNode le = teamFirst;
        ListNode rs = null;
        ListNode re = null;
        ListNode next = null;
        int pass = 0;
        while (teamFirst != null) {
            pass++;
            if (pass <= len) {
                le = teamFirst;
            }
            if (pass == len + 1) {
                rs = teamFirst;
            }
            if (pass > len) {
                re = teamFirst;
            }
            if (pass == (len << 1)) {
                break;
            }
            teamFirst = teamFirst.next;
        }
        le.next = null;
        if (re != null) {
            next = re.next;
            re.next = null;
        }
        return new ListNode[]{ls, le, rs, re, next};
    }

    /**
     * merge需要返回合并后的头和尾
     * 因为合并之后，要将前一组的尾和当前组的头连起来
     * 同时下一组的头也要和当前组的尾连起来
     *
     * @since 2022-04-17 16:30:53
     */
    public static ListNode[] merge(ListNode ls, ListNode le, ListNode rs, ListNode re) {
        if (rs == null) {
            return new ListNode[]{ls, le};
        }
        ListNode head = null;
        ListNode pre = null;
        ListNode cur = null;
        ListNode tail = null;
        while (ls != le.next && rs != re.next) {
            if (ls.val <= rs.val) {
                cur = ls;
                ls = ls.next;
            } else {
                cur = rs;
                rs = rs.next;
            }
            if (pre == null) {
                head = cur;
                pre = cur;
            } else {
                pre.next = cur;
                pre = cur;
            }
        }
        if (ls != le.next) {
            while (ls != le.next) {
                pre.next = ls;
                pre = ls;
                tail = ls;
                ls = ls.next;
            }
        } else {
            while (rs != re.next) {
                pre.next = rs;
                pre = rs;
                tail = rs;
                rs = rs.next;
            }
        }
        return new ListNode[]{head, tail};
    }


    /**
     * 链表的快速排序
     * 时间复杂度O(N*logN), 空间复杂度O(logN)
     *
     * @since 2022-04-17 15:55:23
     */
    public static ListNode sortList2(ListNode head) {
        int n = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            n++;
        }
        return process(head, n).head;
    }

    public static class HeadAndTail {
        public ListNode head;
        public ListNode tail;

        public HeadAndTail(ListNode h, ListNode t) {
            head = h;
            tail = t;
        }
    }

    public static HeadAndTail process(ListNode head, int n) {
        if (n == 0) {
            return new HeadAndTail(head, head);
        }
        int index = (int) (Math.random() * n);
        ListNode cur = head;
        while (index-- != 0) {
            cur = cur.next;
        }
        Record r = partition(head, cur);
        HeadAndTail lht = process(r.lhead, r.lsize);
        HeadAndTail rht = process(r.rhead, r.rsize);
        if (lht.tail != null) {
            lht.tail.next = r.mhead;
        }
        r.mtail.next = rht.head;
        return new HeadAndTail(lht.head != null ? lht.head : r.mhead, rht.tail != null ? rht.tail : r.mtail);
    }

    public static class Record {
        public ListNode lhead;
        public int lsize;
        public ListNode rhead;
        public int rsize;
        public ListNode mhead;
        public ListNode mtail;

        public Record(ListNode lh, int ls, ListNode rh, int rs, ListNode mh, ListNode mt) {
            lhead = lh;
            lsize = ls;
            rhead = rh;
            rsize = rs;
            mhead = mh;
            mtail = mt;
        }
    }

    public static Record partition(ListNode head, ListNode mid) {
        ListNode lh = null;
        ListNode lt = null;
        int ls = 0;
        ListNode mh = null;
        ListNode mt = null;
        ListNode rh = null;
        ListNode rt = null;
        int rs = 0;
        ListNode tmp = null;
        while (head != null) {
            tmp = head.next;
            head.next = null;
            if (head.val < mid.val) {
                if (lh == null) {
                    lh = head;
                    lt = head;
                } else {
                    lt.next = head;
                    lt = head;
                }
                ls++;
            } else if (head.val > mid.val) {
                if (rh == null) {
                    rh = head;
                    rt = head;
                } else {
                    rt.next = head;
                    rt = head;
                }
                rs++;
            } else {
                if (mh == null) {
                    mh = head;
                    mt = head;
                } else {
                    mt.next = head;
                    mt = head;
                }
            }
            head = tmp;
        }
        return new Record(lh, ls, rh, rs, mh, mt);
    }

}
