package org.study.coding.class30;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 103. 二叉树的锯齿形层序遍历
 * 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * 解法：
 * 一批一批的处理
 *
 *
 * @since 2022-04-15 08:09:44
 */
public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}
		LinkedList<TreeNode> deque = new LinkedList<>();
		deque.add(root);
		int size = 0;

		// 记录从左开始还是从右开始
		boolean isHead = true;
		while (!deque.isEmpty()) {
			size = deque.size();
			List<Integer> curLevel = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				TreeNode cur = isHead ? deque.pollFirst() : deque.pollLast();
				curLevel.add(cur.val);

				// 加在队列的后面
				if(isHead) {
					if (cur.left != null) {
						deque.addLast(cur.left);
					}
					if (cur.right != null) {
						deque.addLast(cur.right);
					}
				}else {
					// 加在队列的前面

					if (cur.right != null) {
						deque.addFirst(cur.right);
					}
					if (cur.left != null) {
						deque.addFirst(cur.left);
					}
				}
			}
			ans.add(curLevel);
			isHead = !isHead;
		}
		return ans;
	}

}
