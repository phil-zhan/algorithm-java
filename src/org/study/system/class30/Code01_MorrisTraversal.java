package org.study.system.class30;

public class Code01_MorrisTraversal {

	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static void process(Node root) {
		if (root == null) {
			return;
		}
		// 1 打印在这里：就是先序遍历
		process(root.left);
		// 2 打印在这里：就是中序遍历
		process(root.right);
		// 3 打印在这里：就是后序遍历
	}

	/**
	 * morris遍历
	 * @date 2021-10-03 13:38:36
	 */
	public static void morris(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					// mostRight 为空
					mostRight.right = cur;
					cur = cur.left;

					// 结束本次循环
					continue;
				} else {
					// mostRight 等于自己【将最右指针置位空的同时，还会走下面的指针右移】
					mostRight.right = null;
				}
			}
			cur = cur.right;
		}
	}

	/**
	 * morris 改为先序
	 * @date 2021-10-03 13:38:50
	 */
	public static void morrisPre(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {

					// 当前节点有左树
					System.out.print(cur.value + " ");
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			} else {

				// 当前节点没有左树
				System.out.print(cur.value + " ");
			}
			cur = cur.right;
		}
		System.out.println();
	}

	/**
	 * morris 改为中序
	 * @date 2021-10-03 13:39:06
	 */
	public static void morrisIn(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					// 第一次出现的时候就不管了
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}

			// 第二次出现 或只能出现一次。在这打印也就是实现了中序遍历
			// 没有左树【出现一次】 或左树上的最右节点等于自己【出现两次】
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		System.out.println();
	}

	/**
	 * morris 改为后序
	 * @date 2021-10-03 13:39:16
	 */
	public static void morrisPos(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;

					// 第二次到大
					// 逆序打印其左树的右边界
					printEdge(cur.left);
				}
			}
			cur = cur.right;
		}

		// 遍历完成，打印整棵树的右边界
		printEdge(head);
		System.out.println();
	}

	public static void printEdge(Node head) {
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

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}



	// 判断一棵树是否是搜索二叉树
	// 【左子树上的数都比中间的数小，右子树上的数都比中间的数大】
	// 也就是中序遍历后，是升序序列
	public static boolean isBST(Node head) {
		if (head == null) {
			return true;
		}
		Node cur = head;
		Node mostRight = null;
		Integer pre = null;
		boolean ans = true;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
				}
			}
			// 第二次出现 或只能出现一次。在这打印也就是实现了中序遍历
			// 没有左树【出现一次】 或左树上的最右节点等于自己【出现两次】


			// pre : 当前节点的前一个节点的值
			if (pre != null && pre >= cur.value) {
				// 不能在这里直接返回，不然之前被改过的指针就不能恢复了

				ans = false;
			}
			pre = cur.value;
			cur = cur.right;
		}
		return ans;
	}

	public static void main(String[] args) {
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(7);
		printTree(head);
		morrisIn(head);
		morrisPre(head);
		morrisPos(head);
		printTree(head);

	}

}
