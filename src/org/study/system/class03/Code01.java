package org.study.system.class03;

/**
 * @author phil
 * @date 2021/3/13 11:20
 * 链表操作
 */
public class Code01 {

    /**
     * 反转单链表
     * @date 2021-03-13 11:25:35
     */
    public static Node<Integer> reverseLinkedList(Node<Integer> head){

        Node<Integer> pre = null;

        Node<Integer> next = null;

        while (head.next != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }

    /**
     * 删除单链表中指定的节点
     * @date 2021-03-13 13:51:02
     */
    public static Node<Integer> delNode(Node<Integer> head,Integer num){

        while (null != head){
            if(head.value.equals(num)){
                head = head.next;
            }else{
                break;
            }
        }

        Node<Integer> pre = head;
        Node<Integer> cur = head;

        while (cur != null){
            if(cur.value.equals(num)){
                pre.next = cur.next;
            }else{
                cur = cur.next;
            }
        }

        return head;
    }
}
