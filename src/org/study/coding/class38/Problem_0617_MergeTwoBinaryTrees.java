package org.study.coding.class38;

/**
 * https://leetcode.cn/problems/merge-two-binary-trees/
 *
 *
 * 递归套路
 *
 * @since 2022-06-02 20:53:06
 */
public class Problem_0617_MergeTwoBinaryTrees {

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int val) {
			this.val = val;
		}
	}

	// 当前，一棵树的头是t1，另一颗树的头是t2
	// 请返回，整体merge之后的头
	public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null) {
			return t2;
		}
		if (t2 == null) {
			return t1;
		}
		// t1和t2都不是空
		TreeNode merge = new TreeNode(t1.val + t2.val);
		merge.left = mergeTrees(t1.left, t2.left);
		merge.right = mergeTrees(t1.right, t2.right);
		return merge;
	}

}
