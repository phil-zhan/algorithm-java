package org.study.coding.class05;

/**
 * 阿里笔试原题
 *
 * 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
 * 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
 *
 * sameNumber1(head) :以head为头的树，有多少个相等树
 *
 * same(head1,head2): 返回两个树是否是相等树
 * @since 2022-03-05 04:36:03
 */
public class Code02_LeftRightSameTreeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	// 时间复杂度O(N * logN)
	public static int sameNumber1(Node head) {
		if (head == null) {
			return 0;
		}
		return sameNumber1(head.left) + sameNumber2(head.right) + (same(head.left, head.right) ? 1 : 0);
	}

	public static boolean same(Node h1, Node h2) {
		if (h1 == null ^ h2 == null) {
			// 一个是null，另外一个不是 null
			return false;
		}
		if (h2 == null) {
			return true;
		}
		// 两个都不为空
		return h1.value == h2.value && same(h1.left, h2.left) && same(h1.right, h2.right);
	}

	// 时间复杂度O(N)
	public static int sameNumber2(Node head) {
		String algorithm = "SHA-256";
		Hash hash = new Hash(algorithm);
		return process(head, hash).ans;
	}

	public static class Info {
		public int ans;
		public String str;

		public Info(int a, String s) {
			ans = a;
			str = s;
		}
	}

	/**
	 * 设计一个hash，得和左、右树及当前的值 都相关
	 * 因为比较两个树是不是相等，要比较每个几点的值和结构
	 * 这里采用先序序列化来实现【注意，空树也要有对应字符】
	 * 先序来表示一个树的结构，没有任何歧义。
	 * 将其取hash值
	 * hash，相同的输入，必定导致相同的输出
	 * 任何长度的输入，其输出的长度必定一样
	 * 每次都比较相同长度的字符串。就能实现 O(1)
	 *
	 * @since 2022-03-05 05:23:17
	 */
	public static Info process(Node head, Hash hash) {
		if (head == null) {
			return new Info(0, hash.hashCode("#,"));
		}
		Info l = process(head.left, hash);
		Info r = process(head.right, hash);
		int ans = (l.str.equals(r.str) ? 1 : 0) + l.ans + r.ans;
		String str = hash.hashCode(head.value + "," + l.str + r.str);
		return new Info(ans, str);
	}

	public static Node randomBinaryTree(int restLevel, int maxValue) {
		if (restLevel == 0) {
			return null;
		}
		Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
		if (head != null) {
			head.left = randomBinaryTree(restLevel - 1, maxValue);
			head.right = randomBinaryTree(restLevel - 1, maxValue);
		}
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 8;
		int maxValue = 4;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			Node head = randomBinaryTree(maxLevel, maxValue);
			int ans1 = sameNumber1(head);
			int ans2 = sameNumber2(head);
			if (ans1 != ans2) {
				System.out.println("出错了！");
				System.out.println(ans1);
				System.out.println(ans2);
			}
		}
		System.out.println("测试结束");

	}

}
