package org.study.coding.class30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 124. 二叉树中的最大路径和
 * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
 * 路径和 是路径中各节点值的总和。
 * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
 * 【任何两点间都存在路径和，返回最大的路径和】
 *
 * follow up : 如果要求返回整个路径怎么做？
 *
 * 解法：
 * 考虑二叉树的递归套路。
 * 最终的最大路径和与头节点无关【或者与头节点有关】
 * 1）与头节点无关
 * 	最大路径和来自左树或者来自右树【去个max最大值】【需要记录当前树上的最大路径和】
 * 2）与头节点有关
 *  ①：最大路径和只包含头节点
 *  ②：头结点只往左扎，走出来的最大路径和【需要记录当前树必须从头出发的情况下的最大路径和】
 *  ③：头结点只往右扎，走出来的最大路径和【需要记录当前树必须从头出发的情况下的最大路径和】
 *  ④：头结点既往左扎也往右扎。走出来的最大路径和【需要记录当前树必须从头出发的情况下的最大路径和】
 * 总结：需要记录当前树的最大路径和。以及需要记录必须从头节点出发情况下的最大路径和
 *
 * @since 2022-04-16 10:52:04
 */
public class Problem_0124_BinaryTreeMaximumPathSum {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		public TreeNode(int v) {
			val = v;
		}

	}

	public static int maxPathSum(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return process(root).maxPathSum;
	}

	/**
	 * 任何一棵树，必须汇报上来的信息
	 *
	 * @since 2022-04-16 11:06:33
	 */
	public static class Info {
		public int maxPathSum;
		public int maxPathSumFromHead;

		public Info(int path, int head) {
			maxPathSum = path;
			maxPathSumFromHead = head;
		}
	}

	public static Info process(TreeNode x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		// x 1)只有x 2）x往左扎 3）x往右扎
		int maxPathSumFromHead = x.val;
		if (leftInfo != null) {
			maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + leftInfo.maxPathSumFromHead);
		}
		if (rightInfo != null) {
			maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + rightInfo.maxPathSumFromHead);
		}
		// x整棵树最大路径和 1) 只有x 2)左树整体的最大路径和 3) 右树整体的最大路径和
		int maxPathSum = x.val;
		if (leftInfo != null) {
			maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSum);
		}
		if (rightInfo != null) {
			maxPathSum = Math.max(maxPathSum, rightInfo.maxPathSum);
		}
		// 4) x只往左扎
		// 5）x只往右扎
		maxPathSum = Math.max(maxPathSumFromHead, maxPathSum);
		// 6）一起扎
		if (leftInfo != null && rightInfo != null && leftInfo.maxPathSumFromHead > 0
				&& rightInfo.maxPathSumFromHead > 0) {
			maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead + x.val);
		}
		return new Info(maxPathSum, maxPathSumFromHead);
	}

	/**
	 * 如果要返回路径的做法
	 *
	 * @since 2022-04-16 11:07:04
	 */
	public static List<TreeNode> getMaxSumPath(TreeNode head) {
		List<TreeNode> ans = new ArrayList<>();
		if (head != null) {
			Data data = f(head);
			HashMap<TreeNode, TreeNode> fmap = new HashMap<>();
			fmap.put(head, head);

			// 做出父map
			fatherMap(head, fmap);

			// 整合路径
			fillPath(fmap, data.from, data.to, ans);
		}
		return ans;
	}

	public static class Data {
		/**
		 * 整体的路径和
		 * @since 2022-04-16 11:26:23
		 */
		public int maxAllSum;

		/**
		 * 最大路径和的路径起始点也记录一下。最后再根据这两个节点的最低公共祖先去生成整个路径
		 * @since 2022-04-16 11:22:08
		 */
		public TreeNode from;
		public TreeNode to;

		/**
		 * 必须从头出发的路径和
		 * @since 2022-04-16 11:26:34
		 */
		public int maxHeadSum;

		/**
		 * 必须从头出发的路径和，往下扎到了哪。
		 * @since 2022-04-16 11:21:59
		 */
		public TreeNode end;

		public Data(int a, TreeNode b, TreeNode c, int d, TreeNode e) {
			maxAllSum = a;
			from = b;
			to = c;
			maxHeadSum = d;
			end = e;
		}
	}

	public static Data f(TreeNode x) {
		if (x == null) {
			return null;
		}
		Data l = f(x.left);
		Data r = f(x.right);
		int maxHeadSum = x.val;
		TreeNode end = x;

		// 左树能不能推高从头出发的最大路径和
		if (l != null && l.maxHeadSum > 0 && (r == null || l.maxHeadSum > r.maxHeadSum)) {
			maxHeadSum += l.maxHeadSum;
			end = l.end;
		}

		// 右树能不能推高从头出发的最大路径和
		if (r != null && r.maxHeadSum > 0 && (l == null || r.maxHeadSum > l.maxHeadSum)) {
			maxHeadSum += r.maxHeadSum;
			end = r.end;
		}

		// 当前树的最大路径和能不能被推高。能被推高，就记录对应的首尾两端
		int maxAllSum = Integer.MIN_VALUE;
		TreeNode from = null;
		TreeNode to = null;

		// 左边
		if (l != null) {
			maxAllSum = l.maxAllSum;
			from = l.from;
			to = l.to;
		}

		// 右边
		if (r != null && r.maxAllSum > maxAllSum) {
			maxAllSum = r.maxAllSum;
			from = r.from;
			to = r.to;
		}

		// 即往左也往右。这时候的from和to要记录成对应的end
		int p3 = x.val + (l != null && l.maxHeadSum > 0 ? l.maxHeadSum : 0)
				+ (r != null && r.maxHeadSum > 0 ? r.maxHeadSum : 0);
		if (p3 > maxAllSum) {
			maxAllSum = p3;
			from = (l != null && l.maxHeadSum > 0) ? l.end : x;
			to = (r != null && r.maxHeadSum > 0) ? r.end : x;
		}

		// return
		return new Data(maxAllSum, from, to, maxHeadSum, end);
	}

	public static void fatherMap(TreeNode h, HashMap<TreeNode, TreeNode> map) {
		if (h.left == null && h.right == null) {
			return;
		}
		if (h.left != null) {
			map.put(h.left, h);
			fatherMap(h.left, map);
		}
		if (h.right != null) {
			map.put(h.right, h);
			fatherMap(h.right, map);
		}
	}

	public static void fillPath(HashMap<TreeNode, TreeNode> fmap, TreeNode a, TreeNode b, List<TreeNode> ans) {
		if (a == b) {
			ans.add(a);
		} else {
			HashSet<TreeNode> ap = new HashSet<>();
			TreeNode cur = a;
			while (cur != fmap.get(cur)) {
				ap.add(cur);
				cur = fmap.get(cur);
			}
			ap.add(cur);
			cur = b;
			TreeNode lca = null;
			while (lca == null) {
				if (ap.contains(cur)) {
					lca = cur;
				} else {
					cur = fmap.get(cur);
				}
			}
			while (a != lca) {
				ans.add(a);
				a = fmap.get(a);
			}
			ans.add(lca);
			ArrayList<TreeNode> right = new ArrayList<>();
			while (b != lca) {
				right.add(b);
				b = fmap.get(b);
			}
			for (int i = right.size() - 1; i >= 0; i--) {
				ans.add(right.get(i));
			}
		}
	}

	public static void main(String[] args) {
		TreeNode head = new TreeNode(4);
		head.left = new TreeNode(-7);
		head.right = new TreeNode(-5);
		head.left.left = new TreeNode(9);
		head.left.right = new TreeNode(9);
		head.right.left = new TreeNode(4);
		head.right.right = new TreeNode(3);

		List<TreeNode> maxPath = getMaxSumPath(head);

		for (TreeNode n : maxPath) {
			System.out.println(n.val);
		}
	}

}