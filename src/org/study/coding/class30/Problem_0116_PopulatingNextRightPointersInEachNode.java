package org.study.coding.class30;

/**
 * 116. 填充每个节点的下一个右侧节点指针
 * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
 *
 * ```
 * struct Node {
 *   int val;
 *   Node *left;
 *   Node *right;
 *   Node *next;
 * }
 * ```
 *
 * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
 * 初始状态下，所有next 指针都被设置为 NULL。
 *
 * 解法：
 * 主思路还是宽度优先遍历
 *
 * 设置一个数据结构 MyQueue。不带容器，只记录头尾指针和size
 * 复用原结构的next指针。实现双端队列
 *
 *
 * @since 2022-04-16 10:14:20
 */
public class Problem_0116_PopulatingNextRightPointersInEachNode {

	/**
	 * 不要提交这个类
	 *
	 * @since 2022-04-16 10:16:38
	 */
	public static class Node {
		public int val;
		public Node left;
		public Node right;
		public Node next;
	}

	/**
	 * 提交下面的代码
	 *
	 * @since 2022-04-16 10:16:46
	 */
	public static Node connect(Node root) {
		if (root == null) {
			return root;
		}
		MyQueue queue = new MyQueue();
		queue.offer(root);
		while (!queue.isEmpty()) {
			// 第一个弹出的节点
			Node pre = null;

			// 一批一批的处理
			int size = queue.size;
			for (int i = 0; i < size; i++) {
				Node cur = queue.poll();
				if (cur.left != null) {
					queue.offer(cur.left);
				}
				if (cur.right != null) {
					queue.offer(cur.right);
				}

				// 将当前弹出节点，挂在上一个弹出节点的后面
				if (pre != null) {
					pre.next = cur;
				}
				pre = cur;
			}
		}
		return root;
	}

	public static class MyQueue {
		public Node head;
		public Node tail;
		public int size;

		public MyQueue() {
			head = null;
			tail = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		/**
		 * 加节点【加在尾部】
		 * @since 2022-04-16 10:22:11
		 */
		public void offer(Node cur) {
			size++;
			if (head == null) {
				head = cur;
				tail = cur;
			} else {
				tail.next = cur;
				tail = cur;
			}
		}

		/**
		 * 弹节点【从头部】
		 * @since 2022-04-16 10:22:32
		 */
		public Node poll() {
			size--;
			Node ans = head;
			head = head.next;
			ans.next = null;
			return ans;
		}

	}

}
