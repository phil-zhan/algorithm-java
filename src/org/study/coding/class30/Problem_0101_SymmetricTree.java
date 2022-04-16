package org.study.coding.class30;

/**
 * 对称二叉树
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 *
 *
 * @since 2022-04-15 08:02:30
 */
public class Problem_0101_SymmetricTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public boolean isSymmetric(TreeNode root) {

		// 左树和右树是对称
		return isMirror(root.left, root.right);
	}

	public boolean isSymmetric2(TreeNode root) {

		// 自己和自己的复制树如果是镜像，那么他们单独也是镜像
		return isMirror(root, root);
	}

	/**
	 * 一棵树是原始树  head1
	 * 另一棵是翻面树  head2
	 * @since 2022-04-15 08:02:51
	 */
	public static boolean isMirror(TreeNode head1, TreeNode head2) {
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1 != null && head2 != null) {
			return head1.val == head2.val
					// 第一个的左对第二个的右
					// 第二个的右对第一个的左
					&& isMirror(head1.left, head2.right)
					&& isMirror(head1.right, head2.left);
		}
		// 一个为空，一个不为空  false
		return false;
	}

}
