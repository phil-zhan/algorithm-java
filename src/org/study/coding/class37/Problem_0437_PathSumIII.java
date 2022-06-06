package org.study.coding.class37;

import java.util.HashMap;

/**
 *437. 路径总和 III
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 *
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 *
 *
 *
 * 示例 1：
 *
 *
 *
 * 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
 * 输出：3
 * 解释：和等于 8 的路径有 3 条，如图所示。
 * 示例 2：
 *
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：3
 *
 *
 * 提示:
 *
 * 二叉树的节点个数的范围是 [0,1000]
 * -109 <= Node.val <= 109
 * -1000 <= targetSum <= 1000
 *
 *
 * @since 2022-05-31 08:14:30
 */
public class Problem_0437_PathSumIII {

	public class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static int pathSum(TreeNode root, int sum) {

		// key: 前缀和
		// value：该前缀出现的次数
		HashMap<Integer, Integer> preSumMap = new HashMap<>();

		// 还没有遍历的时候，注意加一个初始前缀和
		preSumMap.put(0, 1);

		// 遍历二叉树。来到节点 node 。考察是否有以 node 节点为最下面的一个节点，往上能推出 和 为sum的路径
		// 如果在map 中存在 sum - node.val 。那么就找到一个
		// 算完之后，将当前节点的前缀和维护到 map 中

		// 注意处理前缀和。在遍历完当前节点之后，记得将因为当前节点而产生的前缀和清除出去

		return process(root, sum, 0, preSumMap);
	}

	// 返回方法数
	public static int process(TreeNode x, int sum, int preAll, HashMap<Integer, Integer> preSumMap) {
		if (x == null) {
			return 0;
		}
		int all = preAll + x.val;
		int ans = 0;
		if (preSumMap.containsKey(all - sum)) {
			ans = preSumMap.get(all - sum);
		}
		if (!preSumMap.containsKey(all)) {
			preSumMap.put(all, 1);
		} else {
			preSumMap.put(all, preSumMap.get(all) + 1);
		}
		ans += process(x.left, sum, all, preSumMap);
		ans += process(x.right, sum, all, preSumMap);

		// 擦掉
		if (preSumMap.get(all) == 1) {
			preSumMap.remove(all);
		} else {
			preSumMap.put(all, preSumMap.get(all) - 1);
		}
		return ans;
	}

}
