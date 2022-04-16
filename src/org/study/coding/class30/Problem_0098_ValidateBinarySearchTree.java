package org.study.coding.class30;

/**
 * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
 * 有效 二叉搜索树定义如下：
 *
 * 节点的左子树只包含 小于 当前节点的数。
 * 节点的右子树只包含 大于 当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 *
 * 二叉树的递归套路可以搞定【后续遍历】
 *
 * 中序遍历【是一个递增的结果，也可以搞定】
 * 
 *
 * @since 2022-04-14 21:55:33
 */
public class Problem_0098_ValidateBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public boolean isValidBST(TreeNode root) {
		if (root == null) {
			return true;
		}
		TreeNode cur = root;
		TreeNode mostRight = null;
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
			if (pre != null && pre >= cur.val) {

				// 这里不要return 。不然这颗树可能就乱掉了
				ans = false;
			}
			pre = cur.val;
			cur = cur.right;
		}
		return ans;
	}

}
