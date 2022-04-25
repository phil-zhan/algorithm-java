package org.study.coding.class35;

/**
 * 687. 最长同值路径
 * 给定一个二叉树的 root ，返回 最长的路径的长度 ，这个路径中的 每个节点具有相同值 。 这条路径可以经过也可以不经过根节点。
 * 两个节点之间的路径长度 由它们之间的边数表示。
 *
 * 解法：
 * 考虑二叉树递归套路
 *
 * 以x为头的节点。最大路径是多大【必须是值相同的节点，才能组成路径】
 * 1）和x节点无关【左右树取一个最大值】
 * 2）和x节点有关
 *
 * a. 只包含x节点。
 * b. 左树能被头拉进来，左树能扎多深。右树能被头拉进来，右树能扎多深。最后求和再加1
 *
 *
 * @since 2022-04-24 21:43:53
 */
public class Problem_0687_LongestUnivaluePath {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int v) {
			val = v;
		}
	}

	public static int longestUnivaluePath(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process(root).max - 1;
	}

	/**
	 * 建设以x节点为头的树，返回两个信息
	 *
	 * @since 2022-04-24 21:44:00
	 */
	public static class Info {
		// 在一条路径上：要求每个节点通过且只通过一遍
		public int len; // 路径必须从x出发且只能往下走的情况下，路径的最大距离
		public int max; // 路径不要求必须从x出发的情况下，整棵树的合法路径最大距离

		public Info(int l, int m) {
			len = l;
			max = m;
		}
	}

	private static Info process(TreeNode x) {
		if (x == null) {
			return new Info(0, 0);
		}
		TreeNode l = x.left;
		TreeNode r = x.right;
		// 左树上，不要求从左孩子出发，最大路径
		// 左树上，必须从左孩子出发，往下的最大路径
		Info linfo = process(l);
		// 右树上，不要求从右孩子出发，最大路径
		// 右树上，必须从右孩子出发，往下的最大路径
		Info rinfo = process(r);
		// 必须从x出发的情况下，往下的最大路径
		int len = 1;
		if (l != null && l.val == x.val) {
			len = linfo.len + 1;
		}
		if (r != null && r.val == x.val) {
			len = Math.max(len, rinfo.len + 1);
		}
		// 不要求从x出发，最大路径
		int max = Math.max(Math.max(linfo.max, rinfo.max), len);
		if (l != null && r != null && l.val == x.val && r.val == x.val) {
			max = Math.max(max, linfo.len + rinfo.len + 1);
		}
		return new Info(len, max);
	}

}
