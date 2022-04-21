package org.study.coding.class33;

/**
 * 237. 删除链表中的节点
 * 请编写一个函数，用于 删除单链表中某个特定节点 。在设计函数时需要注意，你无法访问链表的头节点 head ，只能直接访问 要被删除的节点 。
 * 题目数据保证需要删除的节点 不是末尾节点 。
 *
 *
 *
 * 示例 1：
 * 输入：head = [4,5,1,9], node = 5
 * 输出：[4,1,9]
 * 解释：指定链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9
 *
 *
 *
 * 示例 2：
 * 输入：head = [4,5,1,9], node = 1
 * 输出：[4,5,9]
 * 解释：指定链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9
 *
 *
 * @since 2022-04-20 21:32:32
 */
public class Problem_0237_DeleteNodeInLinkedList {

	public static class ListNode {
		int val;
		ListNode next;
	}

	/**
	 * 这样的方式不适用于工程上。可能有其他的一些东西，依赖当前节点的下一个节点
	 * 当前这种方式，无法删除最后一个节点
	 *
	 * @since 2022-04-20 21:36:34
	 */
	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;
	}

}
