package org.study.class12;

import java.util.LinkedList;


/**
 * Complete binary tree
 * 是否是完全二叉树
 *
 * `对于一个二叉树的每一层来说，要么是满的【既有左节点又有右节点】，要么是最后一层不满。就算是不满，也是从左到右排好的`
 * @date 2021-07-05 16:30:26
 */
public class Code01_IsCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * // =============================== //
	 * @date 2021-07-06 09:38:34
	 */
	public static boolean isCBT1(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<>();
		// 是否遇到过左右两个孩子不双全的节点
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;
			if (
				// 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
			    (leaf && (l != null || r != null)) 
			    ||
				// 有右无左，直接 false
			    (l == null && r != null)

			) {
				return false;
			}
			// 继续按层遍历
			if (l != null) {
				queue.add(l);
			}
			if (r != null) {
				queue.add(r);
			}

			// 遇到左右不双全【缺左或缺右，亦或两个都缺】。开启开关
			// 第一次遇到开启为true，之后的也就是 true
			if (l == null || r == null) {
				leaf = true;
			}
		}
		return true;
	}

	public static boolean isCBT2(Node head) {
		if (head == null) {
			return true;
		}
		return process(head).isCBT;
	}

	// 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
	public static class Info {
		public boolean isFull;
		public boolean isCBT;
		public int height;

		public Info(boolean full, boolean cbt, int h) {
			isFull = full;
			isCBT = cbt;
			height = h;
		}
	}

	public static Info process(Node X) {
		if (X == null) {
			return new Info(true, true, 0);
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		
		
		
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		
		
		boolean isFull = leftInfo.isFull 
				&& 
				rightInfo.isFull 
				&& leftInfo.height == rightInfo.height;
		
		
		boolean isCBT = false;
		if (isFull) {
			isCBT = true;
		} else { // 以x为头整棵树，不满
			if (leftInfo.isCBT && rightInfo.isCBT) {
				
				
				if (leftInfo.isCBT 
						&& rightInfo.isFull 
						&& leftInfo.height == rightInfo.height + 1) {
					isCBT = true;
				}
				if (leftInfo.isFull 
						&& 
						rightInfo.isFull 
						&& leftInfo.height == rightInfo.height + 1) {
					isCBT = true;
				}
				if (leftInfo.isFull 
						&& rightInfo.isCBT && leftInfo.height == rightInfo.height) {
					isCBT = true;
				}
				
				
			}
		}
		return new Info(isFull, isCBT, height);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isCBT1(head) != isCBT2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
