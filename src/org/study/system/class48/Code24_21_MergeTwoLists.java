package org.study.system.class48;

/**
 * @author phil
 * @date 2022/2/21 14:17
 */
public class Code24_21_MergeTwoLists {

    public static void main(String[] args) {
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        ListNode listNode = new Code24_21_MergeTwoLists().mergeTwoLists(list1, list2);
        System.out.println();
    }
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        if(list1 == null && list2 == null){
            return null;
        }else if(list1 == null){
            return list2;
        }else if(list2 == null){
            return list1;
        }

        ListNode cur;
        ListNode list = new ListNode(Math.min(list1.val, list2.val));
        cur = list;
        if(list1.val == list.val){
            list1 = list1.next;
        }else{
            list2 = list2.next;
        }

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                cur.next = new ListNode(list1.val);
                list1 = list1.next;
            } else {
                cur.next = new ListNode(list2.val);
                list2 = list2.next;
            }
            cur = cur.next;
        }

        if (list1 != null) {
            cur.next = list1;
        }

        if (list2 != null) {
            cur.next = list2;
        }

        return list;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
